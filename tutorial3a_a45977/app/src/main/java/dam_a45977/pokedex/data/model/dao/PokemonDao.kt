package dam_a45977.pokedex.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.RegionWithPokemons

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getPokemons() : List<Pokemon>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: Pokemon)
    @Query("SELECT COUNT(*) FROM pokemon")
    fun count(): Int

    @Transaction
    @Query("SELECT * FROM pokemon_region WHERE region_id = :regionId")
    fun getPokemonByRegion(regionId: Int): RegionWithPokemons
}