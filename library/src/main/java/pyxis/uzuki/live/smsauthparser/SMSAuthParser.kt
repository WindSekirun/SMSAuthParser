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
    private val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    private val permissions = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    private val receiver: SMSReceiver = SMSReceiver()
    private var authOption: AuthOption = AuthOption()

    fun initialize(authOption: AuthOption) {
        RPermission.instance.checkPermission(authOption.context as Context, permissions, { code, _ ->
            if (code == RPermission.PERMISSION_GRANTED) {
                attachBroadcast()
            }
        })
    }

    private fun attachBroadcast() {
        this.authOption = authOption
        authOption.context?.registerReceiver(receiver, IntentFilter(SMS_RECEIVED))
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
            val pattern = Pattern.compile("[0-9]+")
            for (i in smsMessage.indices) {
                smsMessage[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                val messageBody = smsMessage[i]?.messageBody ?: ""

                if (messageBody.contains(authOption.containsCondition)) {
                    val m = pattern.matcher(messageBody)
                    while (m.find()) {
                        returnParseValue = m.group()
                    }
                }
            }

            if (!returnParseValue.isEmpty()) {
                authOption.callback(returnParseValue)
            }
        }
    };
}