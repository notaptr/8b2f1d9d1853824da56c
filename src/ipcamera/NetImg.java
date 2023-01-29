/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipcamera;

import java.io.File;
import static java.io.File.createTempFile;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.currentTimeMillis;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vadim
 */
public class NetImg {
    
    FileOutputStream fos;
    File tmp;
    IPCamera prt;
    long begin;
    int lastID;
    boolean wasFirst;
    int totalPacks;
    
    NetImg(IPCamera p) {
        
        prt = p;
        lastID = 0;
        wasFirst = false;
        
    }
    
    protected void newPacket(DatagramPacket pt) throws IOException {
    
        int len     = pt.getLength();     
        byte[] data = pt.getData();

        ByteBuffer rcvd = ByteBuffer.wrap(data);
        rcvd.limit(len);
        
        int pN = rcvd.getInt();
        int pT = rcvd.getInt();

        if (lastID == pN) { 
            return; 
        }
        
        if (pN == 1) { 
            wasFirst = true; 
            begin = currentTimeMillis();

            totalPacks = 0;
            
            if ((tmp == null) || (fos == null)) {
                tmp = createTempFile("ipcam", null);
                fos = new FileOutputStream(tmp);
                
                java.awt.EventQueue.invokeLater(() -> {
                    prt.setStatus("Загружаем...");
                });
                
                lastID = 0;
            }
        }
        
        if (!wasFirst) {
            return;
        }

        lastID = pN;
        
        byte[] rest  = new byte[rcvd.remaining()];
        rcvd.get(rest);

        fos.write(rest);

        totalPacks++;
        
        if (pN == pT) {

            fos.flush();
            fos.close();

            final long endf = currentTimeMillis();
            final long beginf = begin;
            
            final IPMutex mtx = new IPMutex();
            mtx.close();
            
            //System.out.println("rest = " + rest.length + ", len = " + len);
            
            java.awt.EventQueue.invokeLater(() -> {               
                try {

                    prt.setImg(ImageIO.read(tmp));
                    synchronized(mtx) {
                        mtx.open();
                        mtx.notify();
                    }

                    prt.setStatus("Загружено за " + (endf-beginf) + " мсек");

                } catch (IOException ex) {
                    //Logger.getLogger(NetImg.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            synchronized(mtx) {
                while(!mtx.isOpen()) {
                    try {
                        mtx.wait();
                    } catch (InterruptedException e) {}
                }
            }
            tmp.delete();
            fos = null;
            tmp = null;
            
            wasFirst = false;
            
        } 
    
    } // newPacket

    protected void fail() {
        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(NetImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        tmp.delete();
        fos = null;
        tmp = null;
    }
}

