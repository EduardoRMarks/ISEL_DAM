package dam_a45977.pokedex.data.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dam_a45977.pokedex.data.model.PokemonRegion
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.dao.PokemonDao
import dam_a45977.pokedex.data.model.dao.PokemonRegionDao

@Database( entities = [PokemonRegion::class, Pokemon::class], version = 2, exportSchema = false )
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun regionDao (): PokemonRegionDao

    abstract fun pokemonDao (): PokemonDao
    companion object {
        // For Singleton instantiation
        @Volatile private var instance : PokemonDatabase ? = null
        fun getInstance ( context : Context): PokemonDatabase {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                instance = Room
                    .databaseBuilder ( context , PokemonDatabase :: class.java , "pokedex_dabase" )
                    .fallbackToDestructiveMigration ()
                    .build ()
            }
            return instance!!
        }
    }
}