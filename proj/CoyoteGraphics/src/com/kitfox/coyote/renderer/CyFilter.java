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

import com.kitfox.coyote.shape.CyRectangle2i;

/**
 *
 * @author kitfox
 */
public interface CyFilter
{
    /**
     * Determine the sampling tile region based on the input tile.
     *
     * @param tileRegion Region that this filter will map to
     * @param samplingRegion Will set to the area of the region that this filter will
     * need to read from to compute the dest tile.  If null, a Rectangle
     * will be allocated.
     * @return A rectangle with the area required to sample from to compute
     * the dest region.
     */
    public CyRectangle2i calcSampleRegion(CyRectangle2i tileRegion, CyRectangle2i samplingRegion);

    /**
     * Uses the sampling image as a data source and writes the result to
     * targetImg.  Note that targetImg already contains the combined image
     * data for all layers below this one, so you'll need to merge this filter
     * with it rather than overwrite it.
     *
     * @param samplingImg Tile of data that is input to this filter
     * @param sampArea Region samplingImage was calculated from
     * @param targetImg Destination image to combine filtered image with.
     * @param tgtArea Region of targetImg that will be written to
     */
    public void apply(CyFramebuffer samplingImg, CyRectangle2i sampArea,
            CyFramebuffer targetImg, CyRectangle2i tgtArea);
}
