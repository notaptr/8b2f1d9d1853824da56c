/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipcamera;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author vadim
 */
public class FrameView extends JPanel {
    
    private static final int COMPONENT_WIDTH = 649;
    private static final int COMPONENT_HEIGHT = 488;
    private static final int TEXT_MARGIN = 10;
    
    protected Image mImg;
    private String ldateTime;
    
    public FrameView() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setDoubleBuffered(true);
        setTimeString();
        setMinimumSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));
    }

    private void setTimeString() {

        //Получаем текущую дату и время и превращаем её в строку
        DateFormat lTIME = DateFormat.getTimeInstance(DateFormat.LONG);
        DateFormat lDATE = DateFormat.getDateInstance(DateFormat.MEDIUM);
        lTIME.setTimeZone(TimeZone.getTimeZone(ZoneId.of("GMT+3")));
        Date ldate = new Date();

        ldateTime = lDATE.format(ldate) + ", " + lTIME.format(ldate);
        
    }
    
    public void setImage(Image i) {
    
        setTimeString();
        mImg = i;

    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        Image lImg;
        FontMetrics ltm = g.getFontMetrics();
        Graphics2D g2 = (Graphics2D) g;
            
        Insets lBI = getBorder().getBorderInsets(this);
        int lHI = lBI.left + lBI.right;
        int lVI = lBI.top + lBI.bottom;
        int lW = getWidth() - lHI;
        int lH = getHeight() - lVI;
        
        //System.out.println(lW + "," + lH);
        
        if ((lH % 8) != 0) {
            lH = lH - (lH % 8);
        }
 
        if ((lW % 8) != 0) {
            lW = lW - (lW % 8);
        }

        if (mImg == null) {
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream("resources/analog-foto.jpg");
                mImg = ImageIO.read(is);
            } catch (IOException ex) {}
        }
        
        if (mImg != null) {
            int lIW = mImg.getWidth(null);
            int lIH = mImg.getHeight(null);
            
            double cfs = (double)lIW / (double)lIH;
            double cft = (double)lW / (double)lH;
            
            if (cft > cfs) { 
                lImg = mImg.getScaledInstance(-1, lH, Image.SCALE_FAST);
             
            } else {
                lImg = mImg.getScaledInstance(lW, -1, Image.SCALE_FAST);
            }

            lIW = lImg.getWidth(null);
            lIH = lImg.getHeight(null);
            
            int lximgpos = lBI.left + (lW - lIW) / 2;
            int lyimgpos = lBI.top + (lH - lIH) / 2;
        
            //Получаем размер строки текста в пикселях и делаем рамочку
            int lFBL = ltm.getMaxDescent();
            int lstrW = ltm.stringWidth(ldateTime);
            int lstrH = ltm.getHeight();
            int lRectW = lstrW + TEXT_MARGIN;
            int lRectH = lstrH + TEXT_MARGIN;
            
            //Рисуем подложку, основное изображение
            g2.drawImage(lImg, lximgpos, lyimgpos, null);
            
            //Создаём буфер картинки в памяти по размеру рамки с текстом
            BufferedImage bi = new BufferedImage(lRectW, lRectH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g3 = (Graphics2D) bi.getGraphics();

           
            g3.setColor(Color.LIGHT_GRAY);
            g3.fillRect(0, 0, lRectW, lRectH);
                        
            float[] scales = { 1f, 1f, 1f, 0.7f };
            float[] offsets = { 0f, 0f, 0f, 0f };
            RescaleOp rop = new RescaleOp(scales, offsets, null);

            //Впечатываем полупрозрачный фон
            g2.drawImage(bi, rop, lximgpos, lyimgpos);
            
            //Дорисовываем текст и рамочку без прозрачности
            g2.setColor(Color.BLACK);
            g2.drawRect(lximgpos, lyimgpos, lRectW, lRectH);
            
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            g2.drawString(ldateTime, lximgpos + TEXT_MARGIN/2, lyimgpos + TEXT_MARGIN/2 + lstrH - lFBL);
            
        } else {
            // Draw Text
            String lem = "Пока картинка не получена, ждём-с";
            int lstrW = ltm.stringWidth(lem);
            g.drawString(lem ,(lW - lstrW - lHI)/2, lH/2);

        }

    }  

}
