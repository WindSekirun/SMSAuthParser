package pyxis.uzuki.live.smsauthparser.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pyxis.uzuki.live.richutilskt.utils.alert
import pyxis.uzuki.live.smsauthparser.AuthOption
import pyxis.uzuki.live.smsauthparser.SMSAuthParser

class MainActivity : AppCompatActivity() {
    private val parser = SMSAuthParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authOption = AuthOption.Builder()
                .setContext(this) // necessary
                .setContainsCondition("Steam") // optional
                .setParsingRegex("[0-9]+") // optional, default is [0-9]+
                .setCallback { this@MainActivity.alert("Parsing Result: %s".format(it)) } // necessary
                .build()

        parser.initialize(authOption)
    }

    override fun onDestroy() {
        super.onDestroy()
        parser.release()
    }
}
