//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[CommentsViewHolder](index.md)

# CommentsViewHolder

[androidJvm]\
class [CommentsViewHolder](index.md)(**itemView**: [View](https://developer.android.com/reference/kotlin/android/view/View.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)

View holder for the recycler view that handles the comments on the posts.

## Parameters

androidJvm

| | |
|---|---|
| itemView | layout for a single item in the recycler view |

## Constructors

| | |
|---|---|
| [CommentsViewHolder](-comments-view-holder.md) | [androidJvm]<br>fun [CommentsViewHolder](-comments-view-holder.md)(itemView: [View](https://developer.android.com/reference/kotlin/android/view/View.html))<br>Creates a view holder for the recycler view that handles the comments on the posts. |

## Properties

| Name | Summary |
|---|---|
| [btmItemCommentOptions](btm-item-comment-options.md) | [androidJvm]<br>private val [btmItemCommentOptions](btm-item-comment-options.md): BottomSheetDialog<br>Bottom sheet dialog for modifying a comment (that is, either editing or deleting it). |
| [civItemCommentProfilePic](civ-item-comment-profile-pic.md) | [androidJvm]<br>private val [civItemCommentProfilePic](civ-item-comment-profile-pic.md): CircleImageView<br>Image view for the profile picture of the user who posted the comment. |
| [ibItemCommentEdit](ib-item-comment-edit.md) | [androidJvm]<br>private val [ibItemCommentEdit](ib-item-comment-edit.md): [ImageButton](https://developer.android.com/reference/kotlin/android/widget/ImageButton.html)<br>Button for editing the comment. |
| [storage](storage.md) | [androidJvm]<br>private var [storage](storage.md): FirebaseStorage<br>Service that supports uploading and downloading large objects to Google Cloud Storage. |
| [storageRef](storage-ref.md) | [androidJvm]<br>private var [storageRef](storage-ref.md): StorageReference<br>Represents a reference to a Google Cloud Storage object. |
| [tvItemCommentComment](tv-item-comment-comment.md) | [androidJvm]<br>private val [tvItemCommentComment](tv-item-comment-comment.md): [TextView](https://developer.android.com/reference/kotlin/android/widget/TextView.html)<br>Text view for the comment per se. |
| [tvItemCommentDate](tv-item-comment-date.md) | [androidJvm]<br>private val [tvItemCommentDate](tv-item-comment-date.md): [TextView](https://developer.android.com/reference/kotlin/android/widget/TextView.html)<br>Text view for the date when the comment was posted. |
| [tvItemCommentUsername](tv-item-comment-username.md) | [androidJvm]<br>private val [tvItemCommentUsername](tv-item-comment-username.md): [TextView](https://developer.android.com/reference/kotlin/android/widget/TextView.html)<br>Text view for the username of the user who posted the comment. |

## Inherited properties

| Name | Summary |
|---|---|
| [itemView](index.md#49815443%2FProperties%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)()<br>val [itemView](index.md#49815443%2FProperties%2F-912451524): [View](https://developer.android.com/reference/kotlin/android/view/View.html) |
| [mBindingAdapter](index.md#1021094872%2FProperties%2F-912451524) | [androidJvm]<br>val [mBindingAdapter](index.md#1021094872%2FProperties%2F-912451524): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)<out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)> |
| [mFlags](index.md#-1534599279%2FProperties%2F-912451524) | [androidJvm]<br>val [mFlags](index.md#-1534599279%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mInChangeScrap](index.md#1559896348%2FProperties%2F-912451524) | [androidJvm]<br>val [mInChangeScrap](index.md#1559896348%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [mIsRecyclableCount](index.md#1239472223%2FProperties%2F-912451524) | [androidJvm]<br>private val [mIsRecyclableCount](index.md#1239472223%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mItemId](index.md#1426853588%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemId](index.md#1426853588%2FProperties%2F-912451524): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [mItemViewType](index.md#-440906896%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemViewType](index.md#-440906896%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mNestedRecyclerView](index.md#271720807%2FProperties%2F-912451524) | [androidJvm]<br>val [mNestedRecyclerView](index.md#271720807%2FProperties%2F-912451524): [WeakReference](https://developer.android.com/reference/kotlin/java/lang/ref/WeakReference.html)<[RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)> |
| [mOldPosition](index.md#-332793464%2FProperties%2F-912451524) | [androidJvm]<br>val [mOldPosition](index.md#-332793464%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mOwnerRecyclerView](index.md#936058305%2FProperties%2F-912451524) | [androidJvm]<br>val [mOwnerRecyclerView](index.md#936058305%2FProperties%2F-912451524): [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html) |
| [mPayloads](index.md#-668615587%2FProperties%2F-912451524) | [androidJvm]<br>val [mPayloads](index.md#-668615587%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mPendingAccessibilityState](index.md#883340318%2FProperties%2F-912451524) | [androidJvm]<br>val [mPendingAccessibilityState](index.md#883340318%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPosition](index.md#-1918676903%2FProperties%2F-912451524) | [androidJvm]<br>val [mPosition](index.md#-1918676903%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPreLayoutPosition](index.md#1328530562%2FProperties%2F-912451524) | [androidJvm]<br>val [mPreLayoutPosition](index.md#1328530562%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mScrapContainer](index.md#858165234%2FProperties%2F-912451524) | [androidJvm]<br>val [mScrapContainer](index.md#858165234%2FProperties%2F-912451524): [RecyclerView.Recycler](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Recycler.html) |
| [mShadowedHolder](index.md#1960564663%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowedHolder](index.md#1960564663%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mShadowingHolder](index.md#570743594%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowingHolder](index.md#570743594%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mUnmodifiedPayloads](index.md#-2002985509%2FProperties%2F-912451524) | [androidJvm]<br>val [mUnmodifiedPayloads](index.md#-2002985509%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mWasImportantForAccessibilityBeforeHidden](index.md#1526652347%2FProperties%2F-912451524) | [androidJvm]<br>private val [mWasImportantForAccessibilityBeforeHidden](index.md#1526652347%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [getItemCommentOptions](get-item-comment-options.md) | [androidJvm]<br>fun [getItemCommentOptions](get-item-comment-options.md)(): BottomSheetDialog<br>Returns the bottom sheet dialog for modifying a comment (that is, either editing or deleting it). |
| [getItemCommentProfilePic](get-item-comment-profile-pic.md) | [androidJvm]<br>fun [getItemCommentProfilePic](get-item-comment-profile-pic.md)(): CircleImageView<br>Returns the image view for the profile picture of the user who posted the comment. |
| [setItemCommentComment](set-item-comment-comment.md) | [androidJvm]<br>fun [setItemCommentComment](set-item-comment-comment.md)(comment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the comment to the specified string. |
| [setItemCommentDate](set-item-comment-date.md) | [androidJvm]<br>fun [setItemCommentDate](set-item-comment-date.md)(date: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the date when the comment was posted to the specified string. |
| [setItemCommentEdit](set-item-comment-edit.md) | [androidJvm]<br>fun [setItemCommentEdit](set-item-comment-edit.md)(editable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Allows the user to edit a comment posted by them but disallows them to edit a comment created by another user. |
| [setItemCommentOptionsOnClickListener](set-item-comment-options-on-click-listener.md) | [androidJvm]<br>fun [setItemCommentOptionsOnClickListener](set-item-comment-options-on-click-listener.md)(onClickListener: [View.OnClickListener](https://developer.android.com/reference/kotlin/android/view/View.OnClickListener.html))<br>Sets the onclick listener of the options icon displayed alongside a comment. |
| [setItemCommentProfileOnClickListener](set-item-comment-profile-on-click-listener.md) | [androidJvm]<br>fun [setItemCommentProfileOnClickListener](set-item-comment-profile-on-click-listener.md)(onClickListener: [View.OnClickListener](https://developer.android.com/reference/kotlin/android/view/View.OnClickListener.html))<br>Sets the onclick listener of the profile picture of the user who posted the comment. |
| [setItemCommentProfilePic](set-item-comment-profile-pic.md) | [androidJvm]<br>fun [setItemCommentProfilePic](set-item-comment-profile-pic.md)(picture: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sets the profile picture of the user who posted the comment to the photo specified by the given URI. |
| [setItemCommentUsername](set-item-comment-username.md) | [androidJvm]<br>fun [setItemCommentUsername](set-item-comment-username.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Sets the username of the user who posted the comment to the specified string. |

## Inherited functions

| Name | Summary |
|---|---|
| [addChangePayload](../-search-view-holder/index.md#261292935%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addChangePayload](../-search-view-holder/index.md#261292935%2FFunctions%2F-912451524)(p0: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)) |
| [addFlags](../-search-view-holder/index.md#-98255429%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addFlags](../-search-view-holder/index.md#-98255429%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [clearOldPosition](../-search-view-holder/index.md#1542333312%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearOldPosition](../-search-view-holder/index.md#1542333312%2FFunctions%2F-912451524)() |
| [clearPayload](../-search-view-holder/index.md#-1678162526%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearPayload](../-search-view-holder/index.md#-1678162526%2FFunctions%2F-912451524)() |
| [clearReturnedFromScrapFlag](../-search-view-holder/index.md#-1553068564%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearReturnedFromScrapFlag](../-search-view-holder/index.md#-1553068564%2FFunctions%2F-912451524)() |
| [clearTmpDetachFlag](../-search-view-holder/index.md#923797466%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearTmpDetachFlag](../-search-view-holder/index.md#923797466%2FFunctions%2F-912451524)() |
| [createPayloadsIfNeeded](../-search-view-holder/index.md#1328516304%2FFunctions%2F-912451524) | [androidJvm]<br>private open fun [createPayloadsIfNeeded](../-search-view-holder/index.md#1328516304%2FFunctions%2F-912451524)() |
| [doesTransientStatePreventRecycling](../-search-view-holder/index.md#950310091%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [doesTransientStatePreventRecycling](../-search-view-holder/index.md#950310091%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [flagRemovedAndOffsetPosition](../-search-view-holder/index.md#-1556540183%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [flagRemovedAndOffsetPosition](../-search-view-holder/index.md#-1556540183%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p2: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [getAbsoluteAdapterPosition](../-search-view-holder/index.md#358648312%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getAbsoluteAdapterPosition](../-search-view-holder/index.md#358648312%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getAdapterPosition](../-search-view-holder/index.md#644519777%2FFunctions%2F-912451524) | [androidJvm]<br>~~fun~~ [~~getAdapterPosition~~](../-search-view-holder/index.md#644519777%2FFunctions%2F-912451524)~~(~~~~)~~~~:~~ [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBindingAdapter](../-search-view-holder/index.md#-646392777%2FFunctions%2F-912451524) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)()<br>fun [getBindingAdapter](../-search-view-holder/index.md#-646392777%2FFunctions%2F-912451524)(): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)<out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)>? |
| [getBindingAdapterPosition](../-search-view-holder/index.md#1427640590%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getBindingAdapterPosition](../-search-view-holder/index.md#1427640590%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getItemId](../-search-view-holder/index.md#1378485811%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemId](../-search-view-holder/index.md#1378485811%2FFunctions%2F-912451524)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getItemViewType](../-search-view-holder/index.md#-1649344625%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemViewType](../-search-view-holder/index.md#-1649344625%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getLayoutPosition](../-search-view-holder/index.md#-1407255826%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getLayoutPosition](../-search-view-holder/index.md#-1407255826%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getOldPosition](../-search-view-holder/index.md#-1203059319%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getOldPosition](../-search-view-holder/index.md#-1203059319%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getPosition](../-search-view-holder/index.md#-1155470344%2FFunctions%2F-912451524) | [androidJvm]<br>~~fun~~ [~~getPosition~~](../-search-view-holder/index.md#-1155470344%2FFunctions%2F-912451524)~~(~~~~)~~~~:~~ [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getUnmodifiedPayloads](../-search-view-holder/index.md#-1340096838%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getUnmodifiedPayloads](../-search-view-holder/index.md#-1340096838%2FFunctions%2F-912451524)(): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [hasAnyOfTheFlags](../-search-view-holder/index.md#-1508071070%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [hasAnyOfTheFlags](../-search-view-holder/index.md#-1508071070%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAdapterPositionUnknown](../-search-view-holder/index.md#-38574553%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isAdapterPositionUnknown](../-search-view-holder/index.md#-38574553%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAttachedToTransitionOverlay](../-search-view-holder/index.md#335386437%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isAttachedToTransitionOverlay](../-search-view-holder/index.md#335386437%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isBound](../-search-view-holder/index.md#-871435581%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isBound](../-search-view-holder/index.md#-871435581%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInvalid](../-search-view-holder/index.md#1764418410%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isInvalid](../-search-view-holder/index.md#1764418410%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRecyclable](../-search-view-holder/index.md#-1703443315%2FFunctions%2F-912451524) | [androidJvm]<br>fun [isRecyclable](../-search-view-holder/index.md#-1703443315%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRemoved](../-search-view-holder/index.md#903910689%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isRemoved](../-search-view-holder/index.md#903910689%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isScrap](../-search-view-holder/index.md#1114019792%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isScrap](../-search-view-holder/index.md#1114019792%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isTmpDetached](../-search-view-holder/index.md#1073894904%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isTmpDetached](../-search-view-holder/index.md#1073894904%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isUpdated](../-search-view-holder/index.md#-1973462746%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isUpdated](../-search-view-holder/index.md#-1973462746%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [needsUpdate](../-search-view-holder/index.md#-847853903%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [needsUpdate](../-search-view-holder/index.md#-847853903%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [offsetPosition](../-search-view-holder/index.md#-626976801%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [offsetPosition](../-search-view-holder/index.md#-626976801%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [onEnteredHiddenState](../-search-view-holder/index.md#-1314651163%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onEnteredHiddenState](../-search-view-holder/index.md#-1314651163%2FFunctions%2F-912451524)(p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onLeftHiddenState](../-search-view-holder/index.md#-142764541%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onLeftHiddenState](../-search-view-holder/index.md#-142764541%2FFunctions%2F-912451524)(p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [resetInternal](../-search-view-holder/index.md#-439112821%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [resetInternal](../-search-view-holder/index.md#-439112821%2FFunctions%2F-912451524)() |
| [saveOldPosition](../-search-view-holder/index.md#-1570989724%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [saveOldPosition](../-search-view-holder/index.md#-1570989724%2FFunctions%2F-912451524)() |
| [setFlags](../-search-view-holder/index.md#1913047905%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setFlags](../-search-view-holder/index.md#1913047905%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setIsRecyclable](../-search-view-holder/index.md#-1860912636%2FFunctions%2F-912451524) | [androidJvm]<br>fun [setIsRecyclable](../-search-view-holder/index.md#-1860912636%2FFunctions%2F-912451524)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setScrapContainer](../-search-view-holder/index.md#-1794523421%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setScrapContainer](../-search-view-holder/index.md#-1794523421%2FFunctions%2F-912451524)(p0: [RecyclerView.Recycler](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Recycler.html), p1: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [shouldBeKeptAsChild](../-search-view-holder/index.md#2126280289%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [shouldBeKeptAsChild](../-search-view-holder/index.md#2126280289%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldIgnore](../-search-view-holder/index.md#-1576574146%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [shouldIgnore](../-search-view-holder/index.md#-1576574146%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [stopIgnoring](../-search-view-holder/index.md#1900238322%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [stopIgnoring](../-search-view-holder/index.md#1900238322%2FFunctions%2F-912451524)() |
| [toString](../-search-view-holder/index.md#-1200015593%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [toString](../-search-view-holder/index.md#-1200015593%2FFunctions%2F-912451524)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [unScrap](../-search-view-holder/index.md#1008577791%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [unScrap](../-search-view-holder/index.md#1008577791%2FFunctions%2F-912451524)() |
| [wasReturnedFromScrap](../-search-view-holder/index.md#662064276%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [wasReturnedFromScrap](../-search-view-holder/index.md#662064276%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
