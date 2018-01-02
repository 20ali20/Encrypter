package com.alimojarrad.encrypter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alimojarrad.encrypter.CreateKey.CreateKeyActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CreateKeyActivity.startActivityForDraft(this)

    }
}
