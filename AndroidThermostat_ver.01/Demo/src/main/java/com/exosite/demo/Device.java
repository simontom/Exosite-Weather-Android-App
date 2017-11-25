package com.exosite.demo;

import java.util.Date;

/**
 * Device model.
 */
public class Device {

    public Device() {
        //mSwitch = null;
    }

    private boolean mWriteInProgress = false;
    public boolean getWriteInProgress() {
        return mWriteInProgress;
    }
    public void setWriteInProgress(boolean writeInProgress) {
        mWriteInProgress = writeInProgress;
    }

    private String mError = "";
    public String getError() {
        return mError;
    }
    public void setError(String error) {
        mError = error;
    }


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


    private Date mPreUpdate = null;
    public Date getPreUpdate() {
        return mPreUpdate;
    }
    public void setPreUpdate(Date lastUpdate) {
        mPreUpdate = lastUpdate;
    }

    private Double mPreOut = null;
    public Double getPreOut() {
        return mPreOut;
    }
    public void setPreOut(Double hum) {
        mPreOut = hum;
    }

    private Double mPreOutDiff = null;
    public Double getPreOutDiff() {
        return mPreOutDiff;
    }
    public void setPreOutDiff(Double pre) {
        mPreOutDiff = pre;
    }


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


    private Integer mSwitch = null;
    public Integer getSwitch() {
        return mSwitch;
    }
    public void setSwitchFromCloud(Integer value) {
        if (!this.getWriteInProgress()) {
            mSwitch = value;
        }
    }
    public void setSwitchFromUI(Integer value) {
        mSwitch = value;
    }

    final int SLIDER_LOW = 0;
    final int SLIDER_HIGH = 100;

    private Double mPwmR = null;
    public Double getPwmR() {
        return mPwmR;
    }
    public Double getPwmRAsPercent() {
        // translate setpoint range to 0-100
        Double val = ((mPwmR - SLIDER_LOW) / (SLIDER_HIGH - SLIDER_LOW)) * 100.0;
        return new Double(Math.round(val));
    }
    public void setPwmR(Double setpoint) {
        mPwmR = setpoint;
    }
    public void setPwmRFromPercent(int percent) {
        setPwmR(new Double(Math.round((percent / 100.0) * (SLIDER_HIGH - SLIDER_LOW) + SLIDER_LOW)));
    }

    private Double mPwmG = null;
    public Double getPwmG() {
        return mPwmG;
    }
    public Double getPwmGAsPercent() {
        // translate setpoint range to 0-100
        Double val = ((mPwmG - SLIDER_LOW) / (SLIDER_HIGH - SLIDER_LOW)) * 100.0;
        return new Double(Math.round(val));
    }
    public void setPwmG(Double setpoint) {
        mPwmG = setpoint;
    }
    public void setPwmGFromPercent(int percent) {
        setPwmG(new Double(Math.round((percent / 100.0) * (SLIDER_HIGH - SLIDER_LOW) + SLIDER_LOW)));
    }

    private Double mPwmB = null;
    public Double getPwmB() {
        return mPwmB;
    }
    public Double getPwmBAsPercent() {
        // translate setpoint range to 0-100
        Double val = ((mPwmB - SLIDER_LOW) / (SLIDER_HIGH - SLIDER_LOW)) * 100.0;
        return new Double(Math.round(val));
    }
    public void setPwmB(Double setpoint) {
        mPwmB = setpoint;
    }
    public void setPwmBFromPercent(int percent) {
        setPwmB(new Double(Math.round((percent / 100.0) * (SLIDER_HIGH - SLIDER_LOW) + SLIDER_LOW)));
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

}
