package dam_a45977.pokedex.data.model.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dam_a45977.pokedex.data.model.PokemonType

@JsonClass(generateAdapter = true)
data class PokemonListBaseResponse<T>(
    @field:Json(name = "count") val count: Int?,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "results") val results: List<T>?
)

@JsonClass(generateAdapter = true)
data class PokemonRegionsResponse(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class PokemonByRegionResponse(
    @field:Json(name = "pokemon_species") val pokemons: List<PokemonResponse>,
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "types") val types: String?
)

@JsonClass(generateAdapter = true)
data class PokemonGenericResponse(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?,
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val details: PokemonDet
)

@JsonClass(generateAdapter = true)
data class PokemonDet(
    val id: Int,
    val name: String,
    // Add other properties as needed
)