package dam_a45977.projeto.ui.adpaters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.projeto.R
import dam_a45977.projeto.data.model.Collection
import dam_a45977.projeto.databinding.ItemCollectionBinding
import dam_a45977.projeto.ui.CollectionActivity
import dam_a45977.projeto.ui.CollectionListActivity

class CollectionAdapter (
    private val collectionList: List<Collection>,
    private val context: Context
) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val collectionItemBinding = ItemCollectionBinding.bind(itemView)
        fun bindView(collection: Collection) {
            collectionItemBinding.collection = collection
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = collectionList[position]
        holder.bindView(collection)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CollectionListActivity::class.java)
            intent.putExtra("collectionName", collection.collectionName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return collectionList.size
    }
}