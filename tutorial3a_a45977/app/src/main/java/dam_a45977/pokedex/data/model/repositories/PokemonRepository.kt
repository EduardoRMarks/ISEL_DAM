package dam_a45977.pokedex.data.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.dao.PokemonDao
import dam_a45977.pokedex.data.model.network.PokemonApi

class PokemonRepository(private val pokemonApi: PokemonApi,
                        private val pokemonDao: PokemonDao
)
{
    suspend fun fetchPokemons(generation: Int) : LiveData<List<Pokemon>>{
        try {

            var regionWithPokemons = pokemonDao.getPokemonByRegion(generation)

            if (regionWithPokemons.pokemon.isEmpty()) {
                var pkByRegionResponse = pokemonApi.fetchPokemonByRegionId(generation)
                var pokemonsList = pkByRegionResponse.pokemons.map {
                    val regexToGetId = "/([^/]+)/?\$".toRegex()

                    var pokemonId = regexToGetId.find(it.url!!)?.value
                    pokemonId = pokemonId?.removeSurrounding("/")

                    val imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                            "/sprites/pokemon/other/official-artwork/${pokemonId?.toInt()}.png"

                    var pokemonName = it.name.toString()
                    pokemonName = pokemonName?.capitalize().toString()

                    Pokemon(pokemonId?.toInt() ?: 0, pokemonName, imgUrl, generation)
                }
                if (pokemonsList != null) {
                    pokemonsList = pokemonsList.sortedBy { it.id }
                }
                savePokemonsinDB(pokemonsList)
                return MutableLiveData(pokemonsList)
            } else {
                val pks = regionWithPokemons.pokemon
                return MutableLiveData(pks)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }

        return MutableLiveData<List<Pokemon>>()
    }

    private fun savePokemonsinDB(pokemons: List<Pokemon>) {
        pokemons.forEach {
            it.let { it1 -> pokemonDao.insertPokemon(it1) }
        }

    }
}