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

package com.kitfox.raven.util.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author kitfox
 */
abstract public class PropertyWrapperEditor<PropType>
        implements PropertyEditor, PropertyWrapperListener, MouseListener
{
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    ArrayList<PropertyWrapperEditorListener> listeners = new ArrayList<PropertyWrapperEditorListener>();

    private final PropertyWrapper<? extends NodeObject, PropType> wrapper;
    PropertyWrapperWeakListener wrapperListener;

//    protected PropertyData<PropType> value;
    public static final String PROP_VALUE = "value";

    JPopupMenu menu;

    public PropertyWrapperEditor(
            PropertyWrapper<? extends NodeObject, PropType> wrapper)
    {
        this.wrapper = wrapper;

        wrapperListener = new PropertyWrapperWeakListener(this, wrapper);
        wrapper.addPropertyWrapperListener(wrapperListener);
    }

    protected void buildPopupMenu(JPopupMenu menu)
    {
        appendDefaultMenu(menu);
    }

    protected void appendDefaultMenu(JPopupMenu menu)
    {
        if (!wrapper.isAnimatable())
        {
            return;
        }

        menu.add(new JMenuItem(new AbstractAction("Set Key")
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setKey();
                }
            }
        ));

        menu.add(new JMenuItem(new AbstractAction("Remove Key")
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeKey();
                }
            }
        ));
    }

    protected void showMenu(MouseEvent evt)
    {
        if (menu == null)
        {
            menu = new JPopupMenu();
            buildPopupMenu(menu);
        }
        menu.show(evt.getComponent(), evt.getX(), evt.getY());
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            showMenu(evt);
            evt.consume();
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        if (evt.isPopupTrigger())
        {
            showMenu(evt);
            evt.consume();
            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt)
    {
    }

    @Override
    public void mouseEntered(MouseEvent evt)
    {
    }

    @Override
    public void mouseExited(MouseEvent evt)
    {
    }

    public NodeObject getNode()
    {
        return wrapper.getNode();
    }

    public NodeSymbol getDocument()
    {
        NodeObject node = getNode();
        return node == null ? null : node.getSymbol();
    }

    public PropType getValueFlat()
    {
        return wrapper.getValue();
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    @Override
    public PropertyData<PropType> getValue()
    {
        return wrapper.getData();
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    @Override
    public void setValue(Object value)
    {
        setValue(new PropertyDataInline(value));
    }

    public void setValue(PropertyData<PropType> value)
    {
        setValue(value, true);
    }

    public void setValue(PropertyData<PropType> value, boolean recordHistory)
    {
        wrapper.setData(value, recordHistory);
    }

    protected void setKey()
    {
        setKey(TrackKey.Interp.CONST);
    }

    protected void setKey(TrackKey.Interp interp)
    {
        NodeSymbol sym = wrapper.getNode().getSymbol();
        TrackLibrary trackLib = sym.getRoot().getTrackLibrary();
//        Track track = trackLib.getCurTrack();
//        if (track == null)
//        {
//            return;
//        }

        wrapper.setKeyAt(trackLib.getFrameCur(),
                getValue(), interp);
        fireEditingDone();
    }

    protected void removeKey()
    {
        NodeSymbol sym = wrapper.getNode().getSymbol();
        TrackLibrary trackLib = sym.getRoot().getTrackLibrary();
//        Track track = trackLib.getCurTrack();
//        if (track == null)
//        {
//            return;
//        }
        
        wrapper.removeKeyAt(trackLib.getFrameCur());
        fireEditingDone();
    }

    @Override
    public void propertyWrapperDataChanged(PropertyChangeEvent evt)
    {
        propertyChangeSupport.firePropertyChange(PROP_VALUE,
                evt.getOldValue(), evt.getNewValue());
    }

    @Override
    public void propertyWrapperTrackChanged(PropertyTrackChangeEvent evt)
    {
//        propertyChangeSupport.firePropertyChange(PROP_VALUE,
//                null, null);
    }

    @Override
    public void propertyWrapperTrackKeyChanged(PropertyTrackKeyChangeEvent evt)
    {
//        propertyChangeSupport.firePropertyChange(PROP_VALUE,
//                null, null);
    }

    public void addPropertyWrapperEditorListener(PropertyWrapperEditorListener l)
    {
        listeners.add(l);
    }
    
    public void removePropertyWrapperEditorListener(PropertyWrapperEditorListener l)
    {
        listeners.remove(l);
    }
    
    protected void fireEditingDone()
    {
        EventObject evt = new EventObject(this);
        for (PropertyWrapperEditorListener l: 
                new ArrayList<PropertyWrapperEditorListener>(listeners))
        {
            l.propertyWrapperEditingDone(evt);
        }
    }
    
    protected void fireEditingCanceled()
    {
        EventObject evt = new EventObject(this);
        for (PropertyWrapperEditorListener l: 
                new ArrayList<PropertyWrapperEditorListener>(listeners))
        {
            l.propertyWrapperEditingCanceled(evt);
        }
    }
    
    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * @return the wrapper
     */
    public PropertyWrapper<? extends NodeObject, PropType> getWrapper() {
        return wrapper;
    }

    @Override
    final public Component getCustomEditor()
    {
        throw new UnsupportedOperationException("Use getCustomEditorTransaction()");
    }

    abstract public PropertyCustomEditor createCustomEditor();

    //---------------------------------
    public class FindNode implements NodeVisitor
    {
        private NodeObject bestNode;
        private final String name;

        public FindNode(String name)
        {
            this.name = name;
        }

        @Override
        public void visit(NodeObject node)
        {
            if (name.equals(node.getName())
                    && wrapper.getPropertyType().isAssignableFrom(node.getClass()))
            {
                bestNode = node;
            }
        }

        /**
         * @return the bestNode
         */
        public NodeObject getBestNode() {
            return bestNode;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }
    }

}
