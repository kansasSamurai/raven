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

package com.kitfox.docking;

import com.kitfox.docking.DockingRegionTabbed.PathRecordTabbed;

/**
 *
 * @author kitfox
 */
public interface DockingContainer
{
//    public int getNumDockChildren();

//    public DockingChild getDockChild(int index);

    public DockingRegionSplit split(DockingChild child,
            DockingContent content,
            boolean right, boolean vertical);

    /**
     * Indicates that oldChild has become invalid and should be replaced
     * with newChild.  If newChild is null, indicates oldChild should be
     * removed.
     *
     * @param oldChild
     * @param newChild
     */
    public void join(DockingChild oldChild, DockingChild newChild);

    public DockingPathRecord buildPath(DockingChild dockChild, DockingPathRecord childPath);

    public DockingRegionContainer getContainerRoot();

//    public void minimize(DockingContent content, DockingPathRecord partialPath);
//
//    public void maximize(DockingContent content, DockingPathRecord partialPath);
//
//    public void floatWindow(DockingContent content, DockingPathRecord path);

}
