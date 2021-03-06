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

package com.kitfox.raven.editor.paint;

import com.kitfox.game.control.color.ColorStyleEditorPanel;
import com.kitfox.game.control.color.GradientStyleEditorPanel;
import com.kitfox.raven.util.tree.NodeSymbol;
import com.kitfox.raven.util.tree.NodeObject;
import com.kitfox.raven.util.tree.PropertyData;
import com.kitfox.raven.util.tree.PropertyDataInline;
import com.kitfox.raven.util.tree.PropertyDataReference;
import com.kitfox.raven.util.tree.PropertyCustomEditor;
import com.kitfox.raven.util.tree.property.NodeObjectPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author kitfox
 */
@Deprecated
public class RavenPaintCustomEditor extends javax.swing.JPanel
        implements PropertyChangeListener, PropertyCustomEditor
{
    private static final long serialVersionUID = 1;

    static enum PaintState {
        INHERIT, NONE, COLOR, GRADIENT, REF
    }
    PaintState paintState;

//    PropertyChangeWeakListener listener;
    final RavenPaintEditor ed;

    ColorStyleEditorPanel colorPanel = new ColorStyleEditorPanel();
    GradientStyleEditorPanel gradientPanel = new GradientStyleEditorPanel();
//    RadialGradientPaintPanel gradientRadialPanel = new RadialGradientPaintPanel();
    NodeObjectPanel nodeObjectPanel = new NodeObjectPanel();

//    ColorStyle lastColor;
//    LinearGradientStyle lastGradLin;
//    RadialGradientStyle lastGradRad;
//    MultipleGradientStyle lastGrad;
//    int lastRefUid;

    boolean updating = true;
    PropertyData<RavenPaint> initValue;
    PropertyData<RavenPaint> curValue;

    /** Creates new form StrokeStyleCustomEditor */
    public RavenPaintCustomEditor(RavenPaintEditor ed)
    {
        this.ed = ed;
        curValue = initValue = ed.getValue();

        initComponents();

        for (PaintState ps: PaintState.values())
        {
            combo_type.addItem(ps);
        }

        colorPanel.addPropertyChangeListener(
                ColorStyleEditorPanel.PROP_COLOR, this);
        gradientPanel.addPropertyChangeListener(
                GradientStyleEditorPanel.PROP_GRADIENT, this);
//        gradientRadialPanel.addPropertyChangeListener(
//                RadialGradientPaintPanel.PROP_PAINT, this);
        nodeObjectPanel.addPropertyChangeListener(
                NodeObjectPanel.PROP_NODE, this);

        //Setup reference nodes
        NodeSymbol sym = ed.getDocument();
        NodeSymbol.NodeFilter find =
                new NodeSymbol.NodeFilter(RavenPaint.class);
        sym.getRoot().visit(find);
        nodeObjectPanel.setNodes(find.getList());

        buildFromValue();
    }

    public void setValue(RavenPaint value)
    {
        setValue(new PropertyDataInline(value));
    }

    public void setValue(PropertyData<RavenPaint> data)
    {
        curValue = data;
        ed.setValue(data, false);
    }

    @Override
    public void customEditorCommit()
    {
        ed.setValue(curValue);
    }

    @Override
    public void customEditorCancel()
    {
        ed.setValue(initValue, false);
    }

    @Override
    public Component getCustomEditor()
    {
        return this;
    }

    private void buildFromValue()
    {
        updating = true;

        PropertyData<RavenPaint> data = ed.getValue();

        if (data instanceof PropertyDataInline)
        {
            RavenPaint paint = ed.getValueFlat();

            if (paint == null || paint == RavenPaintNone.PAINT)
            {
                combo_type.setSelectedItem(PaintState.NONE);
            }
            else if (paint instanceof RavenPaintColor)
            {
                combo_type.setSelectedItem(PaintState.COLOR);
                colorPanel.setColor(((RavenPaintColor)paint).getColor());
            }
            else if (paint instanceof RavenPaintGradient)
            {
                combo_type.setSelectedItem(PaintState.GRADIENT);
                gradientPanel.setGradient(
                        ((RavenPaintGradient)paint).getGradient());
            }
        }
        else if (data instanceof PropertyDataReference)
        {
            combo_type.setSelectedItem(PaintState.REF);

            NodeSymbol doc = ed.getDocument();
            int uid = ((PropertyDataReference)data).getUid();
            nodeObjectPanel.setNode(doc.getNode(uid));
        }

        synchDisplay();

        updating = false;
    }

    private void synchDisplay()
    {
        PaintState type = (PaintState)combo_type.getSelectedItem();
        if (type != paintState)
        {
            panel_workArea.removeAll();
            switch (type)
            {
                case COLOR:
                    panel_workArea.add(colorPanel, BorderLayout.CENTER);
                    break;
                case GRADIENT:
                    panel_workArea.add(gradientPanel, BorderLayout.CENTER);
                    break;
//                case GRAD_RAD:
//                    panel_workArea.add(gradientRadialPanel, BorderLayout.CENTER);
//                    break;
                case REF:
                    panel_workArea.add(nodeObjectPanel, BorderLayout.CENTER);
                    break;
            }
            paintState = type;

            revalidate();
            repaint();
        }
    }

//    private void cacheColorState(PropertyData<RavenPaint> data)
//    {
//        if (data instanceof PropertyDataInline)
//        {
//            RavenPaint paint = ed.getValueFlat();
//
//            if (paint instanceof RavenPaintColor)
//            {
//                lastColor = ((RavenPaintColor)paint).getColor();
//            }
//            else if (paint instanceof RavenPaintGradientLinear)
//            {
//                lastGrad = lastGradLin =
//                        ((RavenPaintGradientLinear)paint).getGradient();
//            }
//            else if (paint instanceof RavenPaintGradientRadial)
//            {
//                lastGrad = lastGradRad =
//                        ((RavenPaintGradientRadial)paint).getGradient();
//            }
//        }
//        else if (data instanceof PropertyDataReference)
//        {
//            lastRefUid = ((PropertyDataReference)data).getUid();
//        }
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getSource() == colorPanel)
        {
            setValue(new RavenPaintColor(colorPanel.getColor()));
        }
        else if (evt.getSource() == gradientPanel)
        {
            setValue(new RavenPaintGradient(gradientPanel.getGradient()));
        }
