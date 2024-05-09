package dam_a45977.pokedex.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a45977.pokedex.data.model.PokemonRegion
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.data.model.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    fun fetchRegions() {
        //_regions.value = MockData.regions

        viewModelScope.launch(Dispatchers.Default) {
            val response = NetworkModule.client.fetchRegionList()

            val regionsList = response.results?.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()

                var regionId = regexToGetId.find(it.url!!)?.value
                regionId = regionId?.removeSurrounding("/")

                PokemonRegion(regionId?.toInt() ?: 0, it.name?.capitalize().toString(), 0, 0)
            }

            val newList = regionsList?.toMutableList()
            newList?.removeIf{ it.id == 9 }
            newList?.find { it.name == "Paldea" }?.id = 9

            _regions.postValue(newList)
        }
    }
}