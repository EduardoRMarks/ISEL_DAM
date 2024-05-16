package dam_a45977.pokedex.data.model

class PokemonDetail(
    var pokemon: Pokemon,
    var description: String,
    var weight: Double,
    var height: Double,
    var types: List<PokemonType>?
)