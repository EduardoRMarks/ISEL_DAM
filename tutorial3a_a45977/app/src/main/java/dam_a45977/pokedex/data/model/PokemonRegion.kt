package dam_a45977.pokedex.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes

@kotlinx.parcelize.Parcelize
data class PokemonRegion(
    var id: Int,
    var name: String,
    @DrawableRes val bg: Int,
    @DrawableRes val starters: Int
) : Parcelable