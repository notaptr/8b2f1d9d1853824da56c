/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipcamera;

import java.awt.Image;


public class IPCamera {
    
    private final CameraView camView;
    private final NetworkLayer server;

    
    IPCamera() {
        
        camView = new CameraView();
        camView.setVisible(true);
        
        server = new NetworkLayer(this);
        server.start();
        
    }
    
    protected void setImg(Image i) {
        camView.setImg(i);
    }
    
    protected void setStatus(String s) {
        camView.setStatus(s);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            {
                final IPCamera ipc = new IPCamera();
            }
        });
        
    }
    
}
