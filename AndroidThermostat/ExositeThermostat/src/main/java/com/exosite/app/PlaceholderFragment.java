package com.exosite.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import cz.saymon.exosite.weatherstation.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    public static final String FRAGMENT_TAG = PlaceholderFragment.class.getSimpleName();

    public static final Device mDevice = new Device();
    public static float lastX;
    String lastToastString;
    ReadTask updateTask = null;

    protected TextView mOutUpdate;
    protected TextView mTempOut;
    protected TextView mHumOut;
    protected TextView mVolOut;

    protected TextView mLigUpdate;
    protected TextView mLigOut;
    protected TextView mUvOut;
    protected TextView mVolOut2;

    protected TextView mPreUpdate;
    protected TextView mPreHal;
    protected TextView mTempHal;

    protected TextView mBedUpdate;
    protected TextView mTempBed;
    protected TextView mHumBed;

    protected TextView mBedUpdate2;
    protected TextView mTempBed2;
    protected TextView mHumBed2;

    protected TextView mBatUpdate;
    protected TextView mTempBat;
    protected TextView mHumBat;

    protected TextView mLivUpdate;
    protected TextView mTempLiv;
    protected TextView mHumLiv;

    protected TextView mLivUpdate2;
    protected TextView mTempLiv2;
    protected TextView mHumLiv2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setRetainInstance(true);

        findAllViews(rootView);
        prepareTabHost(rootView);

        // Sync widgets with Data Model (mDevice)
        updateWidgets();

        updateData();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                int REQUEST_CODE_SETTINGS = 1;
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivityForResult(i, REQUEST_CODE_SETTINGS);
                return true;

            case R.id.action_update:
                updateData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Start worker thread for reading from OneP
    public void updateData() {
        if (updateTask != null) {
            return;
        }

        updateTask = new ReadTask().setOnDataDownloadedListener(new ReadTask.OnDataDownloadedListener() {
            @Override
            public void onDataDownloaded(boolean downloadedWithoutError) {
                if (downloadedWithoutError) {
                    updateWidgets();
                }
                else {
                    displayError();
                }

                updateTask = null;
            }
        });

        updateTask.execute();
    }

    void findAllViews(View rootView) {
        mOutUpdate = (TextView) rootView.findViewById(R.id.outsideUpdate);
        mTempOut = (TextView)rootView.findViewById(R.id.tempOut);
        mHumOut = (TextView)rootView.findViewById(R.id.humOut);
        mVolOut = (TextView)rootView.findViewById(R.id.volOut);

        mLigUpdate = (TextView) rootView.findViewById(R.id.lightUpdate);
        mLigOut = (TextView)rootView.findViewById(R.id.ligOut);
        mUvOut = (TextView)rootView.findViewById(R.id.uvOut);
        mVolOut2 = (TextView)rootView.findViewById(R.id.volOut2);

        mPreUpdate = (TextView) rootView.findViewById(R.id.pressureUpdate);
        mPreHal = (TextView)rootView.findViewById(R.id.preHal);
        mTempHal = (TextView)rootView.findViewById(R.id.tempHal);

        mBedUpdate = (TextView)rootView.findViewById(R.id.bedroomUpdate);
        mTempBed = (TextView)rootView.findViewById(R.id.tempBed);
        mHumBed = (TextView)rootView.findViewById(R.id.humBed);

        mBedUpdate2 = (TextView)rootView.findViewById(R.id.bedroomUpdate2);
        mTempBed2 = (TextView)rootView.findViewById(R.id.tempBed2);
        mHumBed2 = (TextView)rootView.findViewById(R.id.humBed2);

        mBatUpdate = (TextView) rootView.findViewById(R.id.bathroomUpdate);
        mTempBat = (TextView)rootView.findViewById(R.id.tempBat);
        mHumBat = (TextView)rootView.findViewById(R.id.humBat);

        mLivUpdate = (TextView) rootView.findViewById(R.id.livingUpdate);
        mTempLiv = (TextView)rootView.findViewById(R.id.tempLiv);
        mHumLiv = (TextView)rootView.findViewById(R.id.humLiv);

        mLivUpdate2 = (TextView) rootView.findViewById(R.id.livingUpdate2);
        mTempLiv2 = (TextView)rootView.findViewById(R.id.tempLiv2);
        mHumLiv2 = (TextView)rootView.findViewById(R.id.humLiv2);
    }

    void prepareTabHost(View rootView) {
        final TabHost tabHost = (TabHost)rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        View.OnTouchListener listenerChangeTab = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                final float offset = 202;
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        lastX = touchEvent.getX();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        float diff = lastX - touchEvent.getX();
                        boolean moveToLeftTab = (diff < 0);
                        diff = Math.abs(diff);

                        if (diff > offset) {
                            if (moveToLeftTab) {
                                if (tabHost.getCurrentTab() == 0) {    // On First TAB
                                    tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
                                }
                                else {
                                    tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
                                }
                            }
                            else {
                                if (tabHost.getCurrentTab() == (tabHost.getTabWidget().getTabCount() - 1)) {  // On Last TAB
                                    tabHost.setCurrentTab(0);
                                }
                                else {
                                    tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
                                }
                            }
                        }
                        break;
                    }
                }

                return true;
            }
        };

        TabHost.TabSpec ts = tabHost.newTabSpec("Tab1");
        ts.setContent(R.id.tab1);
        ts.setIndicator(getResources().getString(R.string.tab1_label));
        tabHost.addTab(ts);
        LinearLayout linearLayoutTab1 = (LinearLayout)rootView.findViewById(R.id.tab1);
        linearLayoutTab1.setOnTouchListener(listenerChangeTab);

        ts = tabHost.newTabSpec("Tab2");
        ts.setIndicator(getResources().getString(R.string.tab2_label));
        ts.setContent(R.id.tab2);
        tabHost.addTab(ts);
        LinearLayout linearLayoutTab2 = (LinearLayout)rootView.findViewById(R.id.tab2);
        linearLayoutTab2.setOnTouchListener(listenerChangeTab);

        ts = tabHost.newTabSpec("Tab3");
        ts.setIndicator(getResources().getString(R.string.tab3_label));
        ts.setContent(R.id.tab3);
        tabHost.addTab(ts);
        LinearLayout linearLayoutTab3 = (LinearLayout)rootView.findViewById(R.id.tab3);
        linearLayoutTab3.setOnTouchListener(listenerChangeTab);

        // Change BACKGROUND COLOR and TEXT COLOR of Tabs
        try {
            for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                View view = tabHost.getTabWidget().getChildAt(i);
                TextView tv = (TextView)view.findViewById(android.R.id.title);
                if (tv != null) {
                    tv.setTextColor(getResources().getColor(R.color.white));
                    if (Build.VERSION.SDK_INT >= 14) {
                        tv.setAllCaps(false);
                    }
                    tv.setTextSize(16);
                }
                view.setPadding(7, 5, 7, 5);
            }
        } catch (ClassCastException e) {
            // A precaution, in case Google changes from a TextView on the tabs.
            Log.d(FRAGMENT_TAG, "tabHost text color Exception");
        }

        tabHost.setCurrentTab(0);
    }

    void displayError() {
        // Show a brief message if it hasn't already been shown
        String err = mDevice.getError();
        if (!err.equals(lastToastString)) {
            if (err.length() > 0) {
                @SuppressWarnings("ConstantConditions")
                Context ctx = getView().getContext();
                Toast.makeText(ctx, err, Toast.LENGTH_LONG).show();
            }
            lastToastString = err;
        }
    }

    void updateWidgets() {
        if (getActivity() == null) {
            return;
        }

        // Set Info OUTSIDE
        Utils.setUpdateTextView(mOutUpdate, mDevice.getOutUpdate());
        Utils.setTemperatureTextView(getActivity(), mTempOut, mDevice.getTempOut(), mDevice.getTempOutDiff());
        Utils.setHumidityTextView(mHumOut, mDevice.getHumOut(), mDevice.getHumOutDiff());
        Utils.setVoltageTextView(getActivity(), mVolOut, mDevice.getVolOut(), mDevice.getVolOutDiff());

        // Set Info LIGHT
        Utils.setUpdateTextView(mLigUpdate, mDevice.getLigUpdate());
        Utils.setUvIndexTextView(getActivity(), mUvOut, mDevice.getUvOut(), mDevice.getUvOutDiff());
        Utils.setLightTextView(mLigOut, mDevice.getLigOut(), mDevice.getLigOutDiff());
        Utils.setVoltageTextView(getActivity(), mVolOut2, mDevice.getVolOut2(), mDevice.getVolOutDiff2());

        // Set Info PRESSURE (veranda)
        Utils.setUpdateTextView(mPreUpdate, mDevice.getPreUpdate());
        Utils.setPressureTextView(mPreHal, mDevice.getPreHal(), mDevice.getPreHalDiff());
        Utils.setTemperatureTextView(getActivity(), mTempHal, mDevice.getTempHal(), mDevice.getTempHalDiff());

        // Set Info BEDROOM (my bedroom)
        Utils.setUpdateTextView(mBedUpdate, mDevice.getBedUpdate());
        Utils.setTemperatureTextView(getActivity(), mTempBed, mDevice.getTempBed(), mDevice.getTempBedDiff());
        Utils.setHumidityTextView(mHumBed, mDevice.getHumBed(), mDevice.getHumBedDiff());

        // Set Info BEDROOM2 (kids)
        Utils.setUpdateTextView(mBedUpdate2, mDevice.getBedUpdate2());
        Utils.setTemperatureTextView(getActivity(), mTempBed2, mDevice.getTempBed2(), mDevice.getTempBedDiff2());
        Utils.setHumidityTextView(mHumBed2, mDevice.getHumBed2(), mDevice.getHumBedDiff2());

        // Set Info BATHROOM (my bathroom)
        Utils.setUpdateTextView(mBatUpdate, mDevice.getBatUpdate());
        Utils.setTemperatureTextView(getActivity(), mTempBat, mDevice.getTempBat(), mDevice.getTempBatDiff());
        Utils.setHumidityTextView(mHumBat, mDevice.getHumBat(), mDevice.getHumBatDiff());

        // Set Info LIVING ROOM (sister)
        Utils.setUpdateTextView(mLivUpdate, mDevice.getLivUpdate());
        Utils.setTemperatureTextView(getActivity(), mTempLiv, mDevice.getTempLiv(), mDevice.getTempLivDiff());
        Utils.setHumidityTextView(mHumLiv, mDevice.getHumLiv(), mDevice.getHumLivDiff());

        // Set Info LIVING ROOM 2 (mom)
        Utils.setUpdateTextView(mLivUpdate2, mDevice.getLivUpdate2());
        Utils.setTemperatureTextView(getActivity(), mTempLiv2, mDevice.getTempLiv2(), mDevice.getTempLivDiff2());
        Utils.setHumidityTextView(mHumLiv2, mDevice.getHumLiv2(), mDevice.getHumLivDiff2());
    }

}
