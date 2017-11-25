package com.exosite.app;

import java.util.Date;

/**
 * Device model
 * Meow
 */
public class Device {

    public Device() {
        // Empty constructor
    }

    // Write In Progress FLAG
    private boolean mWriteInProgress = false;
    synchronized public boolean getWriteInProgress() {
        return mWriteInProgress;
    }
    synchronized public void setWriteInProgress(boolean writeInProgress) {
        mWriteInProgress = writeInProgress;
    }

    // Error Handling
    private String mError = "";
    public String getError() {
        return mError;
    }
    public void setError(String error) {
        mError = error;
    }

    // OUTSIDE
    private Date mOutUpdate = null;
    public Date getOutUpdate() {
        return mOutUpdate;
    }
    public void setOutUpdate(Date lastUpdate) {
        mOutUpdate = lastUpdate;
    }

    private Double mTemOut = null;
    public Double getTempOut() {
        return mTemOut;
    }
    public void setTempOut(Double temp) {
        mTemOut = temp;
    }

    private Double mTemOutDiff = null;
    public Double getTempOutDiff() {
        return mTemOutDiff;
    }
    public void setTempOutDiff(Double temp) {
        mTemOutDiff = temp;
    }

    private Double mHumOut = null;
    public Double getHumOut() {
        return mHumOut;
    }
    public void setHumOut(Double hum) {
        mHumOut = hum;
    }

    private Double mHumOutDiff = null;
    public Double getHumOutDiff() {
        return mHumOutDiff;
    }
    public void setHumOutDiff(Double hum) {
        mHumOutDiff = hum;
    }

    private Integer mVolOut = null;
    public Integer getVolOut() {
        return mVolOut;
    }
    public void setVolOut(Integer vol) {
        mVolOut = vol;
    }

    private Integer mVolOutDiff = null;
    public Integer getVolOutDiff() {
        return mVolOutDiff;
    }
    public void setVolOutDiff(Integer vol) {
        mVolOutDiff = vol;
    }


    // LIGHT
    private Date mLigUpdate = null;
    public Date getLigUpdate() {
        return mLigUpdate;
    }
    public void setLigUpdate(Date lastUpdate) {
        mLigUpdate = lastUpdate;
    }

    private Double mLigOut = null;
    public Double getLigOut() {
        return mLigOut;
    }
    public void setLigOut(Double lig) {
        mLigOut = lig;
    }

    private Double mLigOutDiff = null;
    public Double getLigOutDiff() {
        return mLigOutDiff;
    }
    public void setLigOutDiff(Double lig) {
        mLigOutDiff = lig;
    }

    private Integer mUvOut = null;
    public Integer getUvOut() {
        return mUvOut;
    }
    public void setUvOut(Integer uv) {
        mUvOut = uv;
    }

    private Integer mUvOutDiff = null;
    public Integer getUvOutDiff() {
        return mUvOutDiff;
    }
    public void setUvOutDiff(Integer uv) {
        mUvOutDiff = uv;
    }

    private Integer mVolOut2 = null;
    public Integer getVolOut2() {
        return mVolOut2;
    }
    public void setVolOut2(Integer vol) {
        mVolOut2 = vol;
    }

    private Integer mVolOutDiff2 = null;
    public Integer getVolOutDiff2() {
        return mVolOutDiff2;
    }
    public void setVolOutDiff2(Integer vol) {
        mVolOutDiff2 = vol;
    }


    // PRESSURE (HALLWAY)
    private Date mPreUpdate = null;
    public Date getPreUpdate() {
        return mPreUpdate;
    }
    public void setPreUpdate(Date lastUpdate) {
        mPreUpdate = lastUpdate;
    }

    private Double mPreHal = null;
    public Double getPreHal() {
        return mPreHal;
    }
    public void setPreHal(Double hum) {
        mPreHal = hum;
    }

    private Double mPreHalDiff = null;
    public Double getPreHalDiff() {
        return mPreHalDiff;
    }
    public void setPreHalDiff(Double pre) {
        mPreHalDiff = pre;
    }

    private Double mTemHal = null;
    public Double getTempHal() {
        return mTemHal;
    }
    public void setTempHal(Double temp) {
        mTemHal = temp;
    }

    private Double mTemHalDiff = null;
    public Double getTempHalDiff() {
        return mTemHalDiff;
    }
    public void setTempHalDiff(Double temp) {
        mTemHalDiff = temp;
    }


    // BEDROOM
    private Date mBedUpdate = null;
    public Date getBedUpdate() {
        return mBedUpdate;
    }
    public void setBedUpdate(Date lastUpdate) {
        mBedUpdate = lastUpdate;
    }

    private Double mTemBed = null;
    public Double getTempBed() {
        return mTemBed;
    }
    public void setTempBed(Double temp) {
        mTemBed = temp;
    }

