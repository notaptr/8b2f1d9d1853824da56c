/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipcamera;

import java.awt.Image;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vadim
 */
public class NetworkLayer extends Thread {
    
    private DatagramSocket socket;
    private IPCamera parent;
    private Image img;
    
    NetworkLayer(IPCamera ipc) {
        
        parent = ipc;
        
        try {
            socket = new DatagramSocket(17335);
            socket.setSoTimeout(0);
            socket.setReuseAddress(true);
                        
        } catch (Exception ex) {
            socket = null;
            Logger.getLogger(NetworkLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void run() {
        
        final int BUFFER_LENGHT = 65500;
        DatagramPacket pt;
        DatagramPacket send_pt;
        byte[] reply;
        Boolean work = true;
        
        if (socket == null) {
            return;
        }
                
        reply = "OK".getBytes();
        
        byte[] buff = new byte[BUFFER_LENGHT];
        pt = new DatagramPacket(buff, BUFFER_LENGHT);
        send_pt = new DatagramPacket(reply, reply.length);
        
        notify("Работаем, ждём фоточку");
        
        NetImg ni = new NetImg(parent);
        
        while(work) {
      
            try {
                socket.receive(pt);

                send_pt.setAddress(pt.getAddress());
                send_pt.setPort(pt.getPort());
                socket.send(send_pt);
                
                ni.newPacket(pt);
                
            } catch (IOException ex) {
                Logger.getLogger(NetworkLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } // while
        
    } // run()
    
    private void notify(String m) {
        
        java.awt.EventQueue.invokeLater(() -> {
            parent.setStatus(m);
        });
        
    }
    
}
