package cz.saymon.android.exositeoneplatformrpc.model

object Constants {

    val BASE_SERVER_URL = "http://m2.exosite.com/onep:v1/rpc/"
    val CIK = "8a6b7465ef593b47821c2a4f2ff7b1e1d2cb77b3"

    val ALIAS_TEMPBED = "temBed"
    val ALIAS_HUMBED = "humBed"
    val ALIAS_TEMPBAT = "temBat"
    val ALIAS_HUMBAT = "humBat"
    val ALIAS_TEMPLIV = "temLiv"
    val ALIAS_HUMLIV = "humLiv"
    val ALIAS_TEMPHAL = "temHal"
    val ALIAS_PREHAL = "preHal"
    val ALIAS_TEMPOUT = "temOut"
    val ALIAS_HUMOUT = "humOut"
    val ALIAS_VOLOUT = "volOut"

    val ALIAS_TEMPBED2 = "temBed2"
    val ALIAS_HUMBED2 = "humBed2"
    val ALIAS_TEMPLIV2 = "temLiv2"
    val ALIAS_HUMLIV2 = "humLiv2"
    val ALIAS_LIGOUT = "ligOut"
    val ALIAS_UVOUT = "uvOut"
    val ALIAS_VOLOUT2 = "volOut2"

    val ALIAS_PWMR = "pwmR"
    val ALIAS_PWMG = "pwmG"
    val ALIAS_PWMB = "pwmB"

    val ALIASES = listOf(
            ALIAS_TEMPBED, ALIAS_HUMBED,
            ALIAS_TEMPBAT, ALIAS_HUMBAT,
            ALIAS_TEMPLIV, ALIAS_HUMLIV,
            ALIAS_TEMPHAL, ALIAS_PREHAL,
            ALIAS_TEMPOUT, ALIAS_HUMOUT, ALIAS_VOLOUT,
            ALIAS_TEMPBED2, ALIAS_HUMBED2)

    val ALIASES_PWM = listOf(ALIAS_PWMR, ALIAS_PWMG, ALIAS_PWMB)

}
