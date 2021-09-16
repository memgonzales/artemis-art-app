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

    /**************************************************************
     * KEYS USED IN PASSING INTENTS RELATED TO ACTIVITY LAUNCHING
     **************************************************************/

    /**
     * Key used in passing activity launching-related intents involving the profile picture.
     */
    KEY_PROFILE_PICTURE,

    /**
     * Key used in passing activity launching-related intents involving the username.
     */
    KEY_USERNAME,

    /**
     * Key used in passing activity launching-related intents involving the unique identifier
     * of the post.
     */
    KEY_POSTID,

    /**
     * Key used in passing activity launching-related intents involving the post itself.
     */
    KEY_POST,

    /**
     * Key used in passing activity launching-related intents involving the title of the post.
     */
    KEY_TITLE,

    /**
     * Key used in passing activity launching-related intents involving the number of upvotes
     * of a post.
     */
    KEY_NUM_UPVOTES,

    /**
     * Key used in passing activity launching-related intents involving the number of comments
     * on a post.
     */
    KEY_NUM_COMMENTS,

    /**
     * Key used in passing activity launching-related intents involving the date an artwork was posted.
     */
    KEY_DATE_POSTED,

    /**
     * Key used in passing activity launching-related intents involving the medium of an artwork
     * posted.
     */
    KEY_MEDIUM,

    /**
     * Key used in passing activity launching-related intents involving the dimensions of an
     * artwork posted.
     */
    KEY_DIMENSIONS,

    /**
     * Key used in passing activity launching-related intents involving the description of an
     * artwork posted.
     */
    KEY_DESCRIPTION,

    /**
     * Key used in passing activity launching-related intents involving the tags of an artwork
     * posted.
     */
    KEY_TAGS,

    /**
     * Key used in passing activity launching-related intents involving the bookmark status
     * of an artwork posted.
     */
    KEY_BOOKMARK,

    /**
     * Key used in passing activity launching-related intents involving the upvote status
     * of an artwork posted.
     */
    KEY_UPVOTE,

    /**
     * Key used in passing activity launching-related intents involving the highlight status
     * of an artwork posted.
     */
    KEY_HIGHLIGHT,

    /**
     * Key used in passing activity launching-related intents involving the short bio of a user.
     */
    KEY_BIO,

    /**
     * Key used in passing activity launching-related intents involving a comment per se.
     */
    KEY_COMMENT_BODY,

    /**
     * Key used in passing activity launching-related intents involving the unique identifier
     * of a user.
     */
    KEY_USERID,

    /**
     * Key used in passing activity launching-related intents involving the unique identifier
     * of a comment.
     */
    KEY_COMMENTID,

    /**
     * Key used in passing activity launching-related intents involving the search feature.
     */
    KEY_SEARCH,
    
    /**************************************************************
     * KEYS USED IN PASSING INTENTS RELATED TO POSTING AN ARTWORK
     **************************************************************/

    /**
     * Key used to pass the image URI of the artwork to be posted.
     */
    KEY_POST_ARTWORK,

    /**
     * Key used to pass information as to whether the artwork was taken from the camera
     * or chosen from the Gallery
     */
    KEY_POST_FROM,

    /************************************************************
     * KEYS USED IN PASSING DATA RELATED TO DATABASE OPERATIONS
     ************************************************************/

    /**
     * Key used to reference to the Users collection.
     */
    KEY_DB_USERS,

    /**
     * Key used in retrieving and passing data related to the username of a user.
     */
    username,

    /**
     * Key used in retrieving and passing data related to the email address of a user.
     */
    email,

    /**
     * Key used in retrieving and passing data related to the password of a user.
     */
    password,

    /**
     * Key used in retrieving and passing data related to the profile picture of a user.
     */
    userImg,

    /**
     * Key used in retrieving and passing data related to the short bio of a user.
     */
    bio,

    /**
     * Key used in retrieving and passing data related to the bookmarked posts of a user.
     */
    bookmarks,

    /**
     * Key used in retrieving and passing data related to the posts of a user.
     */
    userPosts,

    /**
     * Key used in retrieving and passing data related to the highlighted posts of a user.
     */
    highlights,

    /**
     * Key used in retrieving and passing data related to the upvoted posts of a user.
     */
    upvotedPosts,

    /**
     * Key used in retrieving and passing data related to the users followed by a user.
     */
    usersFollowed,

    /**
     * Key used in retrieving and passing data related to the comments of a user.
     */
    userComments,

    /**
     * Key used to reference to the Posts collection.
     */
    KEY_DB_POSTS,

    /**
     * Key used in retrieving and passing data related to the unique identifier of a post.
     */
    postId,

    /**
     * Key used in retrieving and passing data related to the profile picture of the user
     * who posted an artwork.
     */
    profilePicture,

    /**
     * Key used in retrieving and passing data related to the photo of an artwork posted.
     */
    postImg,

    /**
     * Key used in retrieving and passing data related to the title of an artwork posted.
     */
    title,

    /**
     * Key used in retrieving and passing data related to the date an artwork was posted.
     */
    datePosted,

    /**
     * Key used in retrieving and passing data related to the medium of an artwork posted.
     */
    medium,

    /**
     * Key used in retrieving and passing data related to the dimensions of an artwork posted.
     */
    dimensions,

    /**
     * Key used in retrieving and passing data related to the description of an artwork posted.
     */
    description,

    /**
     * Key used in retrieving and passing data related to the tags of an artwork posted.
     */
    tags,

    /**
     * Key used in retrieving and passing data related to the bookmark status of an artwork posted.
     */
    bookmark,

    /**
     * Key used in retrieving and passing data related to the upvote status of an artwork posted.
     */
    upvote,

    /**
     * Key used in retrieving and passing data related to the highlight status of an artwork posted.
     */
    highlight,

    /**
     * Key used in retrieving and passing data related to the users who upvoted a post.
     */
    upvoteUsers,

    /**
     * Key used in retrieving and passing data related to the users who bookmarked a post.
     */
    bookmarkUsers,

    /**
     * Key used in retrieving and passing data related to the comments on a post.
     */
    comments,
    highlightUser,

    /**
     * Key used in retrieving and passing data related to the number of upvotes of a post.
     */
    numUpvotes,

    /**
     * Key used in retrieving and passing data related to the number of comments on a post.
     */
    numComments,

    /**
     * Key used to reference to the Comments collection.
     */
    KEY_DB_COMMENTS,

    /**
     * Key used in retrieving and passing data related to the unique identifier of a comment.
     */
    commentId,

    /**
     * Key used in retrieving and passing data related to the body of a comment.
     */
    commentBody
}