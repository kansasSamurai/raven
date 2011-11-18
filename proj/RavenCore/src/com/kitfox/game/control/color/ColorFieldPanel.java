/*
 * Copyright 2011 Mark McKay
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * ColorFieldPanel.java
 *
 * Created on Sep 18, 2009, 11:36:05 PM
 */

package com.kitfox.game.control.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author kitfox
 */
public class ColorFieldPanel extends javax.swing.JPanel implements ColorFieldListener
{
    private static final long serialVersionUID = 0;

    BufferedImage backImage;
    protected ColorField field;

    ColorFieldWeakListener listener;

    /** Creates new form ColorFieldPanel */
    public ColorFieldPanel()
    {
        initComponents();
    }

    @Override
    public void paintComponent(Graphics gg)
    {
        Graphics2D g = (Graphics2D)gg;

        g.setPaint(UnderlayPaint.inst().getPaint());
        g.fillRect(0, 0, getWidth(), getHeight());

        BufferedImage img = backImage;
        if (backImage == null)
        {
            backImage = img = getGraphicsConfiguration()
                    .createCompatibleImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);

            float dw = 1f / (img.getWidth() - 1);
            float dh = 1f / (img.getHeight() - 1);
            for (int j = 0; j < img.getHeight(); ++j)
            {
                for (int i = 0; i < img.getWidth(); ++i)
                {
                    img.setRGB(i, j, field == null
                            ? 0xff000000
                            : field.toDisplayColor(i * dw, j * dh).getRGB());
                }
            }
        }

        g.drawImage(img, 0, 0, this);
    }

    /**
     * @return the sampler
     */
    public ColorField getField()
    {
        return field;
    }

    /**
     * @param sampler the sampler to set
     */
    public void setColorField(ColorField sampler)
    {
        if (listener != null)
        {
            listener.remove();
            listener = null;
        }

        this.field = sampler;
        backImage = null;

        if (field != null)
        {
            listener = new ColorFieldWeakListener(this, field);
            field.addColorFieldListener(listener);
        }
        repaint();
    }

    @Override
    public void colorFieldChanged(ChangeEvent evt)
    {
        backImage = null;
        repaint();
    }

    public void beginStopEdits(ChangeEvent evt)
    {
    }

    public void endStopEdits(ChangeEvent evt)
    {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        backImage = null;
        repaint();
    }//GEN-LAST:event_formComponentResized

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        requestFocus();
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
