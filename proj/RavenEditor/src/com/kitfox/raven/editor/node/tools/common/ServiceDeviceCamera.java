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

package com.kitfox.raven.editor.node.tools.common;

import com.kitfox.coyote.math.CyMatrix4d;
import com.kitfox.raven.editor.node.tools.ToolService;
import java.awt.geom.AffineTransform;

/**
 *
 * @author kitfox
 */
public interface ServiceDeviceCamera extends ToolService
{
    @Deprecated
    public AffineTransform getWorldToDeviceTransform(AffineTransform xform);
    @Deprecated
    public void setWorldToDeviceTransform(AffineTransform xform);

    public CyMatrix4d getWorldToDeviceTransform(CyMatrix4d xform);
    public void setWorldToDeviceTransform(CyMatrix4d xform);
}
