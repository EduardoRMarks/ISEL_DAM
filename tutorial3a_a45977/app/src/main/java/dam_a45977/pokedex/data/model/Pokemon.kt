package dam_a45977.pokedex.data.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Pokemon(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var region : PokemonRegion,
    var pokemonList: List<PokemonType>
): Parcelable
