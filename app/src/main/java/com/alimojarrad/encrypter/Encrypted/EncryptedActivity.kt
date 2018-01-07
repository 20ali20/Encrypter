package com.alimojarrad.encrypter.Encrypted

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.alimojarrad.encrypter.R
import kotlinx.android.synthetic.main.activity_encrypted.*

class EncryptedActivity : Activity() {
    //implements View.OnClickListener

    internal val RQS_LOADIMAGE = 0
    internal val RQS_SENDEIMAGE = 1


    internal var imageUri: Uri? = null
    internal var image: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted)





        encrypted_attachimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(intent, RQS_LOADIMAGE)
        }



        encrypted_sendmms.setOnClickListener {
            if (encrypted_number.text.toString() != "") {
                if (encrypted_message.text.toString() != "") {
                    if (image == true) {

                        try {
                            val intent = Intent(Intent.ACTION_SEND)

                            intent.putExtra("address", encrypted_number.text.toString())
                            intent.putExtra("sms_body", encrypted_message.text.toString())

                            if (imageUri != null) {
                                intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                                intent.setType("image/png")
                            } else {
                                intent.setType("plain/text")
                            }
                            startActivity(Intent.createChooser(intent, "Select picture:"))

                        } catch (e: Exception) {
                            // TODO: handle exception
                            e.printStackTrace()
                        }

                    } else {
                        toast("Upload an Image first!")
                    }


                } else {
                    toast("Please Enter a message first!")
                }

            } else {
                toast("Please Enter a number first!")
            }
        }

    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RQS_LOADIMAGE -> {
                    imageUri = data.getData()
                    image = true
                }
                RQS_SENDEIMAGE -> {
                }
            }

        }

    }


}

