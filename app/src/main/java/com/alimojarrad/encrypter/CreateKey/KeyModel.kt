package com.alimojarrad.encrypter.CreateKey

import android.graphics.Color
import android.util.Log



/**
 * Created by mojar on 12/24/2017.
 */

data class Key (
        var keys : HashMap<String,String>
)

object KeyGeneration{
    val alphabet = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "?", ".", "SPACE")






    fun generateRandomKey() : Key{
        var key = HashMap<String,String>()

        alphabet.forEach { item->
            Log.e("item",item)
            assignColorToLetter(item,key)
        }

        Log.e("key",key.toString())
        return Key(key)
    }

    fun initKeyMap() : HashMap<String,String>{
        var map = HashMap<String,String>()
        alphabet.forEach { item->
            map.put(item,"")
        }
        return map
    }

    private fun assignColorToLetter(currentKey: String,key: HashMap<String,String>){

        var flag = true
        var colorValue = ""
        while(flag){
            colorValue = generateRandomColor(key)
            if(colorValue.isNotEmpty()){
                Log.e("color ",colorValue)
                flag = false
            }else{
                Log.e("noColor ",colorValue)
            }
        }
        key.put(currentKey,colorValue)

    }

    private fun generateRandomColor(key : HashMap<String,String>) : String {

        var hexColor = getRandomHexValue()
        try {
            val color = Color.parseColor("#"+hexColor)
            // color is a valid color
            if(key.contains(hexColor)){
                hexColor = ""
            }
        } catch (iae: IllegalArgumentException) {
            // This color string is not valid
            hexColor = ""
        }


        return hexColor

    }

    private fun getRandomHexValue() : String{
        val r = Math.round(Math.random() * 255).toInt()
        val g = Math.round(Math.random() * 255).toInt()
        val b = Math.round(Math.random() * 255).toInt()
        val hex = "FF"+String.format("%02x%02x%02x", r, g,b);
        return hex.toUpperCase()
    }



}