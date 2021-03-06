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

package com.kitfox.coyote.renderer;

import com.kitfox.coyote.math.BufferUtil;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * The CyGLContext tracks information specific to a physical GL context.
 * Mostly this means generating and tracking OpenGL IDs for the
 * various textures, vertex buffers and other memory consuming
 * operations.
 * 
 * <p>CyGLContext also contains a queue messages can be posted to that 
 * will be executed and flushed whenever processActions() is
 * called.  This is typically done at the start of a screen refresh.</p>
 * 
 * <p>Most of the functions CyGLContext provides are intended for use
 * by wrapper objects, so users will typically not need to interact
 * with this object directly.</p>
 *
 * @author kitfox
 */
public class CyGLContext
{
    ArrayList<CyGLAction> actionList = new ArrayList<CyGLAction>();

    HashMap<Class, CyMaterial> materialMap = new HashMap<Class, CyMaterial>();

    WeakHashMap<CyVertexBuffer, VertexBufferInfo> vboMap = new WeakHashMap<CyVertexBuffer, VertexBufferInfo>();
    WeakHashMap<Object, TextureBufferInfo> texMap = new WeakHashMap<Object, TextureBufferInfo>();
    WeakHashMap<Object, FramebufferInfo> fboMap = new WeakHashMap<Object, FramebufferInfo>();
    WeakHashMap<Object, RenderbufferInfo> rboMap = new WeakHashMap<Object, RenderbufferInfo>();
//    ReferenceQueue vboQueue = new ReferenceQueue();

    ReferenceQueue refQueue = new ReferenceQueue();
    HashMap<Reference, CyGLAction> dieMap = new HashMap<Reference, CyGLAction>();

    /**
     * Post an action to be processed when processActions() is
     * next called.
     * 
     * @param action 
     */
    public void postAction(CyGLAction action)
    {
        actionList.add(action);
    }

    /**
     * Process all queued actions and flush the queue.
     * 
     * @param wrapper 
     */
    public void processActions(CyGLWrapper wrapper)
    {
        //For all dead references, trigger their actions
        while (true)
        {
            Reference ref = refQueue.poll();
            if (ref == null)
            {
                break;
            }
            CyGLAction action = dieMap.remove(ref);
            action.doAction(wrapper);
        }

        //Process other actions
        if (actionList.isEmpty())
        {
            return;
        }

        ArrayList<CyGLAction> list = actionList;
        actionList = new ArrayList<CyGLAction>();
        for (int i = 0; i < actionList.size(); ++i)
        {
            list.get(i).doAction(wrapper);
        }
    }

    /**
     * Clear the action queue
     */
    public void clearActions()
    {
        actionList.clear();
    }

    /**
     * Fetch the material of the given class.
     * 
     * @param <T>
     * @param cls
     * @return 
     */
    public <T extends CyMaterial> T getMaterial(Class<T> cls)
    {
        return (T)materialMap.get(cls);
    }

    /**
     * Materials are treated like services.  Keep track of this
     * material instance so that it can be shared by all drawing
     * actions that require the same type of material.
     * 
     * @param mat 
     */
    public void registerMaterial(CyMaterial mat)
    {
        materialMap.put(mat.getClass(), mat);
    }

    /**
     * Allocate and track a vertex buffer for the given source.
     * 
     * @param source
     * @param gl
     * @return 
     */
    public VertexBufferInfo getVertexBufferInfo(CyVertexBuffer source, CyGLWrapper gl)
    {
        VertexBufferInfo info = vboMap.get(source);
        if (info == null)
        {
            info = new VertexBufferInfo(source, gl);
            vboMap.put(source, info);
            WeakReference ref = new WeakReference(source, refQueue);
            dieMap.put(ref, info);
        }
        return info;
    }


    /**
     * Allocate and track a texture buffer for the given source.
     * 
     * @param source
     * @param gl
     * @return 
     */
    public TextureBufferInfo getTextureBufferInfo(Object source, CyGLWrapper gl)
    {
        TextureBufferInfo info = texMap.get(source);
        if (info == null)
        {
            info = new TextureBufferInfo(gl);
            texMap.put(source, info);
            WeakReference ref = new WeakReference(source, refQueue);
            dieMap.put(ref, info);
        }
        return info;
    }


    /**
     * Allocate and track a frame buffer for the given source.
     * 
     * @param source
     * @param gl
     * @return 
     */
    public FramebufferInfo getFramebufferInfo(Object source, CyGLWrapper gl)
    {
        FramebufferInfo info = fboMap.get(source);
        if (info == null)
        {
            info = new FramebufferInfo(gl);
            fboMap.put(source, info);
            WeakReference ref = new WeakReference(source, refQueue);
            dieMap.put(ref, info);
        }
        return info;
    }


