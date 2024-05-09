package dam_a45977.pokedex.data.model.network

import dam_a45977.pokedex.data.model.network.responses.PokemonByRegionResponse
import dam_a45977.pokedex.data.model.network.responses.PokemonDetailResponse
import dam_a45977.pokedex.data.model.network.responses.PokemonGenericResponse
import dam_a45977.pokedex.data.model.network.responses.PokemonListBaseResponse
import dam_a45977.pokedex.data.model.network.responses.PokemonRegionsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("region")
    suspend fun fetchRegionList(): PokemonListBaseResponse<PokemonRegionsResponse>

    @GET("generation/{id}")
    suspend fun fetchPokemonByRegionId(@Path("id") id:Int): PokemonByRegionResponse

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetailById(@Path("id") id:Int): PokemonDetailResponse

    @GET("type")
    suspend fun fetchPokemonTypes(): PokemonListBaseResponse<PokemonGenericResponse>

}