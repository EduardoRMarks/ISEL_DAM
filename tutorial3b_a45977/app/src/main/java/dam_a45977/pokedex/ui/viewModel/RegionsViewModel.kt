package dam_a45977.pokedex.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a45977.pokedex.data.model.PokemonRegion
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.data.model.network.NetworkModule
import dam_a45977.pokedex.data.model.repositories.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    private lateinit var _repository: RegionRepository
    fun initViewMode(repository: RegionRepository) {
        _repository = repository
    }

    fun fetchRegions() {
        //_regions.value = MockData.regions
        viewModelScope.launch(Dispatchers.Default) {
            val regionsList = _repository.getRegions()
            _regions.postValue(regionsList.value)
        }
    }
}