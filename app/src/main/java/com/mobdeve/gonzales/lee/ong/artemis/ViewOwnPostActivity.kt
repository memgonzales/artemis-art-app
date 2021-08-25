package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView

class ViewOwnPostActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnPostProfilePic: CircleImageView
    private lateinit var tvItemViewOwnPostUsername: TextView
    private lateinit var ivItemViewOwnPostPostImg: ImageView
    private lateinit var tvItemViewOwnPostTitle: TextView
    private lateinit var tvItemViewOwnPostDatePosted: TextView
    private lateinit var tvItemViewOwnPostMedium: TextView
    private lateinit var tvItemViewOwnPostDimensions: TextView
    private lateinit var tvItemViewOwnPostDescription: TextView
    private lateinit var tvItemViewOwnPostTags: TextView
    private lateinit var ibItemViewOwnPostOptions: ImageButton

    private lateinit var btmViewOwnPost: BottomSheetDialog
    private lateinit var clDialogViewOwnPostEdit: ConstraintLayout
    private lateinit var clDialogViewOwnPostDelete: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)

        this.civItemViewOwnPostProfilePic = findViewById(R.id.civ_item_view_own_post_profile_pic)
        this.tvItemViewOwnPostUsername = findViewById(R.id.tv_item_view_own_post_username)
        this.ivItemViewOwnPostPostImg = findViewById(R.id.iv_item_view_own_post_post)
        this.tvItemViewOwnPostTitle = findViewById(R.id.tv_item_view_own_post_title)
        this.tvItemViewOwnPostDatePosted = findViewById(R.id.tv_item_view_own_post_date)
        this.tvItemViewOwnPostMedium = findViewById(R.id.tv_item_view_own_post_medium)
        this.tvItemViewOwnPostDimensions = findViewById(R.id.tv_item_view_own_post_dimen)
        this.tvItemViewOwnPostDescription = findViewById(R.id.tv_item_view_own_post_desc)
        this.tvItemViewOwnPostTags = findViewById(R.id.tv_item_view_own_post_tags)
        this.ibItemViewOwnPostOptions = findViewById(R.id.ib_item_view_own_post_options)
        this.btmViewOwnPost = BottomSheetDialog(this@ViewOwnPostActivity)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_post))
        initActionBar()
         initIntent()
    }

    private fun initIntent() {
        val intent: Intent = intent

        var profilePicture: Int = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        var username: String = intent.getStringExtra(Keys.KEY_USERNAME.name)!!
        var postImg: Int = intent.getIntExtra(Keys.KEY_POST.name, 0)
        var title: String = intent.getStringExtra(Keys.KEY_TITLE.name)!!
        var datePosted: String = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)!!
        var medium: String = intent.getStringExtra(Keys.KEY_MEDIUM.name)!!
        var dimensions: String = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)!!
        var description: String = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)!!
        var tags: Array<String> = intent.getStringArrayExtra(Keys.KEY_TAGS.name)!!

        var tagsString: String = tags?.joinToString(", ")

        this.civItemViewOwnPostProfilePic.setImageResource(profilePicture)
        this.tvItemViewOwnPostUsername.text = username
        this.ivItemViewOwnPostPostImg.setImageResource(postImg)
        this.tvItemViewOwnPostTitle.text = title
        this.tvItemViewOwnPostDatePosted.text = datePosted
        this.tvItemViewOwnPostMedium.text = medium
        this.tvItemViewOwnPostDimensions.text = dimensions
        this.tvItemViewOwnPostDescription.text = description
        this.tvItemViewOwnPostTags.text = tagsString

        civItemViewOwnPostProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )

            startActivity(intent)
        })

        tvItemViewOwnPostUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )

            startActivity(intent)
        })

        launchDialog(title, medium, dimensions, description, postImg)
    }

    private fun launchDialog(title: String, medium: String, dimensions: String, description: String,
                             postImg: Int) {
        val view = LayoutInflater.from(this@ViewOwnPostActivity).inflate(R.layout.dialog_own_post, null)

        this.ibItemViewOwnPostOptions.setOnClickListener {
            btmViewOwnPost.setContentView(view)

            this.clDialogViewOwnPostEdit = btmViewOwnPost.findViewById(R.id.cl_dialog_own_post_edit)!!
            this.clDialogViewOwnPostDelete = btmViewOwnPost.findViewById(R.id.cl_dialog_own_post_delete)!!

            clDialogViewOwnPostEdit.setOnClickListener(View.OnClickListener {
                btmViewOwnPost.dismiss()
                val intent = Intent(this@ViewOwnPostActivity, EditPostActivity::class.java)

                val dummyTags = arrayOf("dummy tag 1", "dummy tag 2", "dummy tag 3", "dummy tag 4", "dummy tag 5")

                intent.putExtra(
                    Keys.KEY_TITLE.name,
                    title
                )
                intent.putExtra(
                    Keys.KEY_MEDIUM.name,
                    medium
                )
                intent.putExtra(
                    Keys.KEY_DIMENSIONS.name,
                    dimensions
                )
                intent.putExtra(
                    Keys.KEY_DESCRIPTION.name,
                    description
                )
                intent.putExtra(
                    Keys.KEY_TAGS.name,
                    dummyTags
                )
                intent.putExtra(
                    Keys.KEY_POST.name,
                    postImg
                )
                startActivity(intent)
                finish()
            })

            clDialogViewOwnPostDelete.setOnClickListener(View.OnClickListener {
                btmViewOwnPost.dismiss()
                Toast.makeText(this@ViewOwnPostActivity, "Your post has been deleted", Toast.LENGTH_SHORT).show()
                finish()
            })

            btmViewOwnPost.show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}