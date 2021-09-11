//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[FirebaseHelper](index.md)

# FirebaseHelper

[androidJvm]\
class [FirebaseHelper](index.md)

## Constructors

| | |
|---|---|
| [FirebaseHelper](-firebase-helper.md) | [androidJvm]<br>fun [FirebaseHelper](-firebase-helper.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |
| [FirebaseHelper](-firebase-helper.md) | [androidJvm]<br>fun [FirebaseHelper](-firebase-helper.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, userIdPost: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [FirebaseHelper](-firebase-helper.md) | [androidJvm]<br>fun [FirebaseHelper](-firebase-helper.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), Id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [context](context.md) | [androidJvm]<br>private var [context](context.md): [Context](https://developer.android.com/reference/kotlin/android/content/Context.html) |
| [db](db.md) | [androidJvm]<br>private var [db](db.md): DatabaseReference |
| [mAuth](m-auth.md) | [androidJvm]<br>private var [mAuth](m-auth.md): FirebaseAuth |
| [user](user.md) | [androidJvm]<br>private lateinit var [user](user.md): FirebaseUser |
| [userId](user-id.md) | [androidJvm]<br>private lateinit var [userId](user-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [deletePostDB](delete-post-d-b.md) | [androidJvm]<br>fun [deletePostDB](delete-post-d-b.md)(postKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [deletePostFromUsersDB](delete-post-from-users-d-b.md) | [androidJvm]<br>fun [deletePostFromUsersDB](delete-post-from-users-d-b.md)(postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [deleteUserDB](delete-user-d-b.md) | [androidJvm]<br>fun [deleteUserDB](delete-user-d-b.md)() |
| [editComment](edit-comment.md) | [androidJvm]<br>fun [editComment](edit-comment.md)(commentId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), commentBody: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [editPost](edit-post.md) | [androidJvm]<br>fun [editPost](edit-post.md)(postId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), medium: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dimensions: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), desc: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tags: [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>) |
| [updateBookmarkDB](update-bookmark-d-b.md) | [androidJvm]<br>fun [updateBookmarkDB](update-bookmark-d-b.md)(userVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, postKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, postVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [updateHighlightDB](update-highlight-d-b.md) | [androidJvm]<br>fun [updateHighlightDB](update-highlight-d-b.md)(postKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, postVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [updateUpvoteDB](update-upvote-d-b.md) | [androidJvm]<br>fun [updateUpvoteDB](update-upvote-d-b.md)(userVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, postKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, postVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, numUpvotes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [updateUserPostDB](update-user-post-d-b.md) | [androidJvm]<br>fun [updateUserPostDB](update-user-post-d-b.md)(postKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), postVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [updateUsersFollowedDB](update-users-followed-d-b.md) | [androidJvm]<br>fun [updateUsersFollowedDB](update-users-followed-d-b.md)(userKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, userVal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
