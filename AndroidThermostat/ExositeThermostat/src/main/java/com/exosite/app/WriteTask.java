package com.exosite.app;

import android.os.AsyncTask;
import android.util.Log;

import com.exosite.onepv1.HttpRPCRequestException;
import com.exosite.onepv1.HttpRPCResponseException;
import com.exosite.onepv1.OnePlatformException;
import com.exosite.onepv1.OnePlatformRPC;
import com.exosite.onepv1.Result;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by SaymonRey on 14.12.2015.
 * Meow
 */
public class WriteTask extends AsyncTask<String, Integer, ArrayList<Result>> {
    private static final String TAG = "WriteTask";
    protected Exception exception = null;

    protected static String prepareRequestBody(String... values) {
        String requestBody =
                "{\"auth\":{\"cik\":\"" + Constants.mCIK + "\"}," +
                        "\"calls\":[";

        for (int i = 0; i < values.length; i += 2) {
            String alias = values[i];
            String value = values[i + 1];

            requestBody +=
                    "{\"id\":\"" + alias + "\"," +
                            "\"procedure\":\"write\"," +
                            "\"arguments\":[{\"alias\":\"" + alias + "\"}," + "\"" + value + "\"]}";

            // Are we pointing to the last alias?
            if (i != values.length - 2) {
                requestBody += ',';
            }
        }

        requestBody += "]}";

        Log.d(TAG, requestBody);
        return requestBody;
    }

    @Override
    protected void onPreExecute() {
        if (PlaceholderFragment.mDevice != null) {
            PlaceholderFragment.mDevice.setWriteInProgress(true);
        }
    }

    // Pass two values per alias to write -- alias followed by value to write
    // For example "foo", "1", "bar", "2"
    @Override
    protected ArrayList<Result> doInBackground(String... values) {
        if ((values.length % 2) != 0) {
            return null;
        }

        OnePlatformRPC rpc = new OnePlatformRPC();
        String responseBody = null;
        try {
            responseBody = rpc.callRPC(prepareRequestBody(values));
            Log.d(TAG, responseBody);
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

    @Override
    protected void onPostExecute(ArrayList<Result> results) {
        if (PlaceholderFragment.mDevice != null) {
            PlaceholderFragment.mDevice.setWriteInProgress(false);
        }
    }

}
