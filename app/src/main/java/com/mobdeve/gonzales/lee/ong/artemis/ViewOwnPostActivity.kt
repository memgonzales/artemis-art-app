package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView

class ViewOwnPostActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnPostProfilePic: CircleImageView
    private lateinit var tvItemViewOwnPostUsername: TextView
    private lateinit var ivItemViewOwnPostPostImg: ImageView
    private lateinit var tvItemViewOwnPostTitle: TextView
    private lateinit var tvItemViewOwnPostUpvoteCounter: TextView
    private lateinit var tvItemViewOwnPostComments: TextView
    private lateinit var tvItemViewOwnPostDatePosted: TextView
    private lateinit var tvItemViewOwnPostMedium: TextView
    private lateinit var tvItemViewOwnPostDimensions: TextView
    private lateinit var tvItemViewOwnPostDescription: TextView
    private lateinit var tvItemViewOwnPostTags: TextView

    private lateinit var ivItemViewOwnPostHighlight: ImageView
    private lateinit var tvItemViewOwnPostHighlight: TextView
    private lateinit var clItemViewOwnPostHighlight: ConstraintLayout
    private lateinit var clItemViewOwnPostComment: ConstraintLayout
    private lateinit var bnvViewOwnPostBottom: BottomNavigationView

    private lateinit var ibItemViewOwnPostOptions: ImageButton
    private lateinit var btmViewOwnPost: BottomSheetDialog
    private lateinit var clDialogViewOwnPostEdit: ConstraintLayout
    private lateinit var clDialogViewOwnPostDelete: ConstraintLayout

    private lateinit var srlViewOwnPost: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)

        this.civItemViewOwnPostProfilePic = findViewById(R.id.civ_item_view_own_post_profile_pic)
        this.tvItemViewOwnPostUsername = findViewById(R.id.tv_item_view_own_post_username)
        this.ivItemViewOwnPostPostImg = findViewById(R.id.iv_item_view_own_post_post)
        this.tvItemViewOwnPostTitle = findViewById(R.id.tv_item_view_own_post_title)
        this.tvItemViewOwnPostUpvoteCounter = findViewById(R.id.tv_item_view_own_post_upvote_counter)
        this.tvItemViewOwnPostComments = findViewById(R.id.tv_item_view_own_post_comments)
        this.tvItemViewOwnPostDatePosted = findViewById(R.id.tv_item_view_own_post_date)
        this.tvItemViewOwnPostMedium = findViewById(R.id.tv_item_view_own_post_medium)
        this.tvItemViewOwnPostDimensions = findViewById(R.id.tv_item_view_own_post_dimen)
        this.tvItemViewOwnPostDescription = findViewById(R.id.tv_item_view_own_post_desc)
        this.tvItemViewOwnPostTags = findViewById(R.id.tv_item_view_own_post_tags)

        this.ivItemViewOwnPostHighlight = findViewById(R.id.iv_item_view_own_post_highlight)
        this.tvItemViewOwnPostHighlight = findViewById(R.id.tv_item_view_own_post_highlight)
        this.clItemViewOwnPostHighlight = findViewById(R.id.cl_item_view_own_post_highlight)
        this.clItemViewOwnPostComment = findViewById(R.id.cl_item_view_own_post_comment)

        this.ibItemViewOwnPostOptions = findViewById(R.id.ib_item_view_own_post_options)
        this.btmViewOwnPost = BottomSheetDialog(this@ViewOwnPostActivity)

        initIntent()
        initComponents()
        initBottom()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_post))
        initActionBar()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        this.srlViewOwnPost = findViewById(R.id.srl_view_own_post)
        srlViewOwnPost.setOnRefreshListener {
            onRefresh();
        }

        srlViewOwnPost.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewOwnPost.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val postImg = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

        val upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val tagsString = tags?.joinToString(", ")

        this.civItemViewOwnPostProfilePic.setImageResource(profilePicture)
        this.tvItemViewOwnPostUsername.text = username
        this.ivItemViewOwnPostPostImg.setImageResource(postImg)
        this.tvItemViewOwnPostTitle.text = title
        this.tvItemViewOwnPostUpvoteCounter.text = upvoteString
        this.tvItemViewOwnPostComments.text = commentString
        this.tvItemViewOwnPostDatePosted.text = datePosted
        this.tvItemViewOwnPostMedium.text = medium
        this.tvItemViewOwnPostDimensions.text = dimensions
        this.tvItemViewOwnPostDescription.text = description
        this.tvItemViewOwnPostTags.text = tagsString

        updateHighlight(highlight)

        clItemViewOwnPostHighlight.setOnClickListener(View.OnClickListener {
            if (highlight) {
                highlight = false
                updateHighlight(highlight)
            } else {
                highlight = true
                updateHighlight(highlight)
                Toast.makeText(this@ViewOwnPostActivity, "Added to your Highlights", Toast.LENGTH_SHORT).show()
            }
        })

        clItemViewOwnPostComment.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewCommentsActivity::class.java)
            startActivity(intent)
        })

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

        val editTitle: String = title.toString()
        val editMedium: String = medium.toString()
        val editDimensions: String = dimensions.toString()
        val editDescription: String = description.toString()
        val editPostImg: Int = postImg
        val editTags: String = tagsString.toString()

        launchDialog(editTitle, editMedium, editDimensions, editDescription, editPostImg, editTags)
    }

    private fun launchDialog(title: String, medium: String, dimensions: String, description: String,
                             postImg: Int, tags: String) {
        val view = LayoutInflater.from(this@ViewOwnPostActivity).inflate(R.layout.dialog_own_post, null)

        this.ibItemViewOwnPostOptions.setOnClickListener {
            btmViewOwnPost.setContentView(view)

            this.clDialogViewOwnPostEdit = btmViewOwnPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogViewOwnPostDelete = btmViewOwnPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogViewOwnPostEdit.setOnClickListener(View.OnClickListener {
                btmViewOwnPost.dismiss()
                val intent = Intent(this@ViewOwnPostActivity, EditPostActivity::class.java)

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
                    tags
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

    private fun initBottom() {
        this.bnvViewOwnPostBottom = findViewById(R.id.nv_view_own_post_bottom)

        bnvViewOwnPostBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@ViewOwnPostActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@ViewOwnPostActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@ViewOwnPostActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@ViewOwnPostActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun updateHighlight(highlight: Boolean) {
        if(highlight) {
            this.ivItemViewOwnPostHighlight.setImageResource(R.drawable.baseline_star_24)
            this.ivItemViewOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ivItemViewOwnPostHighlight.context, R.color.pinkish_purple)
            )
            this.tvItemViewOwnPostHighlight.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(this.tvItemViewOwnPostHighlight.context, R.color.pinkish_purple))
            )
        } else {
            this.ivItemViewOwnPostHighlight.setImageResource(R.drawable.outline_star_border_24)
            this.ivItemViewOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ivItemViewOwnPostHighlight.context, R.color.default_gray)
            )
            this.tvItemViewOwnPostHighlight.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(this.tvItemViewOwnPostHighlight.context, R.color.default_gray))
            )
        }
    }
}