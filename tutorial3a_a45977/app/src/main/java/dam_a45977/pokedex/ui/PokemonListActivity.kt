package dam_a45977.pokedex.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.database.DBModule
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.databinding.ActivityPokemonlistBinding
import dam_a45977.pokedex.databinding.ActivityRegionsBinding
import dam_a45977.pokedex.ui.adapters.PokemonsAdapter
import dam_a45977.pokedex.ui.viewModel.PokemonListViewModel
import dam_a45977.pokedex.ui.viewModel.RegionsViewModel

class PokemonListActivity : AppCompatActivity() {

    val viewModel: PokemonListViewModel by viewModels()
    private lateinit var binding : ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemonlist)
        val pokemonListBinding = binding as ActivityPokemonlistBinding

        viewModel.initViewMode(DBModule.getInstance(this).pokemonRepository)

        val generation = intent.getIntExtra("generation", 1)

        viewModel.pokemonList.observe(this) {
            pokemonListBinding.pksRecyclerView2.adapter =
                viewModel.pokemonList.value?.let { it1 -> PokemonsAdapter(pokemonList = it1, context = this) }
        }

        viewModel.fetchPokemons(generation)
    }
}
