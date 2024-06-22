package dam_a45977.projeto.ui.adpaters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.projeto.R
import dam_a45977.projeto.data.model.Book
import dam_a45977.projeto.data.model.BookInList
import dam_a45977.projeto.databinding.ItemBookBinding
import dam_a45977.projeto.databinding.ItemBookCollectionBinding
import dam_a45977.projeto.ui.BookActivity

class CollectionListAdapter(
    private val bookList: List<BookInList>,
    private val context: Context
) : RecyclerView.Adapter<CollectionListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookItemBinding = ItemBookCollectionBinding.bind(itemView)
        fun bindView(book: BookInList) {
            bookItemBinding.book = book
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_book_collection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bindView(book)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}