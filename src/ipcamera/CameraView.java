/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipcamera;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

/**
 *
 * @author vadim
 */
public class CameraView extends javax.swing.JFrame {

    FrameView imgFrame;
    
    public CameraView() {
        
        initComponents();
        this.setTitle("IPCamera v.1.1");
           
    }    
    
    public void setImg(Image i) {
        
        imgFrame.setImage(i.getScaledInstance(-1, -1, Image.SCALE_FAST));
        imgFrame.repaint();
        
        i.flush();
            
    }

    public void setStatus(String ms) {
        
        jLabel2.setText(ms);              

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        javax.swing.JPanel imgFrame = new FrameView();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 480));
        setSize(new java.awt.Dimension(700, 540));
        setType(java.awt.Window.Type.UTILITY);

        imgFrame.setBorder(javax.swing.BorderFactory.createTitledBorder("Кадр"));
        imgFrame.setAlignmentX(1.0F);
        imgFrame.setAlignmentY(1.0F);

        javax.swing.GroupLayout imgFrameLayout = new javax.swing.GroupLayout(imgFrame);
        imgFrame.setLayout(imgFrameLayout);
        imgFrameLayout.setHorizontalGroup(
            imgFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imgFrameLayout.setVerticalGroup(
            imgFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        getContentPane().add(imgFrame, java.awt.BorderLayout.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("jLabel2");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setAlignmentY(0.0F);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CameraView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
