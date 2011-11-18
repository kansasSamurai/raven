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

package com.kitfox.raven.shape.bezier;

import com.kitfox.cache.CacheBoolean;
import com.kitfox.cache.CacheElement;
import com.kitfox.raven.util.planeData.PlaneDataProvider;
import com.kitfox.raven.util.service.ServiceInst;

/**
 *
 * @author kitfox
 */
@ServiceInst(service=PlaneDataProvider.class)
public class EdgeVisible extends PlaneDataProvider<Boolean>
{

    public EdgeVisible()
    {
        super(Boolean.class, "Edge Visible");
    }

    @Override
    public CacheBoolean asCache(Boolean data)
    {
        return new CacheBoolean(data);
    }

    @Override
    public Boolean parse(CacheElement cacheElement)
    {
        if (cacheElement instanceof CacheBoolean)
        {
            return ((CacheBoolean)cacheElement).getValue();
        }
        return false;
    }

}
