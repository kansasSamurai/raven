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

package com.kitfox.coyote.renderer.jogl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author kitfox
 */
public class TexSourceURIAWT extends TexSourceAWT
{
    private final URI uri;
    Color knockoutColor;
    SoftReference<BufferedImage> imgRef;

    public TexSourceURIAWT(URI uri, Color knockoutColor)
    {
        this.uri = uri;
        this.knockoutColor = knockoutColor;
    }

    @Override
    protected BufferedImage getImg()
    {
        BufferedImage img = imgRef == null ? null : imgRef.get();
        if (img == null)
        {
            try
            {
                img = ImageIO.read(new File(uri));
                img = clearKnockoutColor(img, knockoutColor);

                imgRef = new SoftReference<BufferedImage>(img);
            } catch (IOException ex)
            {
                Logger.getLogger(TexSourceURIAWT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return img;
    }

    /**
     * @return the uri
     */
    public URI getUri()
    {
        return uri;
    }

}
