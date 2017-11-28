package com.exosite.app;

import android.os.AsyncTask;
import android.util.Log;

import com.exosite.onepv1.HttpRPCRequestException;
import com.exosite.onepv1.HttpRPCResponseException;
import com.exosite.onepv1.OnePlatformException;
import com.exosite.onepv1.OnePlatformRPC;
import com.exosite.onepv1.Result;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SaymonRey on 14.12.2015.
 * Meow
 */
public class ReadTask extends AsyncTask<Void, Void, ArrayList<Result>> {
    private static final String TAG = ReadTask.class.getSimpleName();

    private Exception exception;
    private OnDataDownloadedListener onDataDownloadedListener;


    public interface OnDataDownloadedListener {
        void onDataDownloaded(boolean downloadedWithoutError);
    }

    public ReadTask setOnDataDownloadedListener(OnDataDownloadedListener onDataDownloadedListener) {
        this.onDataDownloadedListener = onDataDownloadedListener;
        return this;
    }

    protected String prepareRequesteBody() {
        String requestBody = "{\"auth\":{\"cik\":\"" + Constants.mCIK + "\"},\"calls\":[";
        for (String alias: Constants.ALIASES) {
            requestBody +=
                    "{\"id\":\"" + alias + "\"," +
                            "\"procedure\":\"read\"," +
                            "\"arguments\":[{\"alias\":\"" + alias + "\"}," +
                            "{\"limit\":2,\"sort\":\"desc\"}]}";

            if (!alias.equals(Constants.ALIASES[Constants.ALIASES.length - 1])) {
                requestBody += ',';
            }
        }
        requestBody += "]}";

        Log.v(TAG, requestBody);
        return requestBody;
    }

