package cz.saymon.android.exositeoneplatformrpc.utils

import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT
import android.telephony.TelephonyManager.NETWORK_TYPE_EDGE
import cz.saymon.android.exositeoneplatformrpc.App
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStatus @Inject constructor(app: App) {

    private val connectivityManager: ConnectivityManager by lazy {
        app.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val wifiManager: WifiManager by lazy {
        app.getSystemService(WIFI_SERVICE) as WifiManager
    }

    val isOnGoodConnection: Boolean
        get() {
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo?.let { isOnGoodConnection(it) } ?: false
        }

    private fun isOnGoodConnection(netInfo: NetworkInfo): Boolean {
        return netInfo.isConnected && (isOnGoodWiFiConnection(netInfo) || isOnGoodCellularConnection(netInfo))
    }

    private fun isOnGoodWiFiConnection(netInfo: NetworkInfo): Boolean {
        return (netInfo.type == TYPE_WIFI) &&
                (WifiManager.calculateSignalLevel(wifiManager.connectionInfo.rssi, 4) > 0)
    }

    private fun isOnGoodCellularConnection(netInfo: NetworkInfo): Boolean {
        return (netInfo.subtype != NETWORK_TYPE_1xRTT) && (netInfo.subtype != NETWORK_TYPE_EDGE)
    }

}
