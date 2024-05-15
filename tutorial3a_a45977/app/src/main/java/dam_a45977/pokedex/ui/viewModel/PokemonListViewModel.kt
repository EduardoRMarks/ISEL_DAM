package dam_a45977.pokedex.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.network.NetworkModule
import dam_a45977.pokedex.data.model.repositories.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>?>()

    val pokemonList: MutableLiveData<List<Pokemon>?>
        get() = _pokemonList

    private lateinit var _repository: PokemonRepository
    fun initViewMode(repository: PokemonRepository) {
        _repository = repository
    }

    fun fetchPokemons(generation: Int) {
        //_pokemonList.value = dam_a45977.pokedex.data.model.mocks.MockData.getPokemon(generation = generation)
        /*viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchPokemonByRegionId(generation)

            var pokemonsList = response.pokemons.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()

                var pokemonId = regexToGetId.find(it.url!!)?.value
                pokemonId = pokemonId?.removeSurrounding("/")

                val imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                        "/sprites/pokemon/other/official-artwork/${pokemonId?.toInt()}.png"

                var pokemonName = it.name.toString()
                pokemonName = pokemonName?.capitalize().toString()

                Pokemon(pokemonId?.toInt() ?: 0, pokemonName, imgUrl, generation, null)
            }
            if (pokemonsList != null) {
                pokemonsList = pokemonsList.sortedBy { it.id }
            }
            _pokemonList.postValue(pokemonsList)
        }*/
        viewModelScope.launch(Dispatchers.Default) {
            val pkList = _repository.fetchPokemons(generation)
            _pokemonList.postValue(pkList.value)
        }
    }
}