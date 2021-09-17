package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Class handling the functionalities related to viewing the highlight of another user for
 * unregistered users.
 *
 * @constructor Creates a class that handles the functionalities related to viewing the highlight
 * of another user for unregistered users.
 */
class ViewOthersHighlightUnregisteredActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose highlight is being viewed.
     */
    private lateinit var civItemViewOthersHighlightUnregisteredProfilePic: CircleImageView

    /**
     * Username of the user whose highlight is being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredUsername: TextView

    /**
     * Artwork of the highlight being viewed.
     */
    private lateinit var ivItemViewOthersHighlightUnregisteredPost: ImageView

    /**
     * Title of the highlight being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredTitle: TextView

    /**
     * Date posted of the highlight being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredDatePosted: TextView

    /**
     * Medium of the highlight being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredMedium: TextView

    /**
     * Dimensions of the highlight being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredDimensions: TextView

    /**
     * Description of the highlight being viewed.
     */
    private lateinit var tvItemViewOthersHighlightUnregisteredDescription: TextView

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewOthersHighlightUnregisteredBottom: BottomNavigationView

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Object for accessing the Firebase helper methods.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_others_highlight_unregistered)

        civItemViewOthersHighlightUnregisteredProfilePic = findViewById(R.id.civ_item_view_others_highlight_unregistered_profile_pic)
        tvItemViewOthersHighlightUnregisteredUsername = findViewById(R.id.tv_item_view_others_highlight_unregistered_username)
        ivItemViewOthersHighlightUnregisteredPost = findViewById(R.id.iv_item_view_others_highlight_unregistered_post)
        tvItemViewOthersHighlightUnregisteredTitle = findViewById(R.id.tv_item_view_others_highlight_unregistered_title)
        tvItemViewOthersHighlightUnregisteredDatePosted = findViewById(R.id.tv_item_view_others_highlight_unregistered_date)
        tvItemViewOthersHighlightUnregisteredMedium = findViewById(R.id.tv_item_view_others_highlight_unregistered_medium)
        tvItemViewOthersHighlightUnregisteredDimensions = findViewById(R.id.tv_item_view_others_highlight_unregistered_dimen)
        tvItemViewOthersHighlightUnregisteredDescription = findViewById(R.id.tv_item_view_others_highlight_unregistered_desc)

        initIntent()
        initComponents()
        initBottom()
    }

    /**
     * Initializes the intent passed to the activity.
     */
    private fun initIntent() {
        val intent: Intent = intent

        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewOthersHighlightUnregisteredActivity, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)

        Glide.with(this@ViewOthersHighlightUnregisteredActivity)
            .load(profilePicture)
                        .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewOthersHighlightUnregisteredProfilePic)

        this.tvItemViewOthersHighlightUnregisteredUsername.text = username

        Glide.with(this@ViewOthersHighlightUnregisteredActivity)
            .load(postImg)
                        .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewOthersHighlightUnregisteredPost)

        if (!title.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredTitle.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredTitle.text = title
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewOthersHighlightUnregisteredDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredMedium.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredMedium.text = medium
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredDimensions.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredDimensions.text = dimensions
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredDescription.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredDescription.text = description
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredDescription.visibility = View.GONE
        }

        civItemViewOthersHighlightUnregisteredProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewOthersHighlightUnregisteredUsername.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight_unregistered))
        initActionBar()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewOthersHighlightUnregisteredBottom = findViewById(R.id.nv_view_others_highlight_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_view_others_highlight_unregistered_add)

        BottomMenuUtil.setFinishBottomMenuListenersUnregistered(bnvViewOthersHighlightUnregisteredBottom, this,
            this@ViewOthersHighlightUnregisteredActivity)

        fabAddPost.setOnClickListener {
            Toast.makeText(
                this@ViewOthersHighlightUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}