package dam_a45977.projeto.data.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Collection (
    var owner: String,
    var collectionName: String,
    var collectionGenre: String
): Parcelable