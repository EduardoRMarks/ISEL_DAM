package dam_a45977.pokedex.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_id")
    var id: Int,

    @ColumnInfo(name = "pokemon_name")
    var name: String,

    @ColumnInfo(name = "pokemon_imgUrl")
    var imageUrl: String,
    //var region : PokemonRegion,
    @ColumnInfo(name = "region_id")
    var region: Int,

    //@ColumnInfo(name = "pokemon_types")
    //var pokemonTypeList: List<PokemonType>?
): Parcelable
