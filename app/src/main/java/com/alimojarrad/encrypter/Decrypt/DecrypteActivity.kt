package com.alimojarrad.encrypter.Decrypt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alimojarrad.encrypter.Common.Steg
import com.alimojarrad.encrypter.Common.Utils
import com.alimojarrad.encrypter.CreateKey.KeyGeneration
import com.alimojarrad.encrypter.Decrypted.DecryptedActivity
import com.alimojarrad.encrypter.R
import kotlinx.android.synthetic.main.activity_decrypte.*
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class DecrypteActivity : AppCompatActivity() {
    private val SELECTED_PICTURE = 1
    private val PICKFILE_RESULT_CODE = 2

    var colors = ArrayList<String>()
    internal var file: File? = null
    internal var message = ""
    internal var hexMapR = HashMap<String, String>()
    internal var keyOpened: Boolean = false
    internal var imgOpened: Boolean = false
    var image: Bitmap? = null



    companion object {
        fun startActivity(context : Context){
            val intent = Intent(context,DecrypteActivity::class.java)
            (context as? Activity)?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypte)
        colors = ArrayList()


        decrypt_back.setOnClickListener {
            finish()
        }

        decrypt_stegranography.setOnClickListener {
           if(imgOpened){
               image?.let { bitmap ->
                   message = Steg.withInput(bitmap).decode().intoString()
                   DecryptedActivity.startActivity(this,message)

               }
           }else{
               toast("open an image first!")
           }
        }


        selectImg.setOnClickListener {
            var intent =Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, SELECTED_PICTURE)
        }

        decKey.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(intent, PICKFILE_RESULT_CODE)
        }

        dec_btn.setOnClickListener {
            if (keyOpened == true) {
                if (imgOpened == true) {
                    translate()
                    DecryptedActivity.startActivity(this,message)
                    finish()
                } else
                toast("open an image first!")
            } else
            toast("get a key first!")
        }

    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun readFile(filename: String) {

        val f = File(filename)
        val txtData: Scanner
        val array: Array<String>
        var hexVals = ""




        try {
            txtData = Scanner(f)


            while (txtData.hasNext()) {

                val str = txtData.nextLine()
                val bracketIndex1 = str.indexOf('[')

                val value = str.substring(bracketIndex1)
                val bracketIndex2 = str.indexOf(']')

                hexVals = value.substring(bracketIndex1 + 1, bracketIndex2)

            }

            array = hexVals.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in KeyGeneration.alphabet.indices) {
                hexMapR.put(array[i].replace("\\s".toRegex(), ""), KeyGeneration.alphabet[i])


            }

            keyOpened = true


        } catch (e: FileNotFoundException) {

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SELECTED_PICTURE -> if (resultCode == Activity.RESULT_OK) {
                val uri = data.data
                uri?.let {
                    Utils.getPath(this,it)?.let {
                        val yourSelectedImage = BitmapFactory.decodeFile(it)
                        image = yourSelectedImage
                        val d = BitmapDrawable(yourSelectedImage)

                        imageView.background = d
                        // iv.setOnClickListener(onClick1);


                        val b = (imageView.background as BitmapDrawable).bitmap


                        var whitePixelCheck = ""

                        var y = 60
                        while (y < b.height) {

                            var x = 60
                            while (x < b.width) {
                                //
                                whitePixelCheck = String.format("%06X", 0xFFFFFF and b.getPixel(x, y))
                                if (whitePixelCheck != "FFFFFF") {
                                    colors.add("FF"+String.format("%06X", 0xFFFFFF and b.getPixel(x, y)))
                                }
                                x = x + 100


                            }
                            y = y + 100

                        }
                        imgOpened = true                    }

                }




            }

            PICKFILE_RESULT_CODE -> if (resultCode == Activity.RESULT_OK) {

                val uri = data.data
                uri?.let {
                    Utils.getPath(this, it)?.let {
                        readFile(it)
                    }
                }
            }

            else -> {
            }
        }


    }

    private fun translate() {
        for (i in colors.indices) {
            if (hexMapR[colors[i]] == "SPACE")
                message += " "
            else
                message += hexMapR[colors[i]]
        }


    }



}