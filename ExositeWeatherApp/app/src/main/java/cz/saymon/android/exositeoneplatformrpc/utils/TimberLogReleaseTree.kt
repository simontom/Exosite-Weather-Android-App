package cz.saymon.android.exositeoneplatformrpc.utils

import android.util.Log

import timber.log.Timber

class TimberLogReleaseTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        // Only log WARN, ERROR, WTF
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
    }

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        // Report caught exceptions to Crashlytics
        if (priority == Log.ERROR && t != null) {
//          Crashlytics.log(t);
        }

        if (isLoggable(tag, priority)) {
            // Message is short enough, does not need to be broken into chunks
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }
        }

        // Split by line, then ensure each line can fit into Log's maximum length
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    companion object {
        private val MAX_LOG_LENGTH = 4000
    }

}
