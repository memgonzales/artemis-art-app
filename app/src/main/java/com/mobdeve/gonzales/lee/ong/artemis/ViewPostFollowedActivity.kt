package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewPostFollowedActivity : AppCompatActivity() {
    private lateinit var civItemViewPostFollowedProfilePic: CircleImageView
    private lateinit var tvItemViewPostFollowedUsername: TextView
    private lateinit var ivItemViewPostFollowedPost: ImageView
    private lateinit var tvItemViewPostFollowedTitle: TextView
    private lateinit var tvItemViewPostFollowedUpvoteCounter: TextView
    private lateinit var tvItemViewPostFollowedComments: TextView
    private lateinit var tvItemViewPostFollowedDatePosted: TextView
    private lateinit var tvItemViewPostFollowedMedium: TextView
    private lateinit var tvItemViewPostFollowedDimensions: TextView
    private lateinit var tvItemViewPostFollowedDescription: TextView
    private lateinit var tvItemViewPostFollowedTags: TextView

    private lateinit var ibItemViewPostFollowedBookmark: ImageButton
    private lateinit var ivItemViewPostFollowedUpvote: ImageView
    private lateinit var tvItemViewPostFollowedUpvote: TextView
    private lateinit var clItemViewPostFollowedUpvote: ConstraintLayout
    private lateinit var clItemViewPostFollowedComment: ConstraintLayout
    private lateinit var clItemViewPostFollowedShare: ConstraintLayout
    private lateinit var bnvViewPostFollowedBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlViewPostFollowed: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_followed)

        civItemViewPostFollowedProfilePic = findViewById(R.id.civ_item_view_post_followed_profile_pic)
        tvItemViewPostFollowedUsername = findViewById(R.id.tv_item_view_post_followed_username)
        ivItemViewPostFollowedPost = findViewById(R.id.iv_item_view_post_followed_post)
        tvItemViewPostFollowedTitle = findViewById(R.id.tv_item_view_post_followed_title)
        tvItemViewPostFollowedUpvoteCounter = findViewById(R.id.tv_item_view_post_followed_upvote_counter)
        tvItemViewPostFollowedComments = findViewById(R.id.tv_item_view_post_followed_comments)
        tvItemViewPostFollowedDatePosted = findViewById(R.id.tv_item_view_post_followed_date)
        tvItemViewPostFollowedMedium = findViewById(R.id.tv_item_view_post_followed_medium)
        tvItemViewPostFollowedDimensions = findViewById(R.id.tv_item_view_post_followed_dimen)
        tvItemViewPostFollowedDescription = findViewById(R.id.tv_item_view_post_followed_desc)
        tvItemViewPostFollowedTags = findViewById(R.id.tv_item_view_post_followed_tags)
        ibItemViewPostFollowedBookmark = findViewById(R.id.ib_item_view_post_followed_bookmark)
        ivItemViewPostFollowedUpvote = findViewById(R.id.iv_item_view_post_followed_upvote)
        tvItemViewPostFollowedUpvote = findViewById(R.id.tv_item_view_post_followed_upvote)
        clItemViewPostFollowedUpvote = findViewById(R.id.cl_item_view_post_followed_upvote)
        clItemViewPostFollowedComment = findViewById(R.id.cl_item_view_post_followed_comment)
        clItemViewPostFollowedShare = findViewById(R.id.cl_item_view_post_followed_share)

        initIntent()
        initComponents()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        var upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        var upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)

        var upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val tagsString = tags?.joinToString(", ")

        this.civItemViewPostFollowedProfilePic.setImageResource(profilePicture)
        this.tvItemViewPostFollowedUsername.text = username
        this.ivItemViewPostFollowedPost.setImageResource(post)
        this.tvItemViewPostFollowedTitle.text = title
        this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
        this.tvItemViewPostFollowedComments.text = commentString
        this.tvItemViewPostFollowedDatePosted.text = datePosted
        this.tvItemViewPostFollowedMedium.text = medium
        this.tvItemViewPostFollowedDimensions.text = dimensions
        this.tvItemViewPostFollowedDescription.text = description
        this.tvItemViewPostFollowedTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostFollowedBookmark.setOnClickListener(View.OnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)
        })

        clItemViewPostFollowedUpvote.setOnClickListener(View.OnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            } else {
                upvote = true
                upvoteCounter += 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            }
        })

        clItemViewPostFollowedComment.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewCommentsFollowedActivity::class.java)
            startActivity(intent)
        })

        clItemViewPostFollowedShare.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewPostFollowedActivity,"Post shared on Facebook", Toast.LENGTH_SHORT).show()
        })

        civItemViewPostFollowedProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })

        tvItemViewPostFollowedUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_followed))
        initActionBar()
        initSwipeRefresh()
        initBottom()
        addPost()
    }

    private fun initSwipeRefresh() {
        this.srlViewPostFollowed = findViewById(R.id.srl_view_post_followed)
        srlViewPostFollowed.setOnRefreshListener {
            onRefresh();
        }

        srlViewPostFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewPostFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initBottom() {
        this.bnvViewPostFollowedBottom = findViewById(R.id.nv_view_post_followed_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewPostFollowedBottom, this,
            this@ViewPostFollowedActivity)
    }

    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewPostFollowedBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewPostFollowedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostFollowedBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewPostFollowedBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewPostFollowedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostFollowedBookmark.context, R.color.default_gray)
            )
        }
    }

    private fun updateUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemViewPostFollowedUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemViewPostFollowedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostFollowedUpvote.context, R.color.pinkish_purple)
            )
            tvItemViewPostFollowedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostFollowedUpvote.context, R.color.pinkish_purple)))
        } else {
            ivItemViewPostFollowedUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemViewPostFollowedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostFollowedUpvote.context, R.color.default_gray)
            )
            tvItemViewPostFollowedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostFollowedUpvote.context, R.color.default_gray)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            } else -> {
            return super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewPostFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_view_post_followed_add)

        val view = LayoutInflater.from(this@ViewPostFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewPostFollowedActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewPostFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewPostFollowedActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewPostFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}