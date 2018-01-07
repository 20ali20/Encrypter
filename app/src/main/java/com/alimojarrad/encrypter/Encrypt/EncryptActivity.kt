package com.alimojarrad.encrypter.Encrypt

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.alimojarrad.encrypter.Common.Steg
import com.alimojarrad.encrypter.Common.Utils
import com.alimojarrad.encrypter.CreateKey.KeyGeneration
import com.alimojarrad.encrypter.DrawImage.DrawImage
import com.alimojarrad.encrypter.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_encrypt.*
import java.io.*
import java.util.*


class EncryptActivity : AppCompatActivity() , EncryptInterface.View {

    private val SELECTED_PICTURE = 2

    private val PICKFILE_RESULT_CODE = 1
    internal var mymsg = ""
    internal var msgArray = ArrayList<String>()
    internal var hexMap = HashMap<String, String>()
    internal var imageOpened  = false
    internal var keyOpened: Boolean? = false
    var image: Bitmap? = null


    companion object {
        fun startActivity(context : Context){
            val intent = Intent(context, EncryptActivity::class.java)
            (context as? Activity)?.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt)

        encrypt_back.setOnClickListener {
            finish()
        }

        encrypt_stegranography.setOnClickListener {
            if(encrypt_message.text.isNotEmpty()){
                if(imageOpened){
                    image?.let { bitmap ->
                        val message = encrypt_message.text.toString()
                        var encodedBitmap = Steg.withInput(bitmap).encode(message).intoBitmap()
                        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
                        var fOut: OutputStream? = null

                        val fname = "Stag-${Calendar.getInstance().time}.png"
                        val filez = File(path, fname) // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                        try {

                            var stream: OutputStream? = null
                            stream = FileOutputStream(filez)
                            encodedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            stream.flush()
                            stream.close()
                            toast("Message is successfully hidden within the text file!")
                            finish()

                        } catch (e: IOException) // Catch the exception
                        {
                            e.printStackTrace()
                            toast("Message was not hidden because ${e.localizedMessage}")
                        }
                    }
                }else{
                    toast("Add an image first!")
                }
            }else{
                toast("Enter a Message first!")
            }
        }

        encrypt_addimage.setOnClickListener {
            var intent =Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.type = "image/*"
            startActivityForResult(intent, SELECTED_PICTURE)
        }

        encrypt_encrypt.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
                readText()


                if (encrypt_message.text.isNotEmpty()) {
                    if (keyOpened == true) {
                        DrawImage.startActivity(this, hexMap, msgArray)
                        finish()
                    } else {
                        toast("Get a Key first!")
                    }
                } else {
                    toast("Enter a Message first!")
                }
            }
        }

        encrypt_key.setOnClickListener {
            RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
                if(it){
                    var intent =Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "*/*"
                    startActivityForResult(intent, PICKFILE_RESULT_CODE)
                }
            }

        }
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }




    private fun readText() {
        mymsg = encrypt_message.text.toString()
        if (mymsg != "") {
            for (i in 0 until mymsg.length) {
                if (mymsg[i].toString() == " ")
                    msgArray.add(i, "SPACE")
                else
                    msgArray.add(i, mymsg[i].toString().toUpperCase())
            }
        }
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
                hexMap.put(KeyGeneration.alphabet[i],"#"+ array[i].replace("\\s".toRegex(), ""))
            }
            keyOpened = true


        } catch (e: FileNotFoundException) {
            Log.e("error","dd",e)
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // TODO Auto-generated method stub
        when (requestCode) {
            PICKFILE_RESULT_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data.data
                    uri?.let {
                        Utils.getPath(this, it)?.let {
                            readFile(it)
                        }

                    }

                }
            }

            SELECTED_PICTURE -> if (resultCode == Activity.RESULT_OK) {
                val uri = data.data
                uri?.let {
                    Utils.getPath(this, it)?.let {
                        image = BitmapFactory.decodeFile(it)
                        imageOpened = true

                    }


                }
            }


        }
    }}






