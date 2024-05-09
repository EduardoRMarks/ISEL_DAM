package dam_a45977.pokedex.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.PokemonType
import dam_a45977.pokedex.databinding.ItemTypeBinding

class PokemonDetailAdapter(
    private val typeList: List<PokemonType>,
    private val context: Context
) : RecyclerView.Adapter<PokemonDetailAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonItemBinding = ItemTypeBinding.bind(itemView)
        fun bindView(type: PokemonType) {
            pokemonItemBinding.pokemonType = type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonDetailAdapter.ViewHolder, position: Int) {
        val pokemonType = typeList[position]

        holder.bindView(pokemonType)

        /*
        Glide.with(holder.typeImage.context)
            .asBitmap()
            .load(pokemonType.icon)
            .into(holder.typeImage)
        holder.typeName.text = pokemonType.name*/
    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}

