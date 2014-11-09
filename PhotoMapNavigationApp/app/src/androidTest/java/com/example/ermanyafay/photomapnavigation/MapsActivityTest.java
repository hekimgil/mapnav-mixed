package com.example.ermanyafay.photomapnavigation;

import android.test.ActivityInstrumentationTestCase2;

import com.example.ermanyafay.photomapnavigation.activity.MapsActivity;

/**
 * Created by Erman Yafay on 09.11.2014.
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2<MapsActivity> {

    MapsActivity activity;

    public MapsActivityTest() {
        super(MapsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }
}
