package pyxis.uzuki.live.smsauthparser

import android.content.Context
import pyxis.uzuki.live.richutilskt.impl.F1

class AuthOption() {
    var context: Context? = null
    var containsCondition: String = ""
    var callback: (String) -> Unit = {}

    constructor(context: Context, containsCondition: String, callback: (String) -> Unit) : this() {
        this.context = context
        this.containsCondition = containsCondition
        this.callback = callback
    }

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