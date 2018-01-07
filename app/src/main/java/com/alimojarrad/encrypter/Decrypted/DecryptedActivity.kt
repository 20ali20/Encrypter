package com.alimojarrad.encrypter.Decrypted

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alimojarrad.encrypter.Encrypt.EncryptActivity
import com.alimojarrad.encrypter.R
import kotlinx.android.synthetic.main.activity_decrypted.*

class DecryptedActivity : AppCompatActivity() {

    internal var message = ""

    companion object {
        fun startActivity(context : Context, message : String){
            val intent = Intent(context, DecryptedActivity::class.java)
            intent.putExtra("message",message)
            (context as? Activity)?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypted)
        val intent = intent
        message = intent.getSerializableExtra("message") as String
        dectxt.text = message

        btn_reply.setOnClickListener {
            EncryptActivity.startActivity(this)
        }

        decrypted_back.setOnClickListener {
            finish()
        }
    }



}
