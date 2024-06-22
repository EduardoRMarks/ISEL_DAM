package dam_a45977.projeto.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a45977.projeto.data.model.Book
import dam_a45977.projeto.data.model.network.NetworkModule
import dam_a45977.projeto.data.model.network.responses.BookByTitleResponse
import dam_a45977.projeto.data.model.network.responses.BooksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class BookViewModel : ViewModel(){
    private val _books = MutableLiveData<List<Book>?>()

    val book: MutableLiveData<List<Book>?>
        get() = _books

    fun fetchBook(searchValue: String){
        viewModelScope.launch(Dispatchers.IO) {
            val call = NetworkModule.client.searchBooksByISBN(searchValue)

            val response = call?.execute()

            if (response != null) {
                if (response.isSuccessful) {
                    val booksList = response.body()?.results
                        ?.filter { it.title != null && it.cover != null && it.isbn != null }
                        ?.map {
                            val regexToGetId = "/([^/]+)/?\$".toRegex()
                            val bookId = regexToGetId.find(it.title!!)?.value
                            val bookCover =
                                "https://covers.openlibrary.org/b/olid/${it.cover}-M.jpg"
                            Book(it.title, it.author, it.isbn, bookCover,
                                it.numberOfPages.toString()
                            )
                        }

                    _books.postValue(booksList)
                } else {
                    // Handle error
                }
            }
        }
    }

}