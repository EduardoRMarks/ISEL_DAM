package dam_a45977.pokedex.data.model

import androidx.annotation.DrawableRes

data class PokemonRegion(
    var id: Int,
    var name: String,
    @DrawableRes val bg: Int,
    @DrawableRes val starters: Int
)