//        else if (evt.getSource() == gradientRadialPanel)
//        {
//            setValue(new RavenPaintGradientRadial(gradientRadialPanel.getPaint()));
//        }
        else if (evt.getSource() == nodeObjectPanel)
        {
            setValue(new PropertyDataReference<RavenPaint>
                    (nodeObjectPanel.getSelectedNode().getUid()));
        }
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
        combo_type = new javax.swing.JComboBox();
        panel_workArea = new javax.swing.JPanel();

        jLabel1.setText("Type");

        combo_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_typeActionPerformed(evt);
            }
        });

        panel_workArea.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_type, 0, 316, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panel_workArea, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_workArea, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void combo_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_typeActionPerformed

        if (updating)
        {
            return;
        }

        PaintState type = (PaintState)combo_type.getSelectedItem();

        switch (type)
        {
            case NONE:
                setValue(RavenPaintNone.PAINT);
                break;
            case COLOR:
                setValue(new RavenPaintColor(colorPanel.getColor()));
                break;
            case GRADIENT:
                setValue(new RavenPaintGradient(
                        gradientPanel.getGradient()));
                break;
            case REF:
                NodeObject node = nodeObjectPanel.getSelectedNode();
                setValue(node == null ? null
                        : new PropertyDataReference<RavenPaint>(node.getUid()));
                break;
        }

        synchDisplay();

    }//GEN-LAST:event_combo_typeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combo_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel_workArea;
    // End of variables declaration//GEN-END:variables

//    class FindNodes implements NodeVisitor
//    {
//        private ArrayList<NodeObject> list = new ArrayList<NodeObject>();
//
//        @Override
//        public void visit(NodeObject node)
//        {
//            if (RavenPaint.class.isAssignableFrom(
//                    node.getClass()))
//            {
//                list.add(node);
//            }
//        }
//
//        /**
//         * @return the list
//         */
//        public ArrayList<NodeObject> getList() {
//            return list;
//        }
//    }

}
