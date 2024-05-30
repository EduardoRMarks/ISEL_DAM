package dam_a45977.projeto.data.model

import android.os.Parcelable


@kotlinx.parcelize.Parcelize
data class Book(
    var title: String,
    val authorName: List<String>?,
    val isbn: List<String>?,
    var bookCover: String?,
    var numberOfPages: Int?,
//    var genre: String,
//    var year: Int,
//    var rating: Float,
//    var description: String
) : Parcelable