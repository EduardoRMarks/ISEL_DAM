package dam_a45977.pokedex.data.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dam_a45977.pokedex.data.model.PokemonRegion
import dam_a45977.pokedex.data.model.dao.PokemonRegionDao
import dam_a45977.pokedex.data.model.network.NetworkModule
import dam_a45977.pokedex.data.model.network.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionRepository(private val pokemonApi: PokemonApi,
                       private val regionDao: PokemonRegionDao)
{
    suspend fun getRegions() : LiveData<List<PokemonRegion>> {
        val hasRegions = regionDao.count()
        if (hasRegions > 0) {
            val regions = regionDao.getRegions()
            return MutableLiveData(regions)
        }
        try {
            val regionsResponse = pokemonApi.fetchRegionList()
            val regions = regionsResponse.results?.map {
                val regexToGetId = "/([^/]+)/?\$".toRegex()
                var regionId = regexToGetId.find(it.url!!)?.value
                regionId = regionId?.removeSurrounding("/")
                PokemonRegion(regionId?.toInt() ?: 0, it.name?.capitalize().toString(), 0, 0)

            }
            val newRegionList = regions?.toMutableList()
            newRegionList?.removeIf{ it.id == 9 }
            newRegionList?.find { it.name == "Paldea" }?.id = 9

            newRegionList?.forEach {
                regionDao.insertRegion(it)
            }
            return MutableLiveData(newRegionList)
        } catch (e: java.lang.Exception) {
            Log.e("ERROR", e.toString())
        }
        return MutableLiveData()
    }
}