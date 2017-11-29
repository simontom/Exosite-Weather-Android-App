package cz.saymon.android.exositeoneplatformrpctest.model

object Constants {

    val BASE_SERVER_URL = "http://m2.exosite.com/onep:v1/rpc/"
    val CIK = "8a6b7465ef593b47821c2a4f2ff7b1e1d2cb77b3"

    private val ALIAS_TEMPBED = "temBed"
    private val ALIAS_HUMBED = "humBed"
    private val ALIAS_TEMPBAT = "temBat"
    private val ALIAS_HUMBAT = "humBat"
    private val ALIAS_TEMPLIV = "temLiv"
    private val ALIAS_HUMLIV = "humLiv"
    private val ALIAS_TEMPHAL = "temHal"
    private val ALIAS_PREHAL = "preHal"
    private val ALIAS_TEMPOUT = "temOut"
    private val ALIAS_HUMOUT = "humOut"
    private val ALIAS_VOLOUT = "volOut"

    val ALIASES = arrayOf(
            ALIAS_TEMPBED, ALIAS_HUMBED,
            ALIAS_TEMPBAT, ALIAS_HUMBAT,
            ALIAS_TEMPLIV, ALIAS_HUMLIV,
            ALIAS_TEMPHAL, ALIAS_PREHAL,
            ALIAS_TEMPOUT, ALIAS_HUMOUT, ALIAS_VOLOUT)

}
