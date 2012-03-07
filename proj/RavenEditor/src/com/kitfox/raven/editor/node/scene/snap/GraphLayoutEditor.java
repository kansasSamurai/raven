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

package com.kitfox.raven.editor.node.scene.snap;

import com.kitfox.raven.util.service.ServiceInst;
import com.kitfox.raven.util.tree.*;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author kitfox
 */
@Deprecated
public class GraphLayoutEditor extends PropertyWrapperEditor<GraphLayout>
{
    public GraphLayoutEditor(PropertyWrapper wrapper)
    {
        super(wrapper);
    }

    @Override
    public boolean isPaintable()
    {
        return false;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getJavaInitializationString()
    {
        return getAsText();
    }

    @Override
    public String getAsText()
    {
        GraphLayout val = getValueFlat();
        return val == null ? "" : val.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        GraphLayout val = GraphLayout.create(text);
        setValue(new PropertyDataInline<GraphLayout>(val));
    }

    @Override
    public String[] getTags()
    {
        return null;
    }

    @Override
    public PropertyCustomEditor createCustomEditor()
    {
        return null;
    }

    @Override
    public boolean supportsCustomEditor()
    {
        return false;
    }

    //----------------------------

    @ServiceInst(service=PropertyProvider.class)
    public static class Provider extends PropertyProvider<GraphLayout>
    {
        public Provider()
        {
            super(GraphLayout.class);
        }

        @Override
        public PropertyWrapperEditor createEditor(PropertyWrapper wrapper)
        {
            return new GraphLayoutEditor(wrapper);
        }

        @Override
        public String asText(GraphLayout value)
        {
            return value == null ? "" : value.toString();
        }

        @Override
        public GraphLayout fromText(String text)
        {
            return GraphLayout.create(text);
        }
    }
    
}
