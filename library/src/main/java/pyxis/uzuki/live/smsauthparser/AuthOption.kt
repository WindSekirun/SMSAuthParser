package pyxis.uzuki.live.smsauthparser

import android.content.Context
import pyxis.uzuki.live.richutilskt.impl.F1
import java.util.regex.Pattern

class AuthOption() {
    var context: Context? = null
    var containsCondition: String = ""
    var callback: (String) -> Unit = {}
    var parsingRegex = Pattern.compile("[0-9]+")

    constructor(context: Context?, containsCondition: String, callback: (String) -> Unit,
                parsingRegex: Pattern = Pattern.compile("[0-9]+")) : this() {
        this.context = context
        this.containsCondition = containsCondition
        this.callback = callback
        this.parsingRegex = parsingRegex
    }

    class Builder {
        private var context: Context? = null
        private var containsCondition = ""
        private var callback: (String) -> Unit = {}
        private var parsingRegex = Pattern.compile("[0-9]+")

        fun setContext(context: Context) = apply { this.context = context }
        fun setContainsCondition(containsCondition: String) = apply { this.containsCondition = containsCondition }
        fun setCallback(callback: (String) -> Unit) = apply { this.callback = callback }
        fun setCallback(callback: F1<String>) = apply { this.callback = { callback.invoke(it) } }
        fun setParsingRegex(regexStr: String) = apply { this.parsingRegex = Pattern.compile(regexStr) }
        fun build() = AuthOption(context, containsCondition, callback)
    }
}