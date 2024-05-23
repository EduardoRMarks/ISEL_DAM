package dam_a45977.projeto.data.model

import android.os.Parcelable


@kotlinx.parcelize.Parcelize
data class Book(
    var id: Int,
    var title: String,
    var author: String,
    var imageUrl: String,
    var genre: String,
    var year: Int,
    var rating: Float,
    var description: String
) : Parcelable