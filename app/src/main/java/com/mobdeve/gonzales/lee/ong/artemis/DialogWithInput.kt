package com.mobdeve.gonzales.lee.ong.artemis

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * Class that defines a custom alert dialog that accepts a password input from the user.
 *
 * This implementation is based on the Java code presented in the following video:
 * [https://www.youtube.com/watch?v=ARezg1D9Zd0](https://www.youtube.com/watch?v=ARezg1D9Zd0).
 *
 * @constructor Creates a custom alert dialog that accepts a password input from the user
 */
class DialogWithInput : AppCompatDialogFragment() {
    /**
     * Listener that retrieves the password entered by the user.
     */
    private lateinit var listener: DialogWithInputListener

    /**
     * Override to build your own custom Dialog container.
     *
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a
     * freshly created Fragment.
     */
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

    /**
     * Called when a fragment is first attached to its context.
     *
     * @param context Context of this class.
     */
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

    /**
     * Skeleton for the listener that retrieves the password entered by the user.
     */
    interface DialogWithInputListener {
        /**
         * Retrieves and returns the password entered by the user in the confirmation dialog.
         *
         * @param password Password entered by the user in the confirmation dialog.
         * @return Password entered by the user, casted as a string.
         */
        fun fetchPassword(password: String): String
    }
}