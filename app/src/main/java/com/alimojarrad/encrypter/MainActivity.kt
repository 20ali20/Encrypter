package com.alimojarrad.encrypter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alimojarrad.encrypter.CreateKey.CreateKeyActivity
import com.alimojarrad.encrypter.Decrypt.DecrypteActivity
import com.alimojarrad.encrypter.Encrypt.EncryptActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupInteractions()

    }

    private fun setupInteractions() {

        mainactivity_encrypt.setOnClickListener {
            EncryptActivity.startActivity(this)
        }

        mainactivity_decrypt.setOnClickListener {
            DecrypteActivity.startActivity(this)
        }

        mainactivity_createkey.setOnClickListener {
            CreateKeyActivity.startActivity(this)
        }
    }
}
