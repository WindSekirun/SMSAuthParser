package pyxis.uzuki.live.smsauthparser

import android.content.Context
import pyxis.uzuki.live.richutilskt.impl.F1

data class AuthOption(val context: Context, val containsCondition: String, val callback: (String) -> Unit) {

    class Builder {
        private var context: Context? = null
        private var containsCondition = ""
        private var callback: (String) -> Unit = {}

        fun setContext(context: Context) = apply { this.context = context }
        fun setContainsCondition(containsCondition: String) = apply { this.containsCondition = containsCondition }
        fun setCallback(callback: (String) -> Unit) = apply { this.callback = callback }
        fun setCallback(callback: F1<String>) = apply { this.callback = { callback.invoke(it) } }
        fun build() = AuthOption(context as Context, containsCondition, callback)
    }
}