    private Double mTemBedDiff = null;
    public Double getTempBedDiff() {
        return mTemBedDiff;
    }
    public void setTempBedDiff(Double temp) {
        mTemBedDiff = temp;
    }

    private Double mHumBed = null;
    public Double getHumBed() {
        return mHumBed;
    }
    public void setHumBed(Double hum) {
        mHumBed = hum;
    }

    private Double mHumBedDiff = null;
    public Double getHumBedDiff() {
        return mHumBedDiff;
    }
    public void setHumBedDiff(Double hum) {
        mHumBedDiff = hum;
    }


    // BEDROOM2
    private Date mBedUpdate2 = null;
    public Date getBedUpdate2() {
        return mBedUpdate2;
    }
    public void setBedUpdate2(Date lastUpdate) {
        mBedUpdate2 = lastUpdate;
    }

    private Double mTemBed2 = null;
    public Double getTempBed2() {
        return mTemBed2;
    }
    public void setTempBed2(Double temp) {
        mTemBed2 = temp;
    }

    private Double mTemBedDiff2 = null;
    public Double getTempBedDiff2() {
        return mTemBedDiff2;
    }
    public void setTempBedDiff2(Double temp) {
        mTemBedDiff2 = temp;
    }

    private Double mHumBed2 = null;
    public Double getHumBed2() {
        return mHumBed2;
    }
    public void setHumBed2(Double hum) {
        mHumBed2 = hum;
    }

    private Double mHumBedDiff2 = null;
    public Double getHumBedDiff2() {
        return mHumBedDiff2;
    }
    public void setHumBedDiff2(Double hum) {
        mHumBedDiff2 = hum;
    }


    // BATHROOM
    private Date mBatUpdate = null;
    public Date getBatUpdate() {
        return mBatUpdate;
    }
    public void setBatUpdate(Date lastUpdate) {
        mBatUpdate = lastUpdate;
    }

    private Double mTemBat = null;
    public Double getTempBat() {
        return mTemBat;
    }
    public void setTempBat(Double temp) {
        mTemBat = temp;
    }

    private Double mTemBatDiff = null;
    public Double getTempBatDiff() {
        return mTemBatDiff;
    }
    public void setTempBatDiff(Double temp) {
        mTemBatDiff = temp;
    }

    private Double mHumBat = null;
    public Double getHumBat() {
        return mHumBat;
    }
    public void setHumBat(Double hum) {
        mHumBat = hum;
    }

    private Double mHumBatDiff = null;
    public Double getHumBatDiff() {
        return mHumBatDiff;
    }
    public void setHumBatDiff(Double hum) {
        mHumBatDiff = hum;
    }


    // LIVING ROOM
    private Date mLivUpdate = null;
    public Date getLivUpdate() {
        return mLivUpdate;
    }
    public void setLivUpdate(Date lastUpdate) {
        mLivUpdate = lastUpdate;
    }

    private Double mTemLiv = null;
    public Double getTempLiv() {
        return mTemLiv;
    }
    public void setTempLiv(Double temp) {
        mTemLiv = temp;
    }

    private Double mTemLivDiff = null;
    public Double getTempLivDiff() {
        return mTemLivDiff;
    }
    public void setTempLivDiff(Double temp) {
        mTemLivDiff = temp;
    }

    private Double mHumLiv = null;
    public Double getHumLiv() {
        return mHumLiv;
    }
    public void setHumLiv(Double hum) {
        mHumLiv = hum;
    }

    private Double mHumLivDiff = null;
    public Double getHumLivDiff() {
        return mHumLivDiff;
    }
    public void setHumLivDiff(Double hum) {
        mHumLivDiff = hum;
    }


    // LIVING ROOM 2
    private Date mLivUpdate2 = null;
    public Date getLivUpdate2() {
        return mLivUpdate2;
    }
    public void setLivUpdate2(Date lastUpdate) {
        mLivUpdate2 = lastUpdate;
    }

    private Double mTemLiv2 = null;
    public Double getTempLiv2() {
        return mTemLiv2;
    }
    public void setTempLiv2(Double temp) {
        mTemLiv2 = temp;
    }

    private Double mTemLivDiff2 = null;
    public Double getTempLivDiff2() {
        return mTemLivDiff2;
    }
    public void setTempLivDiff2(Double temp) {
        mTemLivDiff2 = temp;
    }

    private Double mHumLiv2 = null;
    public Double getHumLiv2() {
        return mHumLiv2;
    }
    public void setHumLiv2(Double hum) {
        mHumLiv2 = hum;
    }

    private Double mHumLivDiff2 = null;
    public Double getHumLivDiff2() {
        return mHumLivDiff2;
    }
    public void setHumLivDiff2(Double hum) {
        mHumLivDiff2 = hum;
    }

}
