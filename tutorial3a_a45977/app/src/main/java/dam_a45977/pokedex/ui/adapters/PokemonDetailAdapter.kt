package dam_a45977.pokedex.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.PokemonDetail
import dam_a45977.pokedex.data.model.PokemonType

class PokemonDetailAdapter(
    private val typeList: List<PokemonType>,
    private val context: Context
) : RecyclerView.Adapter<PokemonDetailAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardPokemonType = itemView.findViewById<CardView>(R.id.typeCard)
        val typeImage = itemView.findViewById<ImageView>(R.id.pokemonTypeImage)
        val typeName = itemView.findViewById<TextView>(R.id.pokemonTypeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pokemon_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonDetailAdapter.ViewHolder, position: Int) {
        val pokemonType = typeList[position]
        holder.cardPokemonType.setCardBackgroundColor(ContextCompat.getColor(context, pokemonType.color))
        Glide.with(holder.typeImage.context)
            .asBitmap()
            .load(pokemonType.icon)
            .into(holder.typeImage)
        holder.typeName.text = pokemonType.name
    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}

