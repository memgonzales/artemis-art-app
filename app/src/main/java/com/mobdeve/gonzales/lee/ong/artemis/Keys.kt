package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Enumeration class containing the keys used in passing intents across activities and in
 * database-related operations.
 *
 * Since the said operations require strings as keys, they are referenced using their
 * names in this enumeration declaration (that is, via the property <code>name</code>).
 *
 * @constructor Creates an enumeration class containing the keys used in passing intents across
 * activities and in database-related operations.
 */
enum class Keys {
    /* Keys used in passing intents across activities */
    KEY_PROFILE_PICTURE,
    KEY_USERNAME,
    KEY_POST,
    KEY_TITLE,
    KEY_NUM_UPVOTES,
    KEY_NUM_COMMENTS,
    KEY_DATE_POSTED,
    KEY_MEDIUM,
    KEY_DIMENSIONS,
    KEY_DESCRIPTION,
    KEY_TAGS,
    KEY_BOOKMARK,
    KEY_UPVOTE,
    KEY_HIGHLIGHT,
    KEY_BIO,
    KEY_COMMENT_BODY,

    /* Keys used in passing intents related to posting an artwork */

    /**
     * Key used to pass the image URI of the artwork to be posted
     */
    KEY_POST_ARTWORK,
    KEY_CAMERA_PIC,
    KEY_GALLERY_PIC,

    /**
     * Key used to pass information as to whether the artwork was taken from the camera
     * or chosen from the Gallery
     */
    KEY_POST_FROM,

    /* Keys used in database-related operations */
    KEY_DB_USERS,
    username,
    email,
    password,
    userImg,
    bio,

    bookmarks,
    userPosts,
    highlights,
    upvotedPosts,
    usersFollowed,
    userComments,

    KEY_DB_POSTS,
    postId,

    profilePicture,
    postImg,
    title,
    datePosted,

    medium,
    dimensions,
    description,
    tags,

    bookmark,
    upvote,
    highlight,

    upvoteUsers,
    bookmarkUsers,
    comments,

    numUpvotes,
    numComments,

    KEY_DB_COMMENTS
}