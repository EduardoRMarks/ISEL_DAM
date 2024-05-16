package dam_a45977.pokedex.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon_region")
data class PokemonRegion(
    @PrimaryKey
    @ColumnInfo(name = "region_id")
    var id: Int,
    @ColumnInfo(name = "region_name")
    var name: String,
    @DrawableRes val bg: Int,
    @DrawableRes val starters: Int
) : Parcelable