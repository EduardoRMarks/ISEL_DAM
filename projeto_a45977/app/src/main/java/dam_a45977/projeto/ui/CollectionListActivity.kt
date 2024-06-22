package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityBooksCollectionBinding
import dam_a45977.projeto.ui.adpaters.CollectionListAdapter
import dam_a45977.projeto.ui.viewModel.CollectionListViewModel

class CollectionListActivity : AppCompatActivity() {
    private lateinit var viewModel: CollectionListViewModel
    private var collectionName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityBooksCollectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_books_collection)
        viewModel = CollectionListViewModel()

        setContentView(binding.root)

        setupDeleteButton(binding)

        collectionName = intent.getStringExtra("collectionName")
        binding.collectionName.text = collectionName

        viewModel.books.observe(this) {
            binding.searchRecyclerView.adapter =
                viewModel.books.value?.let { it1 -> CollectionListAdapter(bookList = it1, context = this) }
        }

        if (collectionName != null) {
            viewModel.fetchBooks(collectionName!!)
        }
    }

    private fun setupDeleteButton(binding: ActivityBooksCollectionBinding) {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteCollection(collectionName)
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}