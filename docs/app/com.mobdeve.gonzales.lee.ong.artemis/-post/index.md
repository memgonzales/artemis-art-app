//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[Post](index.md)

# Post

[androidJvm]\
class [Post](index.md)

## Constructors

| | |
|---|---|
| [Post](-post.md) | [androidJvm]<br>fun [Post](-post.md)() |
| [Post](-post.md) | [androidJvm]<br>fun [Post](-post.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), postImg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), medium: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dimensions: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tags: [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>) |

## Properties

| Name | Summary |
|---|---|
| [bookmark](bookmark.md) | [androidJvm]<br>private var [bookmark](bookmark.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [bookmarkUsers](bookmark-users.md) | [androidJvm]<br>private var [bookmarkUsers](bookmark-users.md): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [comments](comments.md) | [androidJvm]<br>private var [comments](comments.md): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [datePosted](date-posted.md) | [androidJvm]<br>private var [datePosted](date-posted.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [description](description.md) | [androidJvm]<br>private var [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [dimensions](dimensions.md) | [androidJvm]<br>private var [dimensions](dimensions.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [highlight](highlight.md) | [androidJvm]<br>private var [highlight](highlight.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [medium](medium.md) | [androidJvm]<br>private var [medium](medium.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [numComments](num-comments.md) | [androidJvm]<br>private var [numComments](num-comments.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [numUpvotes](num-upvotes.md) | [androidJvm]<br>private var [numUpvotes](num-upvotes.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [postId](post-id.md) | [androidJvm]<br>private var [postId](post-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [postImg](post-img.md) | [androidJvm]<br>private var [postImg](post-img.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [profilePicture](profile-picture.md) | [androidJvm]<br>private var [profilePicture](profile-picture.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [tags](tags.md) | [androidJvm]<br>private var [tags](tags.md): [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)> |
| [title](title.md) | [androidJvm]<br>private var [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [upvote](upvote.md) | [androidJvm]<br>private var [upvote](upvote.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [upvoteUsers](upvote-users.md) | [androidJvm]<br>private var [upvoteUsers](upvote-users.md): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [userId](user-id.md) | [androidJvm]<br>private var [userId](user-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [username](username.md) | [androidJvm]<br>private var [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |

## Functions

| Name | Summary |
|---|---|
| [getBookmark](get-bookmark.md) | [androidJvm]<br>fun [getBookmark](get-bookmark.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getBookmarkUsers](get-bookmark-users.md) | [androidJvm]<br>fun [getBookmarkUsers](get-bookmark-users.md)(): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [getComments](get-comments.md) | [androidJvm]<br>fun [getComments](get-comments.md)(): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [getDatePosted](get-date-posted.md) | [androidJvm]<br>fun [getDatePosted](get-date-posted.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getDescription](get-description.md) | [androidJvm]<br>fun [getDescription](get-description.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getDimensions](get-dimensions.md) | [androidJvm]<br>fun [getDimensions](get-dimensions.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getHighlight](get-highlight.md) | [androidJvm]<br>fun [getHighlight](get-highlight.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getMedium](get-medium.md) | [androidJvm]<br>fun [getMedium](get-medium.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getNumComments](get-num-comments.md) | [androidJvm]<br>fun [getNumComments](get-num-comments.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNumUpvotes](get-num-upvotes.md) | [androidJvm]<br>fun [getNumUpvotes](get-num-upvotes.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getPostId](get-post-id.md) | [androidJvm]<br>fun [getPostId](get-post-id.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getPostImg](get-post-img.md) | [androidJvm]<br>fun [getPostImg](get-post-img.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getProfilePicture](get-profile-picture.md) | [androidJvm]<br>fun [getProfilePicture](get-profile-picture.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getTags](get-tags.md) | [androidJvm]<br>fun [getTags](get-tags.md)(): [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>? |
| [getTitle](get-title.md) | [androidJvm]<br>fun [getTitle](get-title.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getUpvote](get-upvote.md) | [androidJvm]<br>fun [getUpvote](get-upvote.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getUpvoteUsers](get-upvote-users.md) | [androidJvm]<br>fun [getUpvoteUsers](get-upvote-users.md)(): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?> |
| [getUserId](get-user-id.md) | [androidJvm]<br>fun [getUserId](get-user-id.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getUsername](get-username.md) | [androidJvm]<br>fun [getUsername](get-username.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [setBookmark](set-bookmark.md) | [androidJvm]<br>fun [setBookmark](set-bookmark.md)(bookmark: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setBookmarkUsers](set-bookmark-users.md) | [androidJvm]<br>fun [setBookmarkUsers](set-bookmark-users.md)(bookmarkUsers: [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>) |
| [setComments](set-comments.md) | [androidJvm]<br>fun [setComments](set-comments.md)(comments: [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>) |
| [setDatePosted](set-date-posted.md) | [androidJvm]<br>fun [setDatePosted](set-date-posted.md)(datePosted: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setDescription](set-description.md) | [androidJvm]<br>fun [setDescription](set-description.md)(description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setDimensions](set-dimensions.md) | [androidJvm]<br>fun [setDimensions](set-dimensions.md)(dimensions: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setHighlight](set-highlight.md) | [androidJvm]<br>fun [setHighlight](set-highlight.md)(highlight: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setMedium](set-medium.md) | [androidJvm]<br>fun [setMedium](set-medium.md)(medium: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setNumComments](set-num-comments.md) | [androidJvm]<br>fun [setNumComments](set-num-comments.md)(numComments: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setNumUpvotes](set-num-upvotes.md) | [androidJvm]<br>fun [setNumUpvotes](set-num-upvotes.md)(numUpvotes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setPostId](set-post-id.md) | [androidJvm]<br>fun [setPostId](set-post-id.md)(postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setPostImg](set-post-img.md) | [androidJvm]<br>fun [setPostImg](set-post-img.md)(postImg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setProfilePicture](set-profile-picture.md) | [androidJvm]<br>fun [setProfilePicture](set-profile-picture.md)(picture: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setTags](set-tags.md) | [androidJvm]<br>fun [setTags](set-tags.md)(tags: [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>) |
| [setTitle](set-title.md) | [androidJvm]<br>fun [setTitle](set-title.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [setUpvote](set-upvote.md) | [androidJvm]<br>fun [setUpvote](set-upvote.md)(upvote: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setUpvoteUsers](set-upvote-users.md) | [androidJvm]<br>fun [setUpvoteUsers](set-upvote-users.md)(upvoteUsers: [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>) |
| [setUserId](set-user-id.md) | [androidJvm]<br>fun [setUserId](set-user-id.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setUsername](set-username.md) | [androidJvm]<br>fun [setUsername](set-username.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
