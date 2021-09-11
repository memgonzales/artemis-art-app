//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[SearchViewHolder](index.md)

# SearchViewHolder

[androidJvm]\
class [SearchViewHolder](index.md)(**itemView**: [View](https://developer.android.com/reference/kotlin/android/view/View.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)

## Properties

| Name | Summary |
|---|---|
| [ivItemSearchResults](iv-item-search-results.md) | [androidJvm]<br>private val [ivItemSearchResults](iv-item-search-results.md): [ImageView](https://developer.android.com/reference/kotlin/android/widget/ImageView.html) |
| [storage](storage.md) | [androidJvm]<br>private var [storage](storage.md): FirebaseStorage |
| [storageRef](storage-ref.md) | [androidJvm]<br>private var [storageRef](storage-ref.md): StorageReference |

## Inherited properties

| Name | Summary |
|---|---|
| [itemView](index.md#581519175%2FProperties%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)()<br>val [itemView](index.md#581519175%2FProperties%2F-912451524): [View](https://developer.android.com/reference/kotlin/android/view/View.html) |
| [mBindingAdapter](index.md#-1674235484%2FProperties%2F-912451524) | [androidJvm]<br>val [mBindingAdapter](index.md#-1674235484%2FProperties%2F-912451524): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)<out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)> |
| [mFlags](index.md#638018629%2FProperties%2F-912451524) | [androidJvm]<br>val [mFlags](index.md#638018629%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mInChangeScrap](index.md#2027139536%2FProperties%2F-912451524) | [androidJvm]<br>val [mInChangeScrap](index.md#2027139536%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [mIsRecyclableCount](index.md#-933564653%2FProperties%2F-912451524) | [androidJvm]<br>private val [mIsRecyclableCount](index.md#-933564653%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mItemId](index.md#58532000%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemId](index.md#58532000%2FProperties%2F-912451524): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [mItemViewType](index.md#-1395665860%2FProperties%2F-912451524) | [androidJvm]<br>val [mItemViewType](index.md#-1395665860%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mNestedRecyclerView](index.md#1627054387%2FProperties%2F-912451524) | [androidJvm]<br>val [mNestedRecyclerView](index.md#1627054387%2FProperties%2F-912451524): [WeakReference](https://developer.android.com/reference/kotlin/java/lang/ref/WeakReference.html)<[RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)> |
| [mOldPosition](index.md#-86497476%2FProperties%2F-912451524) | [androidJvm]<br>val [mOldPosition](index.md#-86497476%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mOwnerRecyclerView](index.md#-1236978571%2FProperties%2F-912451524) | [androidJvm]<br>val [mOwnerRecyclerView](index.md#-1236978571%2FProperties%2F-912451524): [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html) |
| [mPayloads](index.md#-1365669079%2FProperties%2F-912451524) | [androidJvm]<br>val [mPayloads](index.md#-1365669079%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mPendingAccessibilityState](index.md#-1161242926%2FProperties%2F-912451524) | [androidJvm]<br>val [mPendingAccessibilityState](index.md#-1161242926%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPosition](index.md#1679236901%2FProperties%2F-912451524) | [androidJvm]<br>val [mPosition](index.md#1679236901%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mPreLayoutPosition](index.md#-844506314%2FProperties%2F-912451524) | [androidJvm]<br>val [mPreLayoutPosition](index.md#-844506314%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mScrapContainer](index.md#-1837165122%2FProperties%2F-912451524) | [androidJvm]<br>val [mScrapContainer](index.md#-1837165122%2FProperties%2F-912451524): [RecyclerView.Recycler](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Recycler.html) |
| [mShadowedHolder](index.md#-734765693%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowedHolder](index.md#-734765693%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mShadowingHolder](index.md#-1380118818%2FProperties%2F-912451524) | [androidJvm]<br>val [mShadowingHolder](index.md#-1380118818%2FProperties%2F-912451524): [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |
| [mUnmodifiedPayloads](index.md#-647651929%2FProperties%2F-912451524) | [androidJvm]<br>val [mUnmodifiedPayloads](index.md#-647651929%2FProperties%2F-912451524): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [mWasImportantForAccessibilityBeforeHidden](index.md#-2036040569%2FProperties%2F-912451524) | [androidJvm]<br>private val [mWasImportantForAccessibilityBeforeHidden](index.md#-2036040569%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [getItemSearchResults](get-item-search-results.md) | [androidJvm]<br>fun [getItemSearchResults](get-item-search-results.md)(): [ImageView](https://developer.android.com/reference/kotlin/android/widget/ImageView.html) |
| [setItemSearchResults](set-item-search-results.md) | [androidJvm]<br>fun [setItemSearchResults](set-item-search-results.md)(post: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Inherited functions

| Name | Summary |
|---|---|
| [addChangePayload](index.md#261292935%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addChangePayload](index.md#261292935%2FFunctions%2F-912451524)(p0: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)) |
| [addFlags](index.md#-98255429%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addFlags](index.md#-98255429%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [clearOldPosition](index.md#1542333312%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearOldPosition](index.md#1542333312%2FFunctions%2F-912451524)() |
| [clearPayload](index.md#-1678162526%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearPayload](index.md#-1678162526%2FFunctions%2F-912451524)() |
| [clearReturnedFromScrapFlag](index.md#-1553068564%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearReturnedFromScrapFlag](index.md#-1553068564%2FFunctions%2F-912451524)() |
| [clearTmpDetachFlag](index.md#923797466%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [clearTmpDetachFlag](index.md#923797466%2FFunctions%2F-912451524)() |
| [createPayloadsIfNeeded](index.md#1328516304%2FFunctions%2F-912451524) | [androidJvm]<br>private open fun [createPayloadsIfNeeded](index.md#1328516304%2FFunctions%2F-912451524)() |
| [doesTransientStatePreventRecycling](index.md#950310091%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [doesTransientStatePreventRecycling](index.md#950310091%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [flagRemovedAndOffsetPosition](index.md#-1556540183%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [flagRemovedAndOffsetPosition](index.md#-1556540183%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p2: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [getAbsoluteAdapterPosition](index.md#358648312%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getAbsoluteAdapterPosition](index.md#358648312%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getAdapterPosition](index.md#644519777%2FFunctions%2F-912451524) | [androidJvm]<br>~~fun~~ [~~getAdapterPosition~~](index.md#644519777%2FFunctions%2F-912451524)~~(~~~~)~~~~:~~ [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBindingAdapter](index.md#-646392777%2FFunctions%2F-912451524) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)()<br>fun [getBindingAdapter](index.md#-646392777%2FFunctions%2F-912451524)(): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)<out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)>? |
| [getBindingAdapterPosition](index.md#1427640590%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getBindingAdapterPosition](index.md#1427640590%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getItemId](index.md#1378485811%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemId](index.md#1378485811%2FFunctions%2F-912451524)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getItemViewType](index.md#-1649344625%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemViewType](index.md#-1649344625%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getLayoutPosition](index.md#-1407255826%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getLayoutPosition](index.md#-1407255826%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getOldPosition](index.md#-1203059319%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getOldPosition](index.md#-1203059319%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getPosition](index.md#-1155470344%2FFunctions%2F-912451524) | [androidJvm]<br>~~fun~~ [~~getPosition~~](index.md#-1155470344%2FFunctions%2F-912451524)~~(~~~~)~~~~:~~ [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getUnmodifiedPayloads](index.md#-1340096838%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getUnmodifiedPayloads](index.md#-1340096838%2FFunctions%2F-912451524)(): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)<[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)> |
| [hasAnyOfTheFlags](index.md#-1508071070%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [hasAnyOfTheFlags](index.md#-1508071070%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAdapterPositionUnknown](index.md#-38574553%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isAdapterPositionUnknown](index.md#-38574553%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isAttachedToTransitionOverlay](index.md#335386437%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isAttachedToTransitionOverlay](index.md#335386437%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isBound](index.md#-871435581%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isBound](index.md#-871435581%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInvalid](index.md#1764418410%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isInvalid](index.md#1764418410%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRecyclable](index.md#-1703443315%2FFunctions%2F-912451524) | [androidJvm]<br>fun [isRecyclable](index.md#-1703443315%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRemoved](index.md#903910689%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isRemoved](index.md#903910689%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isScrap](index.md#1114019792%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isScrap](index.md#1114019792%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isTmpDetached](index.md#1073894904%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isTmpDetached](index.md#1073894904%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isUpdated](index.md#-1973462746%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [isUpdated](index.md#-1973462746%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [needsUpdate](index.md#-847853903%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [needsUpdate](index.md#-847853903%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [offsetPosition](index.md#-626976801%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [offsetPosition](index.md#-626976801%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [onEnteredHiddenState](index.md#-1314651163%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onEnteredHiddenState](index.md#-1314651163%2FFunctions%2F-912451524)(p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onLeftHiddenState](index.md#-142764541%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onLeftHiddenState](index.md#-142764541%2FFunctions%2F-912451524)(p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [resetInternal](index.md#-439112821%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [resetInternal](index.md#-439112821%2FFunctions%2F-912451524)() |
| [saveOldPosition](index.md#-1570989724%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [saveOldPosition](index.md#-1570989724%2FFunctions%2F-912451524)() |
| [setFlags](index.md#1913047905%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setFlags](index.md#1913047905%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setIsRecyclable](index.md#-1860912636%2FFunctions%2F-912451524) | [androidJvm]<br>fun [setIsRecyclable](index.md#-1860912636%2FFunctions%2F-912451524)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setScrapContainer](index.md#-1794523421%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setScrapContainer](index.md#-1794523421%2FFunctions%2F-912451524)(p0: [RecyclerView.Recycler](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Recycler.html), p1: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [shouldBeKeptAsChild](index.md#2126280289%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [shouldBeKeptAsChild](index.md#2126280289%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [shouldIgnore](index.md#-1576574146%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [shouldIgnore](index.md#-1576574146%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [stopIgnoring](index.md#1900238322%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [stopIgnoring](index.md#1900238322%2FFunctions%2F-912451524)() |
| [toString](index.md#-1200015593%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [toString](index.md#-1200015593%2FFunctions%2F-912451524)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [unScrap](index.md#1008577791%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [unScrap](index.md#1008577791%2FFunctions%2F-912451524)() |
| [wasReturnedFromScrap](index.md#662064276%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [wasReturnedFromScrap](index.md#662064276%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
