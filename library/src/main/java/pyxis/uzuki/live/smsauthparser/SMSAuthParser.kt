@file:Suppress("UNCHECKED_CAST")

package pyxis.uzuki.live.smsauthparser

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsMessage
import android.util.Log
import pyxis.uzuki.live.richutilskt.utils.RPermission
import pyxis.uzuki.live.richutilskt.utils.isEmpty
import pyxis.uzuki.live.richutilskt.utils.tryCatch
import java.util.regex.Pattern


/**
 * SMSAuthParser
 * Class: SMSAuthParser
 * Created by Pyxis on 2017-11-21.
 *
 * Description:
 */

class SMSAuthParser {
    private val permissions = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    private val receiver: SMSReceiver = SMSReceiver()
    private var authOption: AuthOption = AuthOption()

    fun initialize(authOption: AuthOption) {
        RPermission.instance.checkPermission(authOption.context as Context, permissions, { code, _ ->
            if (code == RPermission.PERMISSION_GRANTED) {
                attachBroadcast(authOption)
            }
        })
    }

    private fun attachBroadcast(authOption: AuthOption) {
        this.authOption = authOption
        authOption.context?.registerReceiver(receiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
        Log.i(SMSAuthParser::class.simpleName, "Ready to Receive!")
    }

    fun release() {
        tryCatch { authOption.context?.unregisterReceiver(receiver) }
    }

    inner class SMSReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent == null)
                return

            val bundle = intent.extras
            var returnParseValue = ""

            if (bundle == null)
                return

            val pdus = bundle.get("pdus") as Array<Any>
            val smsMessage = arrayOfNulls<SmsMessage>(pdus.size)
            val pattern = authOption.parsingRegex ?: Pattern.compile("[0-9]+")
            for (i in smsMessage.indices) {
                smsMessage[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                val messageBody = smsMessage[i]?.messageBody ?: ""
                val m = pattern.matcher(messageBody)

                if (!authOption.containsCondition.isEmpty() && !messageBody.contains(authOption.containsCondition, false)) {
                    return
                }

                while (m.find()) {
                    returnParseValue = m.group()
                }
            }

            if (!returnParseValue.isEmpty()) {
                authOption.callback(returnParseValue)
            }
        }
    };
}