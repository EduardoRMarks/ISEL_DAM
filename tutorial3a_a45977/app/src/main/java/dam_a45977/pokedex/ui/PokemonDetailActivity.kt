package dam_a45977.pokedex.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.Pokemon
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.ui.adapters.PokemonDetailAdapter
import dam_a45977.pokedex.ui.adapters.PokemonsAdapter

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        enableEdgeToEdge()

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

            listView.adapter = PokemonDetailAdapter(typeList = pokemonDetail.pokemon.pokemonList, context = this)

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
        }
    }
}