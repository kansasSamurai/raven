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
import com.kitfox.coyote.renderer.CyGLWrapper.ProgramParamName;
import com.kitfox.coyote.renderer.CyGLWrapper.ShaderParamName;
import com.kitfox.coyote.renderer.CyGLWrapper.ShaderType;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 *
 * @author kitfox
 */
abstract public class CyMaterial<DrawRecord>
{
    public static final CyVertexArrayKey KEY_POSITION = new CyVertexArrayKey();
    public static final CyVertexArrayKey KEY_TEXCOORD0 = new CyVertexArrayKey();
    public static final CyVertexArrayKey KEY_NORMAL = new CyVertexArrayKey();
    public static final CyVertexArrayKey KEY_COLOR0 = new CyVertexArrayKey();

    ArrayList<DrawRecord> recordPool = new ArrayList<DrawRecord>();

//    /**
//     * Create a mesh-material record that describes how this should be
//     * drawn onscreen.
//     */
//    abstract CyDrawRecord createDrawRecord(CyDrawStack stack);

//    abstract public void bind(GLWrapper gl);

    protected int loadShader(CyGLWrapper gl, ShaderType type, String path) throws CyShaderException
    {
        int id = gl.glCreateShader(type);
        String vertSrc = gl.loadSource(path);
        gl.glShaderSource(id, 1, new String[]{vertSrc}, null);
        gl.glCompileShader(id);

        checkShaderValid(gl, id, path);

        return id;
    }

    protected void checkShaderValid(CyGLWrapper gl, int id, String path) throws CyShaderException
    {
        IntBuffer ibuf = BufferUtil.allocateInt(1);
        gl.glGetShaderiv(id, ShaderParamName.GL_COMPILE_STATUS, ibuf);
        int success = ibuf.get(0);
        if (success == 0)
        {
            gl.glGetShaderiv(id, ShaderParamName.GL_INFO_LOG_LENGTH, ibuf);
            int logLen = ibuf.get(0);
            ByteBuffer log = BufferUtil.allocateByte(logLen);
            gl.glGetShaderInfoLog(id, logLen, null, log);

            byte[] logArr = new byte[logLen];
            log.get(logArr);
            throw new CyShaderException(path, new String(logArr));
        }
    }

    protected void checkProgramValid(CyGLWrapper gl, int id) throws CyProgramException
    {
        IntBuffer ibuf = BufferUtil.allocateInt(1);
        gl.glGetProgramiv(id, ProgramParamName.GL_LINK_STATUS, ibuf);
        int success = ibuf.get(0);
        if (success == 0)
        {
            gl.glGetProgramiv(id, ProgramParamName.GL_INFO_LOG_LENGTH, ibuf);
            int logLen = ibuf.get(0);
            ByteBuffer log = BufferUtil.allocateByte(logLen);
            gl.glGetProgramInfoLog(id, logLen, null, log);
            byte[] logArr = new byte[logLen];
            log.get(logArr);

            throw new CyProgramException(new String(logArr));
        }
    }
}
