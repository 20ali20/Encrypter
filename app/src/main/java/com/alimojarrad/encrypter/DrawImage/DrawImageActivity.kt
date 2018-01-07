package com.alimojarrad.encrypter.DrawImage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.alimojarrad.encrypter.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class DrawImage : Activity() {
    var mymap = HashMap<String, String>()
    lateinit var myColors: ArrayList<String>
    var myMsg = ArrayList<String>()


    companion object {
        fun startActivity(context : Context, hexMap : HashMap<String,String>, msgArray : ArrayList<String>){
            val intent = Intent(context, DrawImage::class.java)
            intent.putExtra("hexMap",hexMap)
            intent.putExtra("msgArray",msgArray)
            (context as? Activity)?.startActivity(intent)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val d = Draw2d(this)
        setContentView(d)
        val intent = intent
        mymap = intent.getSerializableExtra("hexMap") as HashMap<String, String>
        myMsg = intent.getSerializableExtra("msgArray") as ArrayList<String>

        myColors = ArrayList()


        myMsg?.let { message ->
            mymap?.let { map ->
                for (i in 0 until message.size) {
                    myColors.add(map[message[i]]!!)

                }
            }


        }


    }


    inner class Draw2d(context: Context) : View(context) {

        init {
            isDrawingCacheEnabled = true

        }


        override fun onDraw(c: Canvas) {


            var canvas: Canvas? = null
            var fos: FileOutputStream? = null
            val file: File
            val myDir: File
            var bmpBase: Bitmap? = null


            val toDisk: Bitmap? = null
            var i = 0
            var cc = 0
            var kk = 0


            val paint = Paint()


            bmpBase = Bitmap.createBitmap(c.width, c.height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bmpBase!!)
            canvas.drawColor(Color.WHITE)


            for (j in myColors.indices) {

                var m = 0
                while (m < c.width - 90 && i < myColors.size) {

                    // String string = mymap.get(chars[i]).toString();
                    // Log.i(chars[i], mymap.get(chars[i]));
                    val string = myColors[i]

                    val color = Color.parseColor(string)
                    val test = Integer.toHexString(color)
                    paint.color = color
                    paint.style = Paint.Style.FILL

                    canvas.drawRect((10 + cc).toFloat(), (10 + kk).toFloat(), (110 + cc).toFloat(), (110 + kk).toFloat(), paint)
                    c.drawRect((10 + cc).toFloat(), (10 + kk).toFloat(), (110 + cc).toFloat(), (110 + kk).toFloat(), paint)



                    cc = cc + 100
                    i++
                    m = m + 100

                }
                cc = 0
                kk = kk + 100
            }



            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
            var fOut: OutputStream? = null

            val fname = "Enc-${Calendar.getInstance().time}.png"
            val filez = File(path, fname) // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            try {

                var stream: OutputStream? = null
                stream = FileOutputStream(filez)
                bmpBase.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()

            } catch (e: IOException) // Catch the exception
            {
                e.printStackTrace()
            }


            super.onDraw(c)
            val enc2Intent = Intent(this@DrawImage, MainActivity::class.java)
            startActivity(enc2Intent)
            finish()
        }
    }


}
