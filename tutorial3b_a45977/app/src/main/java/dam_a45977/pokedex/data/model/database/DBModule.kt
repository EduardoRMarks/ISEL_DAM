package dam_a45977.pokedex.data.model.database

import android.content.Context
import dam_a45977.pokedex.data.model.network.NetworkModule
import dam_a45977.pokedex.data.model.network.PokemonApi
import dam_a45977.pokedex.data.model.repositories.PokemonRepository
import dam_a45977.pokedex.data.model.repositories.RegionRepository

class DBModule(private val context: Context) {

    val pokemonClient: PokemonApi

    val regionRepository : RegionRepository

    val pokemonDBManager : PokemonDatabase

    var pokemonRepository: PokemonRepository

    companion object {
        // For Singleton instantiation
        @Volatile private var instance : DBModule ? = null
        fun getInstance (context : Context): DBModule {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                return DBModule(context)
            }
            return instance!!
        }
    }

    init {
        pokemonClient = NetworkModule.initPokemonRemoteService()
        pokemonDBManager = PokemonDatabase.getInstance(context)
        regionRepository = RegionRepository(pokemonClient,pokemonDBManager.regionDao())
        pokemonRepository = PokemonRepository(pokemonClient,pokemonDBManager.pokemonDao())
    }
}