package dam_a45977.projeto.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.SearchActivityBinding
import dam_a45977.projeto.ui.adpaters.SearchAdapter
import dam_a45977.projeto.ui.viewModel.SearchPageViewModel

class SearchActivity : BottomNavActivity() {
    val viewModel: SearchPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        this.binding = DataBindingUtil.setContentView(this, R.layout.search_activity)
        val bookSearchList = binding as SearchActivityBinding
//
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.search_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner: Spinner = findViewById(R.id.searchTypeSpinner)
        spinner.adapter = adapter
//
        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val searchType = spinner.selectedItem.toString()
            val searchQuery = findViewById<android.widget.EditText>(R.id.searchText).text.toString()
            viewModel.fetchBooks(searchType, searchQuery)
        }

        viewModel.books.observe(this) {
            bookSearchList.searchRecyclerView.adapter =
                viewModel.books.value?.let { it1 -> SearchAdapter(bookList = it1, context = this) }
        }

        viewModel.fetchRandomBooks()
    }

    override val contentViewId: Int
        get() = R.layout.search_activity
    override val navigationMenuItemId: Int
        get() = R.id.navigation_search
}