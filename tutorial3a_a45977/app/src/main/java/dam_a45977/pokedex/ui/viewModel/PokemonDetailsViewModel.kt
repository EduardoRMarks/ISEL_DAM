package dam_a45977.pokedex.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.PokemonDetail
import dam_a45977.pokedex.data.model.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailsViewModel : ViewModel() {
    private val _pokemonDetails = MutableLiveData<PokemonDetail?>()
    val pokemonDetails: LiveData<PokemonDetail?>
        get() = _pokemonDetails

    fun fetchPokemonDetails(pokemon: Pokemon) {
        pokemon.pokemonTypeList = dam_a45977.pokedex.data.model.mocks.MockData.getTypes()
        _pokemonDetails.postValue(dam_a45977.pokedex.data.model.mocks.MockData.getDetails(pokemon))
    }
}