    /**
     * Allocate and track a render buffer for the given source.
     * 
     * @param source
     * @param gl
     * @return 
     */
    public RenderbufferInfo getRenderbufferInfo(Object source, CyGLWrapper gl)
    {
        RenderbufferInfo info = rboMap.get(source);
        if (info == null)
        {
            info = new RenderbufferInfo(gl);
            rboMap.put(source, info);
            WeakReference ref = new WeakReference(source, refQueue);
            dieMap.put(ref, info);
        }
        return info;
    }

    //------------------------------


    public class RenderbufferInfo implements CyGLAction
    {
        private final int rboId;
        private int dirty;

        public RenderbufferInfo(CyGLWrapper gl)
        {
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            gl.glGenRenderbuffers(1, ibuf);
            rboId = ibuf.get(0);
            dirty = -1;
        }

        /**
         * @return the texId
         */
        public int getRboId()
        {
            return rboId;
        }

        @Override
        public void doAction(CyGLWrapper gl)
        {
            //Delete this object when the source is garbage collected
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            ibuf.put(0, rboId);
            gl.glDeleteRenderbuffers(1, ibuf);
        }

        /**
         * @return the dirty
         */
        public int getDirty()
        {
            return dirty;
        }

        /**
         * @param dirty the dirty to set
         */
        public void setDirty(int dirty)
        {
            this.dirty = dirty;
        }
    }


    public class FramebufferInfo implements CyGLAction
    {
        private final int fboId;
        private int dirty;

        public FramebufferInfo(CyGLWrapper gl)
        {
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            gl.glGenFramebuffers(1, ibuf);
            fboId = ibuf.get(0);

            dirty = -1;
        }

        /**
         * @return the texId
         */
        public int getFboId()
        {
            return fboId;
        }

        @Override
        public void doAction(CyGLWrapper gl)
        {
            //Delete this object when the source is garbage collected
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            ibuf.put(0, fboId);
            gl.glDeleteFramebuffers(1, ibuf);
        }

        /**
         * @return the dirty
         */
        public int getDirty()
        {
            return dirty;
        }

        /**
         * @param dirty the dirty to set
         */
        public void setDirty(int dirty)
        {
            this.dirty = dirty;
        }
    }


    public class TextureBufferInfo implements CyGLAction
    {
        private final int texId;
        private int dirty;

        public TextureBufferInfo(CyGLWrapper gl)
        {
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            gl.glGenBuffers(1, ibuf);
            texId = ibuf.get(0);

            dirty = -1;
        }

        /**
         * @return the texId
         */
        public int getTexId()
        {
            return texId;
        }

        @Override
        public void doAction(CyGLWrapper gl)
        {
            //Delete this object when the source is garbage collected
            IntBuffer ibuf = BufferUtil.allocateInt(1);
            ibuf.put(0, texId);
            gl.glDeleteBuffers(1, ibuf);
        }

        /**
         * @return the dirty
         */
        public int getDirty()
        {
            return dirty;
        }

        /**
         * @param dirty the dirty to set
         */
        public void setDirty(int dirty)
        {
            this.dirty = dirty;
        }
    }

    public class VertexBufferInfo implements CyGLAction
    {
        private final int arrayId;
        private final int eleId;
//        WeakReference<CyVertexBuffer> sourceRef;

        //private boolean dirty = true;
        private int arrayDirty;
        private int eleDirty;

        public VertexBufferInfo(CyVertexBuffer source, CyGLWrapper gl)
        {
//            sourceRef = new WeakReference<CyVertexBuffer>(source, vboQueue);
            arrayDirty = source.arrayDirty - 1;
            eleDirty = source.eleDirty - 1;
            
            IntBuffer ibuf = BufferUtil.allocateInt(2);
            gl.glGenBuffers(2, ibuf);
            arrayId = ibuf.get(0);
            eleId = ibuf.get(1);
        }

        /**
         * @return the arrayId
         */
        public int getArrayId()
        {
            return arrayId;
        }

        /**
         * @return the eleId
         */
        public int getEleId()
        {
            return eleId;
        }

        /**
         * @return the arrayDirty
         */
        public int getArrayDirty()
        {
            return arrayDirty;
        }

        /**
         * @param arrayDirty the arrayDirty to set
         */
        public void setArrayDirty(int arrayDirty)
        {
            this.arrayDirty = arrayDirty;
        }

        /**
         * @return the eleDirty
         */
        public int getEleDirty()
        {
            return eleDirty;
        }

        /**
         * @param eleDirty the eleDirty to set
         */
        public void setEleDirty(int eleDirty)
        {
            this.eleDirty = eleDirty;
        }

        @Override
        public void doAction(CyGLWrapper gl)
        {
            //Delete this object when the source is garbage collected
            IntBuffer ibuf = BufferUtil.allocateInt(2);
            ibuf.put(0, arrayId);
            ibuf.put(1, eleId);
            gl.glDeleteBuffers(2, ibuf);
        }
    }
}
