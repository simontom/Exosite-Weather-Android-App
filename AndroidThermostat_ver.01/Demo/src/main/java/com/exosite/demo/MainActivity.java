package com.exosite.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exosite.onepv1.HttpRPCRequestException;
import com.exosite.onepv1.HttpRPCResponseException;
import com.exosite.onepv1.OnePlatformException;
import com.exosite.onepv1.OnePlatformRPC;
import com.exosite.onepv1.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    // TI device CIK
    static String mCIK = "8a6b7465ef593b47821c2a4f2ff7b1e1d2cb77b3";

    // Device model
    static Device mDevice = new Device();
    SharedPreferences.OnSharedPreferenceChangeListener listener;


    private void updateFromSettings() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mCIK = sharedPreferences.getString(SettingsActivity.KEY_PREF_DEVICE_CIK, "8a6b7465ef593b47821c2a4f2ff7b1e1d2cb77b3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PlaceholderFragment frag = new PlaceholderFragment();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.container, frag, PlaceholderFragment.FRAGMENT_TAG).commit();
        } else if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }

        // set up preferences/settings
        updateFromSettings();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.d(TAG, "called onSharedPreferenceChanged()");
                updateFromSettings();
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        // remove Exosite from title
        setTitle("SaymonRey's WeatherStation");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (mDevice.getTempOut() != null) {
            savedInstanceState.putDouble("temOut", mDevice.getTempOut());
        }
        if (mDevice.getTempOutDiff() != null) {
            savedInstanceState.putDouble("temOutDiff", mDevice.getTempOutDiff());
        }
        if (mDevice.getHumOut() != null) {
            savedInstanceState.putDouble("humOut", mDevice.getHumOut());
        }
        if (mDevice.getTempOutDiff() != null) {
            savedInstanceState.putDouble("humOutDiff", mDevice.getHumOutDiff());
        }

        if (mDevice.getPreOut() != null) {
            savedInstanceState.putDouble("preOut", mDevice.getPreOut());
        }
        if (mDevice.getPreOutDiff() != null) {
            savedInstanceState.putDouble("preOutDiff", mDevice.getPreOutDiff());
        }

        if (mDevice.getTempBed() != null) {
            savedInstanceState.putDouble("temBed", mDevice.getTempBed());
        }
        if (mDevice.getTempBedDiff() != null) {
            savedInstanceState.putDouble("temBedDiff", mDevice.getTempBedDiff());
        }
        if (mDevice.getHumBed() != null) {
            savedInstanceState.putDouble("humBed", mDevice.getHumBed());
        }
        if (mDevice.getTempBedDiff() != null) {
            savedInstanceState.putDouble("humBedDiff", mDevice.getHumBedDiff());
        }

        if (mDevice.getTempLiv() != null) {
            savedInstanceState.putDouble("temLiv", mDevice.getTempLiv());
        }
        if (mDevice.getTempLivDiff() != null) {
            savedInstanceState.putDouble("temLivDiff", mDevice.getTempLivDiff());
        }
        if (mDevice.getHumLiv() != null) {
            savedInstanceState.putDouble("humLiv", mDevice.getHumLiv());
        }
        if (mDevice.getTempLivDiff() != null) {
            savedInstanceState.putDouble("humLivDiff", mDevice.getHumLivDiff());
        }

        if (mDevice.getSwitch() != null) {
            savedInstanceState.putInt("switch", mDevice.getSwitch());
        }
        if (mDevice.getPwmR() != null) {
            savedInstanceState.putDouble("pwmR", mDevice.getPwmR());
        }
        if (mDevice.getPwmG() != null) {
            savedInstanceState.putDouble("pwmG", mDevice.getPwmG());
        }
        if (mDevice.getPwmB() != null) {
            savedInstanceState.putDouble("pwmB", mDevice.getPwmB());
        }

        if (mDevice.getVolOut() != null) {
            savedInstanceState.putInt("volOut", mDevice.getVolOut());
        }
        if (mDevice.getVolOutDiff() != null) {
            savedInstanceState.putInt("volOutDiff", mDevice.getVolOutDiff());
        }
    }
    private void restoreInstanceState(Bundle savedInstanceState) {
        mDevice.setTempOut(savedInstanceState.getDouble("temOut"));
        mDevice.setTempOutDiff(savedInstanceState.getDouble("temOutDiff"));
        mDevice.setHumOut(savedInstanceState.getDouble("humOut"));
        mDevice.setHumOutDiff(savedInstanceState.getDouble("humOutDiff"));

        mDevice.setPreOut(savedInstanceState.getDouble("preOut"));
        mDevice.setPreOutDiff(savedInstanceState.getDouble("preOutDiff"));

        mDevice.setTempBed(savedInstanceState.getDouble("temBed"));
        mDevice.setTempBedDiff(savedInstanceState.getDouble("temBedDiff"));
        mDevice.setHumBed(savedInstanceState.getDouble("humBed"));
        mDevice.setHumBedDiff(savedInstanceState.getDouble("humBedDiff"));

        mDevice.setTempLiv(savedInstanceState.getDouble("temLiv"));
        mDevice.setTempLivDiff(savedInstanceState.getDouble("temLivDiff"));
        mDevice.setHumLiv(savedInstanceState.getDouble("humLiv"));
        mDevice.setHumLivDiff(savedInstanceState.getDouble("humLivDiff"));

        mDevice.setSwitchFromUI(savedInstanceState.getInt("switch"));
        mDevice.setPwmR(savedInstanceState.getDouble("pwmR"));
        mDevice.setPwmG(savedInstanceState.getDouble("pwmG"));
        mDevice.setPwmB(savedInstanceState.getDouble("pwmB"));

        mDevice.setVolOut(savedInstanceState.getInt("volOut"));
        mDevice.setVolOutDiff(savedInstanceState.getInt("volOutDiff"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                int RESULT_SETTINGS = 1;
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        //private static final String TAG = "PlaceholderFragment";
        public static final String FRAGMENT_TAG = "PLACEHOLDER_FRAGMENT";

        protected TextView mOutUpdate;
        protected TextView mTempOut;
        protected TextView mHumOut;

        protected TextView mPreUpdate;
        protected TextView mPreOut;

        protected TextView mBedUpdate;
        protected TextView mTempBed;
        protected TextView mHumBed;

        protected TextView mBatUpdate;
        protected TextView mTempBat;
        protected TextView mHumBat;

        protected TextView mLivUpdate;
        protected TextView mTempLiv;
        protected TextView mHumLiv;

        protected CompoundButton mSwitch;
        protected SeekBar mPwmR;
        protected SeekBar mPwmG;
        protected SeekBar mPwmB;
        protected TextView mPwmRGBText;

        protected TextView mVolOut;

        Handler mReadHandler = new Handler();
        Runnable mReadRunnable;
        String lastToast;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setRetainInstance(true);

            mOutUpdate = (TextView) rootView.findViewById(R.id.outsideUpdate);
            mTempOut = (TextView)rootView.findViewById(R.id.tempOut);
            mHumOut = (TextView)rootView.findViewById(R.id.humOut);

            mPreUpdate = (TextView) rootView.findViewById(R.id.pressureUpdate);
            mPreOut = (TextView)rootView.findViewById(R.id.preOut);

            mBedUpdate = (TextView) rootView.findViewById(R.id.bedroomUpdate);
            mTempBed = (TextView)rootView.findViewById(R.id.tempBed);
            mHumBed = (TextView)rootView.findViewById(R.id.humBed);

            mBatUpdate = (TextView) rootView.findViewById(R.id.bathroomUpdate);
            mTempBat = (TextView)rootView.findViewById(R.id.tempBat);
            mHumBat = (TextView)rootView.findViewById(R.id.humBat);

            mLivUpdate = (TextView) rootView.findViewById(R.id.livingUpdate);
            mTempLiv = (TextView)rootView.findViewById(R.id.tempLiv);
            mHumLiv = (TextView)rootView.findViewById(R.id.humLiv);

            mSwitch = (CompoundButton)rootView.findViewById(R.id.switch_control);
            mPwmR = (SeekBar)rootView.findViewById(R.id.pwmR);
            mPwmG = (SeekBar)rootView.findViewById(R.id.pwmG);
            mPwmB = (SeekBar)rootView.findViewById(R.id.pwmB);
            mPwmRGBText = (TextView)rootView.findViewById(R.id.pwmRGBText);

            mVolOut = (TextView)rootView.findViewById(R.id.volOut);

            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String strVal = b ? "1" : "0";
                    Integer intVal = b ? 1 : 0;
                    mDevice.setSwitchFromUI(intVal);
                    mDevice.setWriteInProgress(true);
                    new WriteTask().execute(ALIAS_SWITCH, strVal);
                }

            });

            mPwmR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                    if (mDevice.getPwmR() != null) {
                        mDevice.setPwmRFromPercent(i);
                    }
                    updateWidgets();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mDevice.setWriteInProgress(true);
                    new WriteTask().execute(ALIAS_PWMR, String.valueOf(mDevice.getPwmR()));
                }
            });

            mPwmG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                    if (mDevice.getPwmG() != null) {
                        mDevice.setPwmGFromPercent(i);
                    }
                    updateWidgets();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mDevice.setWriteInProgress(true);
                    new WriteTask().execute(ALIAS_PWMG, String.valueOf(mDevice.getPwmG()));
                }
            });

            mPwmB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                    if (mDevice.getPwmB() != null) {
                        mDevice.setPwmBFromPercent(i);
                    }
                    updateWidgets();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mDevice.setWriteInProgress(true);
                    new WriteTask().execute(ALIAS_PWMB, String.valueOf(mDevice.getPwmB()));
                }
            });

            // configure to update widgets from platform periodically
            mReadRunnable = new Runnable() {
                @Override
                public void run() {
                    new ReadTask().execute();
                }
            };

            // sync widgets with model
            updateWidgets();

            // start worker thread for reading from OneP
            new ReadTask().execute();
            return rootView;
        }

        void displayError() {
            // show a brief message if it hasn't already been shown
            String err = mDevice.getError();
            if (!err.equals(lastToast)) {
                if (err.length() > 0) {
                    Context ctx = getView().getContext();
                    Toast.makeText(ctx, err, Toast.LENGTH_LONG).show();
                }
                lastToast = err;
            }
        }

        void updateWidgets() {
            if (getActivity() == null || mTempOut == null || mPwmR == null) return;

            //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            final SimpleDateFormat df = new SimpleDateFormat("dd/MM HH:mm:ss");

            Date outUpdate = mDevice.getOutUpdate();
            Double tempOut = mDevice.getTempOut();
            Double tempOutDiff = mDevice.getTempOutDiff();
            Double humOut = mDevice.getHumOut();
            Double humOutDiff = mDevice.getHumOutDiff();

            Date preUpdate = mDevice.getPreUpdate();
            Double preOut = mDevice.getPreOut();
            Double preOutDiff = mDevice.getPreOutDiff();

            Date bedUpdate = mDevice.getBedUpdate();
            Double tempBed = mDevice.getTempBed();
            Double tempBedDiff = mDevice.getTempBedDiff();
            Double humBed = mDevice.getHumBed();
            Double humBedDiff = mDevice.getHumBedDiff();

            Date batUpdate = mDevice.getBatUpdate();
            Double tempBat = mDevice.getTempBat();
            Double tempBatDiff = mDevice.getTempBatDiff();
            Double humBat = mDevice.getHumBat();
            Double humBatDiff = mDevice.getHumBatDiff();

            Date livUpdate = mDevice.getLivUpdate();
            Double tempLiv = mDevice.getTempLiv();
            Double tempLivDiff = mDevice.getTempLivDiff();
            Double humLiv = mDevice.getHumLiv();
            Double humLivDiff = mDevice.getHumLivDiff();

            Integer switchState = mDevice.getSwitch();
            Double pwmR = mDevice.getPwmR();
            Double pwmG = mDevice.getPwmG();
            Double pwmB = mDevice.getPwmB();

            Integer volOut = mDevice.getVolOut();
            Integer volOutDiff = mDevice.getVolOutDiff();

            // Info OUTSIDE
            if (outUpdate == null) {
                mOutUpdate.setText("--");
            } else {
                mOutUpdate.setText("  " + df.format(outUpdate));
            }

            if (tempOut == null) {
                mTempOut.setText("--");
            } else {
                mTempOut.setText(tempOut + "°C   " + String.format("%+.1f", tempOutDiff) + "°C");
            }

            if ((tempOutDiff != null) && (tempOutDiff >= 0.3d)) {
                mTempOut.setBackgroundColor(getResources().getColor(R.color.warming));
            }
            else if ((tempOutDiff != null) && (tempOutDiff <= -0.3d)) {
                mTempOut.setBackgroundColor(getResources().getColor(R.color.cooling));
            }
            else {
                mTempOut.setBackgroundColor(getResources().getColor(R.color.stable));
            }

            if (humOut == null) {
                mHumOut.setText("--");
            } else {
                mHumOut.setText(humOut + "%   " + String.format("%+.1f", humOutDiff) + "%");
            }

            // Info PRESSURE
            if (preUpdate == null) {
                mPreUpdate.setText("--");
            } else {
                mPreUpdate.setText("  " + df.format(preUpdate));
            }

            if (preOut == null) {
                mPreOut.setText("--");
            } else {
                mPreOut.setText(String.format("%.1f", preOut) + "hPa   " + String.format("%+.1f", preOutDiff) + "hPa");
            }

            // Info BEDROOM
            if (bedUpdate == null) {
                mBedUpdate.setText("--");
            } else {
                mBedUpdate.setText("  " + df.format(bedUpdate));
            }

            if (tempBed == null) {
                mTempBed.setText("--");
            } else {
                mTempBed.setText(tempBed + "°C   " + String.format("%+.1f", tempBedDiff) + "°C");
            }

            if ((tempBedDiff != null) && (tempBedDiff >= 0.3d)) {
                mTempBed.setBackgroundColor(getResources().getColor(R.color.warming));
            }
            else if ((tempBedDiff != null) && (tempBedDiff <= -0.3d)) {
                mTempBed.setBackgroundColor(getResources().getColor(R.color.cooling));
            }
            else {
                mTempBed.setBackgroundColor(getResources().getColor(R.color.stable));
            }

            if (humBed == null) {
                mHumBed.setText("--");
            } else {
                mHumBed.setText(humBed + "%   " + String.format("%+.1f", humBedDiff) + "%");
            }

            // Info BATHROOM
            if (batUpdate == null) {
                mBatUpdate.setText("--");
            } else {
                mBatUpdate.setText("  " + df.format(batUpdate));
            }

            if (tempBat == null) {
                mTempBat.setText("--");
            } else {
                mTempBat.setText(tempBat + "°C   " + String.format("%+.1f", tempBatDiff) + "°C");
            }

            if ((tempBatDiff != null) && (tempBatDiff >= 0.3d)) {
                mTempBat.setBackgroundColor(getResources().getColor(R.color.warming));
            }
            else if ((tempBatDiff != null) && (tempBatDiff <= -0.3d)) {
                mTempBat.setBackgroundColor(getResources().getColor(R.color.cooling));
            }
            else {
                mTempBat.setBackgroundColor(getResources().getColor(R.color.stable));
            }

            if (humBat == null) {
                mHumBat.setText("--");
            } else {
                mHumBat.setText(humBat + "%   " + String.format("%+.1f", humBatDiff) + "%");
            }

            // Info LIVING ROOM
            if (batUpdate == null) {
                mLivUpdate.setText("--");
            } else {
                mLivUpdate.setText("  " + df.format(livUpdate));
            }

            if (tempBat == null) {
                mTempLiv.setText("--");
            } else {
                mTempLiv.setText(tempLiv + "°C   " + String.format("%+.1f", tempLivDiff) + "°C");
            }

            if ((tempLivDiff != null) && (tempLivDiff >= 0.3d)) {
                mTempLiv.setBackgroundColor(getResources().getColor(R.color.warming));
            }
            else if ((tempLivDiff != null) && (tempLivDiff <= -0.3d)) {
                mTempLiv.setBackgroundColor(getResources().getColor(R.color.cooling));
            }
            else {
                mTempLiv.setBackgroundColor(getResources().getColor(R.color.stable));
            }

            if (humLiv == null) {
                mHumLiv.setText("--");
            } else {
                mHumLiv.setText(humLiv + "%   " + String.format("%+.1f", humLivDiff) + "%");
            }

            // Info SWITCH, PWM RGB
            if (switchState == null) {
                mSwitch.setChecked(false);
            } else {
                mSwitch.setChecked(switchState != 0);
            }

            if ((pwmR == null) || (pwmG == null) || (pwmB == null)) {
                // if setpoint hasn't been set, leave setpoint slider as it is
                mPwmR.setEnabled(false);
                mPwmG.setEnabled(false);
                mPwmB.setEnabled(false);
                mPwmRGBText.setText("");
            } else {
                mPwmR.setEnabled(true);
                mPwmR.setProgress(mDevice.getPwmRAsPercent().intValue());

                mPwmG.setEnabled(true);
                mPwmG.setProgress(mDevice.getPwmGAsPercent().intValue());

                mPwmB.setEnabled(true);
                mPwmB.setProgress(mDevice.getPwmBAsPercent().intValue());

                mPwmRGBText.setText(String.format("R:%.0f  G:%.0f  B:%.0f", pwmR, pwmG, pwmB));
            }

            // DISABLE EASILY PWM SLIDERS AND SWITCH
            final boolean enabled = true;
            mPwmR.setEnabled(enabled);
            mPwmG.setEnabled(enabled);
            mPwmB.setEnabled(enabled);
            mSwitch.setEnabled(enabled);
            if (!enabled) {
                mPwmRGBText.setText("PWM Control DISABLED");
            }

            // Info VOLTAGE OUTSIDE
            if (volOut == null) {
                mVolOut.setText("--");
            } else {
                mVolOut.setText(volOut + "mV   " + volOutDiff + "mV");
            }

            if ((volOut != null) && (volOut < 3800)) {
                mVolOut.setBackgroundColor(getResources().getColor(R.color.warming));
            }
            else {
                mVolOut.setBackgroundColor(getResources().getColor(R.color.appBlue2));
            }

        }

        private final String ALIAS_TEMPOUT = "temOut";
        private final String ALIAS_HUMOUT = "humOut";
        private final String ALIAS_PREOUT = "preOut";

        private final String ALIAS_TEMPBED = "temBed";
        private final String ALIAS_HUMBED = "humBed";

        private final String ALIAS_TEMPBAT = "temBat";
        private final String ALIAS_HUMBAT = "humBat";

        private final String ALIAS_TEMPLIV = "temLiv";
        private final String ALIAS_HUMLIV = "humLiv";

        private final String ALIAS_SWITCH = "switch";
        private final String ALIAS_PWMR = "pwmR";
        private final String ALIAS_PWMG = "pwmG";
        private final String ALIAS_PWMB = "pwmB";

        private  final String ALIAS_VOLOUT = "volOut";

        class ReadTask extends AsyncTask<Void, Integer, ArrayList<Result>> {
            private static final String TAG = "ReadTask";
            private final String[] aliases = {ALIAS_TEMPOUT, ALIAS_HUMOUT, ALIAS_PREOUT, ALIAS_TEMPBED,
                    ALIAS_HUMBED, ALIAS_TEMPBAT, ALIAS_HUMBAT, ALIAS_TEMPLIV, ALIAS_HUMLIV,
                    ALIAS_SWITCH, ALIAS_PWMR, ALIAS_PWMG, ALIAS_PWMB, ALIAS_VOLOUT};
            private Exception exception;

            protected ArrayList<Result> doInBackground(Void... params) {
                exception = null;
                // call to OneP
                OnePlatformRPC rpc = new OnePlatformRPC();
                String responseBody = null;
                try {
                    String requestBody = "{\"auth\":{\"cik\":\"" + mCIK
                            + "\"},\"calls\":[";
                    for (String alias: aliases) {
                        requestBody += "{\"id\":\"" + alias + "\",\"procedure\":\"read\","
                            + "\"arguments\":[{\"alias\":\"" + alias + "\"},"
                            + "{\"limit\":2,\"sort\":\"desc\"}]}";
                        if (!alias.equals(aliases[aliases.length - 1])) {
                            requestBody += ',';
                        }
                    }
                    requestBody += "]}";
                    Log.v(TAG, requestBody);
                    // do this just to check for JSON parse errors on client side
                    // while debugging. it can be removed for production.
                    JSONObject jo = new JSONObject(requestBody);
                    responseBody = rpc.callRPC(requestBody);

                    Log.v(TAG, responseBody);
                } catch (JSONException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught JSONException before sending request. Message:" + e.getMessage());
                } catch (HttpRPCRequestException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught HttpRPCRequestException " + e.getMessage());
                } catch (HttpRPCResponseException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught HttpRPCResponseException " + e.getMessage());
                }

                if (responseBody != null) {
                    try {
                        ArrayList<Result> results = rpc.parseResponses(responseBody);
                        return results;
                    } catch (OnePlatformException e) {
                        this.exception = e;
                        Log.e(TAG, "Caught OnePlatformException " + e.getMessage());
                    } catch (JSONException e) {
                        this.exception = e;
                        Log.e(TAG, "Caught JSONException " + e.getMessage());
                    }
                }
                return null;
            }

            // this is executed on UI thread when doInBackground
            // returns a result
            protected void onPostExecute(ArrayList<Result> results) {
                boolean hasError = false;
                if (results != null) {
                    for(int i = 0; i < results.size(); i++) {
                        Result result = results.get(i);
                        String alias = aliases[i];
                        if (result.getResult() instanceof JSONArray) {
                            try {
                                JSONArray points = ((JSONArray)result.getResult());
                                if (points.length() > 0) {
                                    JSONArray pointLast = points.getJSONArray(0);
                                    JSONArray pointBefore = points.getJSONArray(1);
                                    // this will break if results are out of order.
                                    // need to fix OnePlatformRPC.java
                                    if (alias.equals(ALIAS_TEMPOUT)) {
                                        Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp
                                        mDevice.setOutUpdate(lastUpdate);
                                        mDevice.setTempOut(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setTempOutDiff(diff);
                                    } else if (alias.equals(ALIAS_HUMOUT)) {
                                        mDevice.setHumOut(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setHumOutDiff(diff);
                                    } else if (alias.equals(ALIAS_PREOUT)) {
                                        Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp
                                        mDevice.setPreUpdate(lastUpdate);
                                        mDevice.setPreOut(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setPreOutDiff(diff);
                                    } else if (alias.equals(ALIAS_TEMPBED)) {
                                        Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp
                                        mDevice.setBedUpdate(lastUpdate);
                                        mDevice.setTempBed(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setTempBedDiff(diff);
                                    } else if (alias.equals(ALIAS_HUMBED)) {
                                        mDevice.setHumBed(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setHumBedDiff(diff);
                                    } else if (alias.equals(ALIAS_TEMPBAT)) {
                                        Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp
                                        mDevice.setBatUpdate(lastUpdate);
                                        mDevice.setTempBat(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setTempBatDiff(diff);
                                    } else if (alias.equals(ALIAS_HUMBAT)) {
                                        mDevice.setHumBat(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setHumBatDiff(diff);
                                    } else if (alias.equals(ALIAS_TEMPLIV)) {
                                        Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp
                                        mDevice.setLivUpdate(lastUpdate);
                                        mDevice.setTempLiv(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setTempLivDiff(diff);
                                    } else if (alias.equals(ALIAS_HUMLIV)) {
                                        mDevice.setHumLiv(pointLast.getDouble(1));
                                        Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                                        diff = Math.round(diff * 100.0) / 100.0;
                                        mDevice.setHumLivDiff(diff);
                                    } else if (alias.equals(ALIAS_SWITCH)) {
                                        if (mDevice.getSwitch() == null) {
                                            mDevice.setSwitchFromCloud(pointLast.getInt(1));
                                        }
                                    } else if (alias.equals(ALIAS_PWMR)) {
                                        if (mDevice.getPwmR() == null) {
                                            mDevice.setPwmR(pointLast.getDouble(1));
                                        }
                                    } else if (alias.equals(ALIAS_PWMG)) {
                                        if (mDevice.getPwmG() == null) {
                                            mDevice.setPwmG(pointLast.getDouble(1));
                                        }
                                    } else if (alias.equals(ALIAS_PWMB)) {
                                        if (mDevice.getPwmB() == null) {
                                            mDevice.setPwmB(pointLast.getDouble(1));
                                        }
                                    } else if (alias.equals(ALIAS_VOLOUT)) {
                                        mDevice.setVolOut(pointLast.getInt(1));
                                        Integer diff = pointLast.getInt(1) - pointBefore.getInt(1);
                                        mDevice.setVolOutDiff(diff);
                                    }
                                } else {
                                    hasError = true;
                                    if (alias.equals(ALIAS_TEMPOUT)) {
                                        mDevice.setTempOut(null);
                                        mDevice.setError("No temperature outside values.");
                                    } else if (alias.equals(ALIAS_HUMOUT)) {
                                        mDevice.setHumOut(null);
                                        mDevice.setError("No humidity outside value");
                                    } else if (alias.equals(ALIAS_PREOUT)) {
                                        mDevice.setPreOut(null);
                                        mDevice.setError("No pressure value");
                                    } else if (alias.equals(ALIAS_TEMPBED)) {
                                        mDevice.setTempBed(null);
                                        mDevice.setError("No temperature bedroom values.");
                                    } else if (alias.equals(ALIAS_HUMBED)) {
                                        mDevice.setHumBed(null);
                                        mDevice.setError("No humidity bedroom value");
                                    } else if (alias.equals(ALIAS_TEMPBAT)) {
                                        mDevice.setTempBat(null);
                                        mDevice.setError("No temperature bathroom values.");
                                    } else if (alias.equals(ALIAS_HUMBAT)) {
                                        mDevice.setHumBat(null);
                                        mDevice.setError("No humidity bathroom value");
                                    } else if (alias.equals(ALIAS_TEMPLIV)) {
                                        mDevice.setTempBat(null);
                                        mDevice.setError("No temperature bathroom values.");
                                    } else if (alias.equals(ALIAS_HUMLIV)) {
                                        mDevice.setHumBat(null);
                                        mDevice.setError("No humidity bathroom value");
                                    } else if (alias.equals(ALIAS_SWITCH)) {
                                        mDevice.setSwitchFromCloud(null);
                                        mDevice.setError("No switch value");
                                    } else if (alias.equals(ALIAS_PWMR)) {
                                        mDevice.setPwmR(null);
                                        mDevice.setError("No pwmR value");
                                    } else if (alias.equals(ALIAS_PWMG)) {
                                        mDevice.setPwmG(null);
                                        mDevice.setError("No pwmG value");
                                    } else if (alias.equals(ALIAS_PWMB)) {
                                        mDevice.setPwmB(null);
                                        mDevice.setError("No pwmB value");
                                    } else if (alias.equals(ALIAS_VOLOUT)) {
                                        mDevice.setVolOut(null);
                                        mDevice.setError("No voltage outside value");
                                    }
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSONException getting the result: " + e.getMessage());
                            }
                        } else {
                            Log.e(TAG, result.getStatus() + ' ' + result.getResult().toString());
                        }
                    }
                    updateWidgets();

                } else {
                    Log.e(TAG, "null result in ReadTask.onPostExecute()");
                    if (this.exception instanceof OnePlatformException) {
                        mDevice.setError("Received error from platform");
                    } else {
                        mDevice.setError("Unable to connect to platform");
                    }
                    hasError = true;
                }
                if (!hasError) {
                    mDevice.setError("");
                } else {
                    displayError();
                }

                final long delayTime = 44 * 60 * 1000; // ms
                mReadHandler.postDelayed(mReadRunnable, delayTime);
            }
        }

        class WriteTask extends AsyncTask<String, Integer, ArrayList<Result>> {
            private static final String TAG = "WriteTask";
            protected Exception exception = null;
            // pass two values per alias to write -- alias followed by value to write
            // for example "foo", "1", "bar", "2"
            protected ArrayList<Result> doInBackground(String... values) {
                assert(values.length % 2 == 0);
                OnePlatformRPC rpc = new OnePlatformRPC();
                String responseBody = null;
                try {
                    String requestBody = "{\"auth\":{\"cik\":\"" + mCIK
                            + "\"},\"calls\":[";
                    for (int i = 0; i < values.length; i += 2) {
                        String alias = values[i];
                        String value = values[i + 1];
                        requestBody += "{\"id\":\"" + alias + "\",\"procedure\":\"write\","
                                + "\"arguments\":[{\"alias\":\"" + alias + "\"},"
                                + "\"" + value + "\"]}";
                        // are we pointing to the last alias?
                        if (i != values.length - 2) {
                            requestBody += ',';
                        }
                    }
                    requestBody += "]}";
                    Log.d(TAG, requestBody);
                    // do this just to check for JSON parse errors on client side
                    // while debugging. it can be removed for production.
                    JSONObject jo = new JSONObject(requestBody);
                    responseBody = rpc.callRPC(requestBody);

                    Log.d(TAG, responseBody);
                } catch (JSONException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught JSONException before sending request. Message:" + e.getMessage());
                } catch (HttpRPCRequestException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught HttpRPCRequestException " + e.getMessage());
                } catch (HttpRPCResponseException e) {
                    this.exception = e;
                    Log.e(TAG, "Caught HttpRPCResponseException " + e.getMessage());
                }

                if (responseBody != null) {
                    try {
                        ArrayList<Result> results = rpc.parseResponses(responseBody);
                        return results;
                    } catch (OnePlatformException e) {
                        this.exception = e;
                        Log.e(TAG, "Caught OnePlatformException " + e.getMessage());
                    } catch (JSONException e) {
                        this.exception = e;
                        Log.e(TAG, "Caught JSONException " + e.getMessage());
                    }
                }
                return null;
            }

            // this is executed on UI thread when doInBackground
            // returns a result
            protected void onPostExecute(ArrayList<Result> results) {
                mDevice.setWriteInProgress(false);
            }
        }

    }

}
