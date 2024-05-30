package dam_a45977.projeto.ui.adpaters

import android.content.Context
import android.content.Intent
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.projeto.R
import dam_a45977.projeto.data.model.Book
import dam_a45977.projeto.databinding.ItemBookBinding
import dam_a45977.projeto.ui.SearchActivity

class SearchAdapter (
    private val bookList: List<Book>,
    private val context: Context
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookItemBinding = ItemBookBinding.bind(itemView)
        fun bindView(book: Book) {
            bookItemBinding.book = book
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bindView(book)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isbn", book.isbn?.get(0))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}