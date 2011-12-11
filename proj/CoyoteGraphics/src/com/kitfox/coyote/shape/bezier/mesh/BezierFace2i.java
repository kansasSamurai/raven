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

package com.kitfox.coyote.shape.bezier.mesh;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kitfox
 */
public class BezierFace2i<FaceData, FaceVertexData>
{
    final ArrayList<BezierVertex2i> vertices = new ArrayList<BezierVertex2i>();
    final ArrayList<BezierEdge2i> edges = new ArrayList<BezierEdge2i>();
    private FaceData data;
    HashMap<BezierVertex2i, FaceVertexData> faceVertexData 
            = new HashMap<BezierVertex2i, FaceVertexData>();

    /**
     * @return the data
     */
    public FaceData getData()
    {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(FaceData data)
    {
        this.data = data;
    }
}
