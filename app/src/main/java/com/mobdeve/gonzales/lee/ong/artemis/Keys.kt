package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Enumeration class containing the keys used in passing intents across activities and in
 * database-related operations.
 *
 * Since the said operations require strings as keys, they are referenced using their
 * names in this enumeration declaration (that is, via the property <code>name</code>).
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
    KEY_POST_ARTWORK,
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
    comments,

    numUpvotes,
    numComments,

    KEY_DB_COMMENTS
}