package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class AddProfilePictureActivity : AppCompatActivity() {
    private lateinit var btnAddProfilePic: Button

    private lateinit var civUploadImg: CircleImageView
    private lateinit var ivCameraPic: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_picture)

        initComponents()
    }

    private fun initComponents() {
        this.civUploadImg = findViewById(R.id.civ_item_feed_profile_pic)
        this.ivCameraPic = findViewById(R.id.iv_add_profile_pic_camera)

        setSupportActionBar(findViewById(R.id.toolbar_add_profile_pic))

        this.btnAddProfilePic = findViewById(R.id.btn_add_profile_pic_add)
        launchAddBio()
    }

    private fun launchAddBio() {
        this.btnAddProfilePic.setOnClickListener {
            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            startActivity(i)
        }
    }

    private fun uploadImg(){
        this.ivCameraPic.setOnClickListener(View.OnClickListener {

        })
    }

    override fun onBackPressed() {
        val i = Intent(this@AddProfilePictureActivity, BrowseFeedActivity::class.java)
        Toast.makeText(this@AddProfilePictureActivity, "You may update your profile details through the account tab.",
            Toast.LENGTH_SHORT).show()
        startActivity(i)
        finish()
    }

    /*
    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

     */
}