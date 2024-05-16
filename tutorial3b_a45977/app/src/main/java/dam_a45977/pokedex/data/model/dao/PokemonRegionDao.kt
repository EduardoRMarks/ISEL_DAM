package dam_a45977.pokedex.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dam_a45977.pokedex.data.model.PokemonRegion

@Dao
interface PokemonRegionDao {
    @Query("SELECT * FROM pokemon_region")
    fun getRegions() : List<PokemonRegion>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegion(region: PokemonRegion)
    @Query("SELECT COUNT(*) FROM pokemon_region")
    fun count(): Int
}