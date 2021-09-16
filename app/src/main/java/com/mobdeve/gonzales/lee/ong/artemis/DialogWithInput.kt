package com.mobdeve.gonzales.lee.ong.artemis

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment

class DialogWithInput : AppCompatDialogFragment() {
    private lateinit var listener: DialogWithInputListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_input_password, null)

        val etInputPassword = view.findViewById<EditText>(R.id.et_input_password)

        return AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle("Confirm Action")
            .setNegativeButton("Cancel") { _, _ ->}
            .setPositiveButton("Confirm") { _, _ ->
                listener.fetchPassword(etInputPassword.text.toString())
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = try {
            context as DialogWithInputListener

        } catch (e: ClassCastException) {
            throw ClassCastException (
                e.printStackTrace().toString()
            )
        }
    }

    interface DialogWithInputListener {
        fun fetchPassword(password: String): String
    }
}