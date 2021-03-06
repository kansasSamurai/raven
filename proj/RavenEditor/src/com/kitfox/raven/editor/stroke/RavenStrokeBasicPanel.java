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
 * StrokeStyleCustomEditor.java
 *
 * Created on Sep 17, 2009, 8:14:59 PM
 */

package com.kitfox.raven.editor.stroke;

import com.kitfox.raven.editor.stroke.RavenStrokeBasic.Cap;
import com.kitfox.raven.editor.stroke.RavenStrokeBasic.Join;
import com.kitfox.rabbit.util.NumberText;
import java.awt.BorderLayout;

/**
 *
 * @author kitfox
 */
@Deprecated
public class RavenStrokeBasicPanel extends javax.swing.JPanel
{
    private static final long serialVersionUID = 1;

    RavenStrokeBasic stroke = new RavenStrokeBasic();
    public static final String PROP_STROKE = "stroke";
    RavenStrokePreviewPanel strokePanel = new RavenStrokePreviewPanel();

    boolean updating = true;

    /** Creates new form StrokeStyleCustomEditor */
    public RavenStrokeBasicPanel()
    {
        initComponents();

        combo_cap.addItem(Cap.ROUND);
        combo_cap.addItem(Cap.BUTT);
        combo_cap.addItem(Cap.SQUARE);

        combo_join.addItem(Join.ROUND);
        combo_join.addItem(Join.SQUARE);
        combo_join.addItem(Join.BEVEL);

        panel_preview.add(strokePanel, BorderLayout.CENTER);

        updateFromStroke();
    }

    private void updateFromStroke()
    {
        updating = true;

        if (stroke == null)
        {
            stroke = new RavenStrokeBasic();
        }

        spinner_width.setValue((Float)stroke.getWidth());
        combo_cap.setSelectedItem(stroke.getCap());
        combo_join.setSelectedItem(stroke.getJoin());
        spinner_miterLimit.setValue((Float)stroke.getMiterLimit());
        float[] dashArray = stroke.getDashes();
        text_dash.setText(dashArray == null ? "" : NumberText.asString(dashArray, ","));
        spinner_dashPhase.setValue((Float)stroke.getDashPhase());

        strokePanel.setStroke(stroke);

        updating = false;
    }

    private void buildStroke()
    {
        if (updating)
        {
            return;
        }

        RavenStrokeBasic oldStroke = stroke;

        float width = (Float)spinner_width.getValue();
        Cap cap = (Cap)combo_cap.getSelectedItem();
        Join join = (Join)combo_join.getSelectedItem();
        float miterLimit = (Float)spinner_miterLimit.getValue();
        float[] dashes = NumberText.findFloatArray(text_dash.getText());
        float dashPhase = (Float)spinner_dashPhase.getValue();

        stroke = new RavenStrokeBasic(width, cap, join, miterLimit, dashes, dashPhase);

        strokePanel.setStroke(stroke);
        
        firePropertyChange(PROP_STROKE, oldStroke, stroke);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        combo_cap = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        combo_join = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        text_dash = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spinner_width = new javax.swing.JSpinner();
        spinner_miterLimit = new javax.swing.JSpinner();
        spinner_dashPhase = new javax.swing.JSpinner();
        panel_preview = new javax.swing.JPanel();

        jLabel1.setText("Width");

        combo_cap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_capActionPerformed(evt);
            }
        });

        jLabel2.setText("Cap");

        jLabel3.setText("Join");

        combo_join.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_joinActionPerformed(evt);
            }
        });

        jLabel4.setText("Miter Limit");

        jLabel5.setText("Dash");

        text_dash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_dashActionPerformed(evt);
            }
        });
        text_dash.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                text_dashFocusLost(evt);
            }
        });

        jLabel6.setText("Dash Phase");

        spinner_width.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        spinner_width.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinner_widthStateChanged(evt);
            }
        });

        spinner_miterLimit.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(4.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        spinner_miterLimit.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinner_miterLimitStateChanged(evt);
            }
        });

        spinner_dashPhase.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), null, Float.valueOf(1.0f)));
        spinner_dashPhase.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinner_dashPhaseStateChanged(evt);
            }
        });

        panel_preview.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel_preview.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_preview, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_width, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_cap, 0, 160, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_join, 0, 160, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_miterLimit, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_dash, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinner_dashPhase, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spinner_width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_cap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(combo_join, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinner_miterLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(text_dash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spinner_dashPhase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_preview, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void text_dashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_dashActionPerformed
        buildStroke();
    }//GEN-LAST:event_text_dashActionPerformed

    private void text_dashFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_dashFocusLost
        buildStroke();
    }//GEN-LAST:event_text_dashFocusLost

    private void combo_capActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_capActionPerformed
        buildStroke();
    }//GEN-LAST:event_combo_capActionPerformed

    private void combo_joinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_joinActionPerformed
        buildStroke();
    }//GEN-LAST:event_combo_joinActionPerformed

    private void spinner_widthStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinner_widthStateChanged
    {//GEN-HEADEREND:event_spinner_widthStateChanged
        buildStroke();
    }//GEN-LAST:event_spinner_widthStateChanged

    private void spinner_miterLimitStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinner_miterLimitStateChanged
    {//GEN-HEADEREND:event_spinner_miterLimitStateChanged
        buildStroke();
    }//GEN-LAST:event_spinner_miterLimitStateChanged

    private void spinner_dashPhaseStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinner_dashPhaseStateChanged
    {//GEN-HEADEREND:event_spinner_dashPhaseStateChanged
        buildStroke();
    }//GEN-LAST:event_spinner_dashPhaseStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combo_cap;
    private javax.swing.JComboBox combo_join;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel panel_preview;
    private javax.swing.JSpinner spinner_dashPhase;
    private javax.swing.JSpinner spinner_miterLimit;
    private javax.swing.JSpinner spinner_width;
    private javax.swing.JTextField text_dash;
    // End of variables declaration//GEN-END:variables


    /**
     * @return the paint
     */
    public RavenStrokeBasic getStroke()
    {
        return stroke;
    }

    /**
     * @param stroke the paint to set
     */
    public void setStroke(RavenStrokeBasic stroke)
    {
        RavenStrokeBasic oldStroke = stroke;
        this.stroke = stroke;
        firePropertyChange(PROP_STROKE, oldStroke, stroke);

        updateFromStroke();
    }

}
