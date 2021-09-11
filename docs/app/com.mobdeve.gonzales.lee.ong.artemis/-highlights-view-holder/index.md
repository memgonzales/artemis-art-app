//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[HighlightsViewHolder](index.md)

# HighlightsViewHolder

[androidJvm]\
class [HighlightsViewHolder](index.md)(**itemView**: [View](https://developer.android.com/reference/kotlin/android/view/View.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)

View holder for the recycler view that handles the posts highlighted by the user.

## Parameters

androidJvm

| | |
|---|---|
| itemView | layout for a single item in the recycler view |

## Constructors

| | |
|---|---|
| [HighlightsViewHolder](-highlights-view-holder.md) | [androidJvm]<br>fun [HighlightsViewHolder](-highlights-view-holder.md)(itemView: [View](https://developer.android.com/reference/kotlin/android/view/View.html))<br>Creates a view holder for the recycler view that handles the posts highlighted by the user. |

## Properties

| Name | Summary |
|---|---|
| [ivItemSearchResults](iv-item-search-results.md) | [androidJvm]<br>private val [ivItemSearchResults](iv-item-search-results.md): [ImageView](https://developer.android.com/reference/kotlin/android/widget/ImageView.html)<br>Image view related to the search results |
| [storage](storage.md) | [androidJvm]<br>private var [storage](storage.md): FirebaseStorage<br>Service that supports uploading and downloading large objects to Google Cloud Storage. |
| [storageRef](storage-ref.md) | [androidJvm]<br>private var [storageRef](storage-ref.md): StorageReference<br>Represents a reference to a Google Cloud Storage object. |

## Inherited properties

| Name | Summary |
|---|---|
| [itemView](index.md#723413342%2FProperties%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)()<br>val [itemView](index.md#723413342%2FProperties%2F-912451524): [View](https://developer.android.com/reference/kotlin/android/view/View.html) |
| [mBindingAdapter](index.md#1397807021%2FProperties%2F-912451524) | [androidJvm]<br>val [mBindingAdapter](index.md#1397807021%2FProperties%2F-912451524): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)<out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)> |
| [mFlags](index.md#146546716%2FProperties%2F-912451524) | [androidJvm]<br>val [mFlags](index.md#146546716%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mInChangeScrap](index.md#-367614297%2FProperties%2F-912451524) | [androidJvm]<br>val [mInChangeScrap](index.md#-367614297%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [mIsRecyclableCount](index.md#1121558634%2FProperties%2F-912451524) | [androidJvm]<br>private val [mIsRecyclableCount](index.md#1121558634%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mItemId](index.md#2002771881%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemId](index.md#2002771881%2FProperties%2F-912451524): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [mItemViewType](index.md#-780179323%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemViewType](index.md#-780179323%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mNestedRecyclerView](index.md#911366844%2FProperties%2F-912451524) | [androidJvm]<br>val [mNestedRecyclerView](index.md#911366844%2FProperties%2F-912451524): [WeakReference](https://developer.android.com/reference/kotlin/java/lang/ref/WeakReference.html)<[RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)> |
| [mOldPosition](index.md#-1590663725%2FProperties%2F-912451524) | [androidJvm]<br>val [mOldPosition](index.md#-1590663725%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mOwnerRecyclerView](index.md#818144716%2FProperties%2F-912451524) | [androidJvm]<br>val [mOwnerRecyclerView](index.md#818144716%2FProperties%2F-912451524): [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html) |
| [mPayloads](index.md#-1261917198%2FProperties%2F-912451524) | [androidJvm]<br>val [mPayloads](index.md#-1261917198%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mPendingAccessibilityState](index.md#-1958198999%2FProperties%2F-912451524) | [androidJvm]<br>val [mPendingAccessibilityState](index.md#-1958198999%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPosition](index.md#1782988782%2FProperties%2F-912451524) | [androidJvm]<br>val [mPosition](index.md#1782988782%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPreLayoutPosition](index.md#1210616973%2FProperties%2F-912451524) | [androidJvm]<br>val [mPreLayoutPosition](index.md#1210616973%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mScrapContainer](index.md#1234877383%2FProperties%2F-912451524) | [androidJvm]<br>val [mScrapContainer](index.md#1234877383%2FProperties%2F-912451524): [RecyclerView.Recycler](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Recycler.html) |
| [mShadowedHolder](index.md#-1957690484%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowedHolder](index.md#-1957690484%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mShadowingHolder](index.md#-636081675%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowingHolder](index.md#-636081675%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mUnmodifiedPayloads](index.md#-1363339472%2FProperties%2F-912451524) | [androidJvm]<br>val [mUnmodifiedPayloads](index.md#-1363339472%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mWasImportantForAccessibilityBeforeHidden](index.md#-692700848%2FProperties%2F-912451524) | [androidJvm]<br>private val [mWasImportantForAccessibilityBeforeHidden](index.md#-692700848%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [getItemSearchResults](get-item-search-results.md) | [androidJvm]<br>fun [getItemSearchResults](get-item-search-results.md)(): [ImageView](https://developer.android.com/reference/kotlin/android/widget/ImageView.html)<br>Returns the image view related to the search results. |
| [setItemSearchResults](set-item-search-results.md) | [androidJvm]<br>fun [setItemSearchResults](set-item-search-results.md)(post: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sets the image related to the search results to the photo associated with a post and specified by the given URI. |

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
