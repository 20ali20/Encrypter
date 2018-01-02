package com.alimojarrad.encrypter.CreateKey

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
            assignColorToLetter(item,key)
        }

        return Key(key)
    }

    private fun assignColorToLetter(currentKey: String,key: HashMap<String,String>){
        val colorValue = generateRandomColor(key)
        if(colorValue.isEmpty()){
            assignColorToLetter(currentKey,key)
        }else{
            key.put(currentKey,colorValue)
        }
    }

    private fun generateRandomColor(key : HashMap<String,String>) : String {
        var letters = "0123456789ABCDEF".split("")
        var color = "#"
        val colorValue = "$color${letters[Math.round(Math.random() * 15).toInt()]}"
        if (!key.containsValue(colorValue)){
            return colorValue
        }else{
            return ""
        }
    }



}