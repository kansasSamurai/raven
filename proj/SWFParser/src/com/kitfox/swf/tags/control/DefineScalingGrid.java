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

package com.kitfox.swf.tags.control;

import com.kitfox.swf.dataType.RECT;
import com.kitfox.swf.dataType.SWFDataReader;
import com.kitfox.swf.tags.SWFTag;
import com.kitfox.swf.tags.SWFTagLoader;
import java.io.IOException;

/**
 *
 * @author kitfox
 */
public class DefineScalingGrid extends SWFTag
{
    public static class Reader extends SWFTagLoader
    {
        public Reader()
        {
            super(TAG_ID);
        }

        public SWFTag read(SWFDataReader in, int length) throws IOException
        {
            int characterId = in.getUI16();
            RECT splitter = in.getRECT();
            return new DefineScalingGrid(characterId, splitter);
        }
    }

    public static final int TAG_ID = 78;

    int characterId;
    RECT splitter;

    public DefineScalingGrid(int characterId, RECT splitter)
    {
        this.characterId = characterId;
        this.splitter = splitter;
    }

    public int getTagId()
    {
        return TAG_ID;
    }
}
