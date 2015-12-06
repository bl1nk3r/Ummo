package com.example.barnes.ummoqmasterdashboard.utils;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;

/**
 * Created by barnes on 10/26/15.
 */
public class CrossfadeWrapper implements ICrossfader
{
    private Crossfader mCrossfader;

    public CrossfadeWrapper(Crossfader crossfader) {
        this.mCrossfader = crossfader;
    }

    @Override
    public void crossfade() {
        mCrossfader.crossFade();
    }

    @Override
    public boolean isCrossfaded() {
        return mCrossfader.isCrossFaded();
    }
}
