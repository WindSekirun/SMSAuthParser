package pyxis.uzuki.live.smsauthparser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import pyxis.uzuki.live.richutilskt.utils.isEmpty
import java.util.regex.Pattern


/**
 * SMSAuthParser
 * Class: SMSAuthParser
 * Created by Pyxis on 2017-11-21.
 *
 * Description:
 */

class SMSAuthParser(private val context: Context, private val containsCondition: String, private val action: (String) -> Unit) {

    init {

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

                if (messageBody.contains(containsCondition)) {
                    val m = pattern.matcher(messageBody)
                    while (m.find()) {
                        returnParseValue = m.group()
                    }
                }
            }

            if (!returnParseValue.isEmpty()) {
                action.invoke(returnParseValue)
            }
        }
    };
}