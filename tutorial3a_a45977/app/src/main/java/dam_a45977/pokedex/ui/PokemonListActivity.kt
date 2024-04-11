package dam_a45977.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.ui.handler.EventClickListener
import dam_a45977.pokedex.ui.region.PokemonsAdapter

class PokemonListActivity : AppCompatActivity(), EventClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemonlist)
        var listView = findViewById<RecyclerView>(R.id.pksRecyclerView)
        listView.adapter = PokemonsAdapter(pokemonList = MockData.getPokemon(), context = this)
    }

    override fun onClicked(id: Int) {
        Toast.makeText(this, "Region clicked: $id", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, PokemonListActivity::class.java))
    }
}