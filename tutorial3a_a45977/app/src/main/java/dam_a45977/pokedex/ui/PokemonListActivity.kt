package dam_a45977.pokedex.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.ui.region.PokemonsAdapter

class PokemonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemonlist)
        var listView = findViewById<RecyclerView>(R.id.pksRecyclerView)
        listView.adapter = PokemonsAdapter(pokemonList = MockData.pokemons, context = this)
    }
}