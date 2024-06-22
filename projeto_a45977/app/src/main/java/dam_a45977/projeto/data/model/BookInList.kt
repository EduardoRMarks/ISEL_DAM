package dam_a45977.projeto.data.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class BookInList (
    var title: String,
    val authorName: String?,
    val isbn: String?,
    var bookCover: String?,
    var numberOfPages: String?,
) : Parcelable