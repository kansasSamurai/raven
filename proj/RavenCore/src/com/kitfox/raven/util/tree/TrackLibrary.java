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

package com.kitfox.raven.util.tree;

import com.kitfox.raven.util.service.ServiceInst;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author kitfox
 */
public class TrackLibrary extends NodeObject
{

//    public static final String CHILD_TRACKS = "tracks";
//    public final ChildWrapperList<NodeRoot, Track>
//            tracks =
//            new ChildWrapperList(this,
//            CHILD_TRACKS, Track.class);

    public static final String PROP_FPS = "fps";
    public final PropertyWrapperFloat<TrackLibrary> fps =
            new PropertyWrapperFloat(this, PROP_FPS, 
            PropertyWrapper.FLAGS_NOANIM, 30f);

    public static final String PROP_FRAMECUR = "frameCur";
    public final PropertyWrapperInteger<TrackLibrary> frameCur =
            new PropertyWrapperInteger(this, PROP_FRAMECUR,
            PropertyWrapper.FLAGS_NOANIM, 0);

    public static final String PROP_FRAMEFIRST = "frameFirst";
    public final PropertyWrapperInteger<TrackLibrary> frameFirst =
            new PropertyWrapperInteger(this, PROP_FRAMEFIRST,
            PropertyWrapper.FLAGS_NOANIM, 0);

    public static final String PROP_FRAMELAST = "frameLast";
    public final PropertyWrapperInteger<TrackLibrary> frameLast =
            new PropertyWrapperInteger(this, PROP_FRAMELAST,
            PropertyWrapper.FLAGS_NOANIM, 100);

//    public static final String PROP_CURTRACK = "curTrack";
//    public final PropertyWrapper<TrackLibrary, Track> curTrack =
//            new PropertyWrapper(this, PROP_CURTRACK,
//            PropertyWrapper.FLAGS_NOANIM, Track.class);

    public static final String PROP_LOOP = "loop";
    public final PropertyWrapperBoolean<TrackLibrary> loop =
            new PropertyWrapperBoolean(this, PROP_LOOP,
            PropertyWrapper.FLAGS_NOANIM, false);


    protected TrackLibrary(int uid)
    {
        super(uid);

        CurFrameUpdater updater = new CurFrameUpdater();
        frameCur.addPropertyWrapperListener(updater);
//        curTrack.addPropertyWrapperListener(updater);
    }


    public void synchSymbolToFrame()
    {
//        Track track = getCurTrack();
//        if (track == null)
//        {
//            return;
//        }

        FrameSynch synch = new FrameSynch(getFrameCur());
        NodeSymbol sym = getSymbol();
        if (sym != null)
        {
            sym.getRoot().visit(synch);
        }
    }

    public int getFrameCur()
    {
        return frameCur.getValue();
    }

    public void setFrameCur(int value)
    {
        frameCur.setValue(value);
    }

    public int getFrameFirst()
    {
        return frameFirst.getValue();
    }

    public void setFrameFirst(int value)
    {
        frameFirst.setValue(value);
    }

    public int getFrameLast()
    {
        return frameLast.getValue();
    }

    public void setFrameLast(int value)
    {
        frameLast.setValue(value);
    }

    public float getFps()
    {
        return fps.getValue();
    }

    public void setFps(float value)
    {
        fps.setValue(value);
    }

//    public int getCurTrackUid()
//    {
//        PropertyData<Track> data = curTrack.getData();
//        if (data instanceof PropertyDataReference)
//        {
//            return ((PropertyDataReference)data).getUid();
//        }
//        return -1;
//    }
//
//    public Track getCurTrack()
//    {
//        PropertyData<Track> data = curTrack.getData();
//        if (data instanceof PropertyDataReference)
//        {
//            NodeSymbol doc = getSymbol();
//            if (doc == null)
//            {
//                return null;
//            }
//
//            int uid = ((PropertyDataReference)data).getUid();
//            return (Track)doc.getNode(uid);
//        }
//        return null;
//    }

    //----------------------------------------

    class FrameSynch implements NodeVisitor
    {
        final int frame;
//        final int trackUid;

        public FrameSynch(int frame)
        {
            this.frame = frame;
//            this.trackUid = trackUid;
        }

        @Override
        public void visit(NodeObject node)
        {
            for (PropertyWrapper wrapper: node.getPropertyWrappers())
            {
                wrapper.synchToTrack(frame);
            }
        }
    }

    class CurFrameUpdater implements PropertyWrapperListener
    {

        @Override
        public void propertyWrapperDataChanged(PropertyChangeEvent evt)
        {
            synchSymbolToFrame();
        }

        @Override
        public void propertyWrapperTrackChanged(PropertyTrackChangeEvent evt)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void propertyWrapperTrackKeyChanged(PropertyTrackKeyChangeEvent evt)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

    //-----------------------------------------------

    @ServiceInst(service=NodeObjectProvider.class)
    public static class Provider extends NodeObjectProvider<TrackLibrary>
    {
        public Provider()
        {
            super(TrackLibrary.class, "Track Library", "/icons/node/trackLibrary.png");
        }

        @Override
        public TrackLibrary createNode(int uid)
        {
            return new TrackLibrary(uid);
        }
    }
}