    protected void processCorrectData(JSONArray points, int i) throws JSONException {
        JSONArray pointLast = points.getJSONArray(0);
        JSONArray pointBefore = points.getJSONArray(1);

        // This will break if results are out of order. Need to fix OnePlatformRPC.java !!

        switch (Constants.ALIASES[i]) {
            // Data from Outside -- backyard
            /////////////////////////////////
            case Constants.ALIAS_TEMPOUT: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setOutUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setTempOut(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempOutDiff(diff);
                break;
            }
            case Constants.ALIAS_HUMOUT: {
                PlaceholderFragment.mDevice.setHumOut(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumOutDiff(diff);
                break;
            }
            case Constants.ALIAS_VOLOUT: {
                PlaceholderFragment.mDevice.setVolOut(pointLast.getInt(1));
                Integer diff = pointLast.getInt(1) - pointBefore.getInt(1);
                PlaceholderFragment.mDevice.setVolOutDiff(diff);
                break;
            }

            // Data from Veranda -- just Pressure
            //////////////////////////////////////
            case Constants.ALIAS_PREHAL: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setPreUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setPreOut(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setPreOutDiff(diff);
                break;
            }

            // Data from LIGHT
            //////////////////////
            case Constants.ALIAS_LIGOUT: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setLigUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setLigOut(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setLigOutDiff(diff);
                break;
            }
            case Constants.ALIAS_UVOUT: {
                PlaceholderFragment.mDevice.setUvOut(pointLast.getInt(1));
                Integer diff = pointLast.getInt(1) - pointBefore.getInt(1);
                PlaceholderFragment.mDevice.setUvOutDiff(diff);
                break;
            }
            case Constants.ALIAS_VOLOUT2: {
                PlaceholderFragment.mDevice.setVolOut2(pointLast.getInt(1));
                Integer diff = pointLast.getInt(1) - pointBefore.getInt(1);
                PlaceholderFragment.mDevice.setVolOutDiff2(diff);
                break;
            }

            // Data from MY BEDROOM
            /////////////////////////
            case Constants.ALIAS_TEMPBED: {
                Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setBedUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setTempBed(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempBedDiff(diff);
                break;
            }
            case Constants.ALIAS_HUMBED: {
                PlaceholderFragment.mDevice.setHumBed(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumBedDiff(diff);
                break;
            }

            // Data from KIDS' BEDROOM
            ////////////////////////////
            case Constants.ALIAS_TEMPBED2: {
                Date lastUpdate = new Date((long)(pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setBedUpdate2(lastUpdate);
                PlaceholderFragment.mDevice.setTempBed2(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempBedDiff2(diff);
                break;
            }
            case Constants.ALIAS_HUMBED2: {
                PlaceholderFragment.mDevice.setHumBed2(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumBedDiff2(diff);
                break;
            }

            // Data from MY BATHROOM
            //////////////////////////
            case Constants.ALIAS_TEMPBAT: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setBatUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setTempBat(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempBatDiff(diff);
                break;
            }
            case Constants.ALIAS_HUMBAT: {
                PlaceholderFragment.mDevice.setHumBat(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumBatDiff(diff);
                break;
            }

            // Data from SISTER'S LIVING ROOM
            ///////////////////////////////////
            case Constants.ALIAS_TEMPLIV: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setLivUpdate(lastUpdate);
                PlaceholderFragment.mDevice.setTempLiv(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempLivDiff(diff);
                break;
            }
            case Constants.ALIAS_HUMLIV: {
                PlaceholderFragment.mDevice.setHumLiv(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumLivDiff(diff);
                break;
            }

            // Data from MOM'S LIVING ROOM
            /////////////////////////////////
            case Constants.ALIAS_TEMPLIV2: {
                Date lastUpdate = new Date((long) (pointLast.getDouble(0) * 1000)); // Gets Date from Timestamp

                PlaceholderFragment.mDevice.setLivUpdate2(lastUpdate);
                PlaceholderFragment.mDevice.setTempLiv2(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setTempLivDiff2(diff);
                break;
            }
            case Constants.ALIAS_HUMLIV2: {
                PlaceholderFragment.mDevice.setHumLiv2(pointLast.getDouble(1));
                Double diff = pointLast.getDouble(1) - pointBefore.getDouble(1);
                diff = Math.round(diff * 100.0) / 100.0;
                PlaceholderFragment.mDevice.setHumLivDiff2(diff);
                break;
            }
        }
    }

    protected void processIncorrectData(int i) {
        switch (Constants.ALIASES[i]) {
            // Data from Outside -- backyard
            /////////////////////////////////
            case Constants.ALIAS_TEMPOUT:
                PlaceholderFragment.mDevice.setTempOut(null);
                PlaceholderFragment.mDevice.setError("No temperature outside values.");
                break;
            case Constants.ALIAS_HUMOUT:
                PlaceholderFragment.mDevice.setHumOut(null);
                PlaceholderFragment.mDevice.setError("No humidity outside value");
                break;
            case Constants.ALIAS_VOLOUT:
                PlaceholderFragment.mDevice.setVolOut(null);
                PlaceholderFragment.mDevice.setError("No voltage outside value");
                break;

            // Data from Veranda -- just Pressure
            //////////////////////////////////////
            case Constants.ALIAS_PREHAL:
                PlaceholderFragment.mDevice.setPreOut(null);
                PlaceholderFragment.mDevice.setError("No pressure value");
                break;

            // Data from MY BEDROOM
            /////////////////////////
            case Constants.ALIAS_TEMPBED:
                PlaceholderFragment.mDevice.setTempBed(null);
                PlaceholderFragment.mDevice.setError("No temperature bedroom values.");
                break;
            case Constants.ALIAS_HUMBED:
                PlaceholderFragment.mDevice.setHumBed(null);
                PlaceholderFragment.mDevice.setError("No humidity bedroom value");
                break;

            // Data from KIDS' BEDROOM
            ////////////////////////////
            case Constants.ALIAS_TEMPBED2:
                PlaceholderFragment.mDevice.setTempBed2(null);
                PlaceholderFragment.mDevice.setError("No temperature bedroom 2 values.");
                break;
            case Constants.ALIAS_HUMBED2:
                PlaceholderFragment.mDevice.setHumBed2(null);
                PlaceholderFragment.mDevice.setError("No humidity bedroom 2 value");
                break;

            // Data from MY BATHROOM
            //////////////////////////
            case Constants.ALIAS_TEMPBAT:
                PlaceholderFragment.mDevice.setTempBat(null);
                PlaceholderFragment.mDevice.setError("No temperature bathroom values.");
                break;
            case Constants.ALIAS_HUMBAT:
                PlaceholderFragment.mDevice.setHumBat(null);
                PlaceholderFragment.mDevice.setError("No humidity bathroom value");
                break;

            // Data from SISTER'S LIVING ROOM
            ///////////////////////////////////
            case Constants.ALIAS_TEMPLIV:
                PlaceholderFragment.mDevice.setTempLiv(null);
                PlaceholderFragment.mDevice.setError("No temperature living room values.");
                break;
            case Constants.ALIAS_HUMLIV:
                PlaceholderFragment.mDevice.setHumLiv(null);
                PlaceholderFragment.mDevice.setError("No humidity living room value");
                break;

            // Data from MOM'S LIVING ROOM
            /////////////////////////////////
            case Constants.ALIAS_TEMPLIV2:
                PlaceholderFragment.mDevice.setTempLiv2(null);
                PlaceholderFragment.mDevice.setError("No temperature living room 2 values.");
                break;
            case Constants.ALIAS_HUMLIV2:
                PlaceholderFragment.mDevice.setHumLiv2(null);
                PlaceholderFragment.mDevice.setError("No humidity living room 2 value");
                break;

            // Data from LIGHT
            //////////////////////
            case Constants.ALIAS_LIGOUT: {
                PlaceholderFragment.mDevice.setLigOut(null);
                PlaceholderFragment.mDevice.setError("No light value");
                break;
            }
            case Constants.ALIAS_UVOUT: {
                PlaceholderFragment.mDevice.setUvOut(null);
                PlaceholderFragment.mDevice.setError("No UV index value");
                break;
            }
            case Constants.ALIAS_VOLOUT2: {
                PlaceholderFragment.mDevice.setVolOut2(null);
                PlaceholderFragment.mDevice.setError("No voltage light value");
                break;
            }
        }
    }

    @Override
    protected ArrayList<Result> doInBackground(Void... params) {
        exception = null;

        // Call to OneP
        OnePlatformRPC rpc = new OnePlatformRPC();
        String responseBody = null;
        try {
            responseBody = rpc.callRPC(prepareRequesteBody());
            Log.v(TAG, responseBody);
        }
        catch (HttpRPCRequestException e) {
            this.exception = e;
            Log.e(TAG, "Caught HttpRPCRequestException " + e.getMessage());
        }
        catch (HttpRPCResponseException e) {
            this.exception = e;
            Log.e(TAG, "Caught HttpRPCResponseException " + e.getMessage());
        }

        if (responseBody != null) {
            try {
                return OnePlatformRPC.parseResponses(responseBody);
            }
            catch (OnePlatformException e) {
                this.exception = e;
                Log.e(TAG, "Caught OnePlatformException " + e.getMessage());
            }
            catch (JSONException e) {
                this.exception = e;
                Log.e(TAG, "Caught JSONException " + e.getMessage());
            }
        }

        return null;
    }

    // This is executed on UI thread when doInBackground
    // returns a result
    @Override
    protected void onPostExecute(ArrayList<Result> results) {
        boolean hasError = false;
        if (results != null) {
            for(int i = 0; i < results.size(); i++) {
                Result result = results.get(i);
                if (result.getResult() instanceof JSONArray) {
                    try {
                        JSONArray points = ((JSONArray)result.getResult());
                        if (points.length() > 0) {
                            processCorrectData(points, i);
                        }
                        else {
                            hasError = true;
                            processIncorrectData(i);
                        }
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "JSONException getting the result: " + e.getMessage());
                    }
                }
                else {
                    Log.e(TAG, result.getStatus() + ' ' + result.getResult().toString());
                }
            }

            if (onDataDownloadedListener != null) {
                onDataDownloadedListener.onDataDownloaded(true);
            }
        }
        else {
            Log.e(TAG, "null result in ReadTask.onPostExecute()");
            hasError = true;
            if (this.exception instanceof OnePlatformException) {
                PlaceholderFragment.mDevice.setError("Received error from platform");
            }
            else {
                PlaceholderFragment.mDevice.setError("Unable to connect to platform");
            }
        }

        if (!hasError) {
            PlaceholderFragment.mDevice.setError("");
        }
        else if (onDataDownloadedListener != null) {
            onDataDownloadedListener.onDataDownloaded(false);
        }
    }

}