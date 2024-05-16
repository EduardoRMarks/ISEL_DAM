package dam_a45977.pokedex.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.databinding.ActivityPokemonDetailBinding
import dam_a45977.pokedex.ui.adapters.PokemonDetailAdapter
import dam_a45977.pokedex.ui.viewModel.PokemonDetailsViewModel

class PokemonDetailActivity : AppCompatActivity() {

    val viewModel: PokemonDetailsViewModel by viewModels()
    private lateinit var binding : ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pokemon_detail)
        enableEdgeToEdge()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_detail)
        val pokemonDetailBinding = binding as ActivityPokemonDetailBinding
        var listView = pokemonDetailBinding.typesRecyclerView

        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        viewModel.pokemonDetails.observe(this) {
            if (it != null) {
                pokemonDetailBinding.pokemonDetails = it
                listView.adapter = it.types?.let { it1 -> PokemonDetailAdapter(it1, this) }
                    //it.pokemonDe.pokemonTypeList?.let { it1 -> PokemonDetailAdapter(it1, this) }
            }
        }


        if (pokemon != null) {
            viewModel.fetchPokemonDetails(pokemon)
        }


        /*
        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")

        val pokemonDetailBackgroundCard = findViewById<CardView>(R.id.pokemonDetailBackgroundCard)
        val pokemonImage = findViewById<ImageView>(R.id.pokemonImage)
        val pokemonNumber = findViewById<TextView>(R.id.pokemonNumber)
        val pokemonName = findViewById<TextView>(R.id.pokemonName)
        val pokedexEntry = findViewById<TextView>(R.id.pokedexEntry)
        val weightValue = findViewById<TextView>(R.id.weightValue)
        val heightValue = findViewById<TextView>(R.id.heightValue)
        val listView = findViewById<RecyclerView>(R.id.typesRecyclerView)



        // Mostrar os detalhes do Pokemon
        pokemon?.let {
            val pokemonDetail = MockData.getDetails(it)

            listView.adapter = pokemonDetail.pokemon.pokemonTypeList?.let { it1 -> PokemonDetailAdapter(typeList = it1, context = this) }

            pokemonName.text = pokemonDetail.pokemon.name
            pokemonNumber.text = "#" + pokemonDetail.pokemon.id

            // adicionar mais detalhes

            // exibir a imagem do Pokemon por Glide
            Glide.with(this)
                .asBitmap()
                .load(pokemonDetail.pokemon.imageUrl)
                .listener(object : RequestListener<Bitmap>
                {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>,
                        isFirstResource: Boolean
                    ): Boolean {

                        Log.d("TAG", e?.message.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        model: Any,
                        p2: Target<Bitmap>?,
                        dataSource: DataSource,
                        p4: Boolean
                    ): Boolean {
                        Log.d("TAG", "OnResourceReady")
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb
                        if (rgb != null) {
                            pokemonDetailBackgroundCard.setCardBackgroundColor(rgb)
                        }
                        return false
                    }
                })
                .into(pokemonImage)
            pokedexEntry.text = pokemonDetail.description
            weightValue.text = pokemonDetail.weight.toString()
            heightValue.text = pokemonDetail.height.toString()
        }*/
    }
}