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
                .setContext(this)
                .setContainsCondition("인증")
                .setCallback { this@MainActivity.alert("번호: %s".format(it)) }
                .build()

        parser.initialize(authOption)
    }

    override fun onPause() {
        super.onPause()
        parser.release()
    }
}
