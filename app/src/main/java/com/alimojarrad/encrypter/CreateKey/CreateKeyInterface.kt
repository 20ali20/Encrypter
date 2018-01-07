package com.alimojarrad.encrypter.CreateKey

/**
 * Created by mojar on 12/24/2017.
 */
interface CreateKeyInterface {

    interface View {
        fun showError(reason : String)
        fun showSuccess()
        fun updateList(keys : Key)

    }

    interface Presenter {
        fun saveKey()
        fun addToKeys(key : String, value : String)
        fun generateRandomKey() : Key
        fun updateKey(key : Key)
    }
}