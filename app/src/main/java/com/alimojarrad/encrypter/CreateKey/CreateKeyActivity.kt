package com.alimojarrad.encrypter.CreateKey

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.alimojarrad.encrypter.R
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_create_key.*

class CreateKeyActivity : AppCompatActivity(), CreateKeyInterface.View {

    lateinit var presenter : CreateKeyPresenter
    lateinit var rxPermissions : RxPermissions
    lateinit var disposable : CompositeDisposable
    var currentBackgroundColor  = -0x1

    companion object {
        fun startActivityForDraft(context : Context){
            val intent = Intent(context,CreateKeyActivity::class.java)
            (context as? Activity)?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_key)
        initializeVariables()
        setupViews()
        setupInteractions()
    }


    fun initializeVariables(){
        presenter = CreateKeyPresenter(this,this)
        rxPermissions = RxPermissions(this)
        disposable = CompositeDisposable()

    }

    fun setupViews(){
        val adapter = CustomGrid(this, KeyGeneration.alphabet)
        createkey_grid.adapter = adapter
    }

    fun setupInteractions() {
        disposable.add(
                RxView.clicks(createkey_save)
                        .compose(rxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        .subscribe { granted->
                            if(granted){
                                presenter?.saveKey()
                            }
                        }
        )

        disposable.add(
                RxView.clicks(createkey_back)
                        .subscribe {
                            finish()
                        }
        )

        disposable.add(
                RxView.clicks(createkey_generate_randomkey)
                        .subscribe {
                            presenter?.generateRandomKey()
                        }
        )

        createkey_grid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val context = this
            ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Choose color")
                    .initialColor(currentBackgroundColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener(object : OnColorSelectedListener {
                        override fun onColorSelected(selectedColor: Int) {
                            Toast.makeText(this@CreateKeyActivity,"Color: FF" + Integer.toHexString(selectedColor),Toast.LENGTH_SHORT)
                        }
                    })
                    .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                        var key = KeyGeneration.alphabet[position]
                        presenter?.addToKeys(key,Integer.toHexString(selectedColor).toUpperCase())
                        view.findViewById<TextView>(R.id.grid_color).text =Integer.toHexString(selectedColor).toUpperCase()
                    }
                    .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which -> })
                    .build()
                    .show()


        }


    }

    override fun showError(reason: String) {
        Toast.makeText(this,reason,Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {

    }

    override fun updateList(keys: Key) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
