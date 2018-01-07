package com.alimojarrad.encrypter.CreateKey

import android.content.Context
import java.io.File
import java.io.FileOutputStream


/**
 * Created by mojar on 12/24/2017.
 */
class CreateKeyPresenter(val view: CreateKeyInterface.View, val context : Context) :CreateKeyInterface.Presenter {
    var keyMap = HashMap<String,String>()
    override fun saveKey() {
        if(keyMap.size != KeyGeneration.alphabet.size){
            view?.showError("Key is incomplete")
        }else{
            val path = context.getExternalFilesDir(null)
            val letDirectory = File("//sdcard//Download//")
            letDirectory.mkdirs()
            val file = File(letDirectory, "Key.txt")
            FileOutputStream(file).use {
                it.write(keyMap.values.toString().toByteArray())
            }
            view?.showSuccess()

        }
    }

    override fun addToKeys(key: String, value: String) {
        if (keyMap.containsValue(value)){
            view?.showError("This color is already chosen for another letter. Please choose another one!")
        }else{
            keyMap.put(key,value)
        }

    }

    override fun generateRandomKey(): Key {
        var randomKey = KeyGeneration.generateRandomKey()
        keyMap = randomKey.keys
        return randomKey
    }

    override fun updateKey(key: Key) {
        val keys = key.keys
        if(keys.size != KeyGeneration.alphabet.size){
            view?.showError("Key is incomplete")
        }else{
           keyMap = keys
           view?.updateList(key)
        }
    }
}