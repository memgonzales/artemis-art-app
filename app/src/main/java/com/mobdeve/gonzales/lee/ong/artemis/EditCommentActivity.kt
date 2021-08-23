package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class EditCommentActivity : AppCompatActivity() {
    private lateinit var etEditComment: EditText
    private lateinit var nvEditCommentBottom: BottomNavigationView
    private lateinit var activityRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_comment)

        initComponents()
    }

    private fun initComponents() {
        this.etEditComment = findViewById(R.id.et_edit_comment)
        this.nvEditCommentBottom = findViewById(R.id.nv_edit_comment_bottom)
        this.activityRootView = findViewById(R.id.cl_edit_comment_root)
        handleKeyboardBehavior()

        setSupportActionBar(findViewById(R.id.toolbar_edit_comment))
        initActionBar()
    }

    private fun handleKeyboardBehavior() {
        nvEditCommentBottom.visibility = View.VISIBLE

        activityRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff = activityRootView.rootView.height - activityRootView.height
            if (heightDiff > dpToPx(this, (200.0).toFloat())) {
                nvEditCommentBottom.visibility = View.GONE
            } else {
                nvEditCommentBottom.visibility = View.VISIBLE
            }
        }
    }

    private fun dpToPx(context: Context, valueInDp: Float) : Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}