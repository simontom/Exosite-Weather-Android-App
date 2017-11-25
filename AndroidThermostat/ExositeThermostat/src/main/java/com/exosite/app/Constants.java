package com.exosite.app;

/**
 * Created by SaymonRey on 14.12.2015.
 * Meow
 */
class Constants {
    // TI device CIK
    static final String mCIK = "8a6b7465ef593b47821c2a4f2ff7b1e1d2cb77b3";

    static final String ALIAS_TEMPOUT = "temOut";
    static final String ALIAS_HUMOUT = "humOut";
    static final String ALIAS_VOLOUT = "volOut";

    static final String ALIAS_TEMPHAL = "temHal";
    static final String ALIAS_PREHAL = "preHal";

    static final String ALIAS_LIGOUT = "ligOut";
    static final String ALIAS_UVOUT = "uvOut";
    static final String ALIAS_VOLOUT2 = "volOut2";

    static final String ALIAS_TEMPBED = "temBed";
    static final String ALIAS_HUMBED = "humBed";

    static final String ALIAS_TEMPBED2 = "temBed2";
    static final String ALIAS_HUMBED2 = "humBed2";

    static final String ALIAS_TEMPBAT = "temBat";
    static final String ALIAS_HUMBAT = "humBat";

    static final String ALIAS_TEMPLIV = "temLiv";
    static final String ALIAS_HUMLIV = "humLiv";

    static final String ALIAS_TEMPLIV2 = "temLiv2";
    static final String ALIAS_HUMLIV2 = "humLiv2";

//    static final String ALIAS_SWITCH = "switch";
//    static final String ALIAS_PWMR = "pwmR";
//    static final String ALIAS_PWMG = "pwmG";
//    static final String ALIAS_PWMB = "pwmB";

    static final String[] ALIASES = {
//            ALIAS_SWITCH, ALIAS_PWMR, ALIAS_PWMG, ALIAS_PWMB,
            ALIAS_TEMPOUT, ALIAS_HUMOUT, ALIAS_VOLOUT,
            ALIAS_LIGOUT, ALIAS_UVOUT, ALIAS_VOLOUT2,
            ALIAS_TEMPBED, ALIAS_HUMBED,
            ALIAS_TEMPBAT, ALIAS_HUMBAT,
            ALIAS_TEMPBED2, ALIAS_HUMBED2,
            ALIAS_TEMPLIV, ALIAS_HUMLIV,
            ALIAS_TEMPLIV2, ALIAS_HUMLIV2,
            ALIAS_PREHAL, ALIAS_TEMPHAL
    };


}
