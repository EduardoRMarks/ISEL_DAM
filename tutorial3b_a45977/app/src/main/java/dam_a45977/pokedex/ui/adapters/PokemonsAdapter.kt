package dam_a45977.pokedex.ui.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
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
import dam_a45977.pokedex.data.model.PokemonRegion
import dam_a45977.pokedex.data.model.PokemonType
import dam_a45977.pokedex.databinding.ItemPokemonBinding
import dam_a45977.pokedex.databinding.ItemRegionBinding
import dam_a45977.pokedex.ui.PokemonDetailActivity


class PokemonsAdapter(
    private val pokemonList: List<Pokemon>,
    private val context: Context
) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonItemBinding = ItemPokemonBinding.bind(itemView)
        fun bindView(pokemon: Pokemon) {
            pokemonItemBinding.pokemon = pokemon
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonsAdapter.ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.bindView(pokemon)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PokemonDetailActivity::class.java)
            intent.putExtra("pokemon", pokemon)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}


/*

        Glide.with(holder.pkImageView.context)
            .asBitmap()
            .load(pokemon.imageUrl)
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
                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb
                        if (rgb != null) {
                            holder.cardView.setCardBackgroundColor(rgb)
                        }
                    }
                    return false
                }
            })
            .into(holder.pkImageView)
        holder.pkNameTextView.text = pokemon.name
        holder.pkIDTextView.text = "#" + pokemon.id
* */
