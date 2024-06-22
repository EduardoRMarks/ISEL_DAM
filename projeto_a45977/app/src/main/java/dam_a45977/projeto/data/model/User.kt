package dam_a45977.projeto.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
data class User (
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
): Parcelable