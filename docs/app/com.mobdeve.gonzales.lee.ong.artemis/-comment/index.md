//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[Comment](index.md)

# Comment

[androidJvm]\
class [Comment](index.md)

Class representing a comment and specifying the attributes associated with it.

## Constructors

| | |
|---|---|
| [Comment](-comment.md) | [androidJvm]<br>fun [Comment](-comment.md)()<br>Creates a comment. |
| [Comment](-comment.md) | [androidJvm]<br>fun [Comment](-comment.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), profilePicture: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), commentBody: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Creates a comment given the unique identifier of the post to which the comment is tied, the comment itself, and the unique identifier, profile picture, and username of the user who posted it. |

## Properties

| Name | Summary |
|---|---|
| [commentBody](comment-body.md) | [androidJvm]<br>private var [commentBody](comment-body.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Comment per se. |
| [commentId](comment-id.md) | [androidJvm]<br>private var [commentId](comment-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Unique identifier of the comment. |
| [dateCommented](date-commented.md) | [androidJvm]<br>private var [dateCommented](date-commented.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Date when the comment was posted. |
| [editable](editable.md) | [androidJvm]<br>private var [editable](editable.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br><code>true</code> if the comment can be edited by the user; <code>false</code>, otherwise |
| [postId](post-id.md) | [androidJvm]<br>private var [postId](post-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Unique identifier of the post to which the comment is tied. |
| [profilePicture](profile-picture.md) | [androidJvm]<br>private var [profilePicture](profile-picture.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>URI of the profile picture of the user who posted the comment. |
| [userId](user-id.md) | [androidJvm]<br>private var [userId](user-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Unique identifier of the user who posted the comment. |
| [username](username.md) | [androidJvm]<br>private var [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>Username of the user who posted the comment. |

## Functions

| Name | Summary |
|---|---|
| [getCommentBody](get-comment-body.md) | [androidJvm]<br>fun [getCommentBody](get-comment-body.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the comment per se. |
| [getCommentId](get-comment-id.md) | [androidJvm]<br>fun [getCommentId](get-comment-id.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the unique identifier of the comment. |
| [getDateCommented](get-date-commented.md) | [androidJvm]<br>fun [getDateCommented](get-date-commented.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the date when the comment was posted. |
| [getEditable](get-editable.md) | [androidJvm]<br>fun [getEditable](get-editable.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns <code>true</code> if the user is allowed to edit the comment; <code>false</code>, otherwise. |
| [getPostId](get-post-id.md) | [androidJvm]<br>fun [getPostId](get-post-id.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the unique identifier of the post to which the comment is tied. |
| [getProfilePicture](get-profile-picture.md) | [androidJvm]<br>fun [getProfilePicture](get-profile-picture.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the profile picture of the user who posted the comment. |
| [getUserId](get-user-id.md) | [androidJvm]<br>fun [getUserId](get-user-id.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the unique identifier of the user who posted the comment. |
| [getUsername](get-username.md) | [androidJvm]<br>fun [getUsername](get-username.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Returns the username of the user who posted the comment. |
| [setCommentBody](set-comment-body.md) | [androidJvm]<br>fun [setCommentBody](set-comment-body.md)(commentBody: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the comment per se to the specified string. |
| [setCommentId](set-comment-id.md) | [androidJvm]<br>fun [setCommentId](set-comment-id.md)(commentId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the unique identifier of the comment to the specified string. |
| [setDateCommented](set-date-commented.md) | [androidJvm]<br>fun [setDateCommented](set-date-commented.md)(dateCommented: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the date when the comment was posted to the specified string. |
| [setEditable](set-editable.md) | [androidJvm]<br>fun [setEditable](set-editable.md)(editable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Allows the user to edit a comment posted by them but disallows them to edit a comment created by another user. |
| [setPostId](set-post-id.md) | [androidJvm]<br>fun [setPostId](set-post-id.md)(postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the unique identifier of the post to which the comment is tied to the specified string. |
| [setProfilePicture](set-profile-picture.md) | [androidJvm]<br>fun [setProfilePicture](set-profile-picture.md)(profPic: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the profile picture of the user who posted the comment to the photo specified by the given URI. |
| [setUserId](set-user-id.md) | [androidJvm]<br>fun [setUserId](set-user-id.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the unique identifier of the user who posted the comment to the specified string. |
| [setUsername](set-username.md) | [androidJvm]<br>fun [setUsername](set-username.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the username of the user who posted the comment to the specified string. |
