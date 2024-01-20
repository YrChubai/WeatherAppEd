package com.example.weatherapped

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {
    fun locationSettingsDialog(context: Context, listener:Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Местоположение?")
        dialog.setMessage("Местоположение отключено, хотите включить")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок"){ _,_ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена"){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }
    fun searchByNameDialog(context: Context, listener:Listener){
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle("Название города")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок"){ _,_ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена"){ _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }
    interface Listener{
        fun onClick(name: String?)
    }
}