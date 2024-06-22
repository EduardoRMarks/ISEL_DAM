package dam_a45977.projeto.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.data.model.BookInList
import dam_a45977.projeto.ui.CollectionListActivity

class CollectionListViewModel : ViewModel() {

    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _books = MutableLiveData<List<BookInList>?>()
    val books: LiveData<List<BookInList>?> get() = _books

    fun fetchBooks(collectionName: String) {
        val currentUserId = fAuth.currentUser?.uid ?: return

        db.collection("bookInCollection")
            .whereEqualTo("owner", currentUserId)
            .whereEqualTo("collection", collectionName)
            .get()
            .addOnSuccessListener { documents ->
                val collectionsList = documents.map { document ->
                    val bookCover = document.getString("cover") ?: ""
                    BookInList(
                        title = document.getString("title") ?: "",
                        authorName = document.getString("authorName") ?: "",
                        isbn = document.getString("isbn") ?: "",
                        bookCover = bookCover,
                        numberOfPages = document.getString("numberOfPages") ?: "",
                        )
                }
                _books.value = collectionsList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    fun deleteCollection(collectionName: String?){
        val currentUserId = fAuth.currentUser?.uid ?: return

        db.collection("bookInCollection")
            .whereEqualTo("owner", currentUserId)
            .whereEqualTo("collection", collectionName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val batch = db.batch()
                    for (document in task.result!!) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { e ->

                        }
                } else {

                }
            }

        db.collection("collection")
            .whereEqualTo("owner", currentUserId)
            .whereEqualTo("name", collectionName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val batch = db.batch()
                    for (document in task.result!!) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { e ->
                        }
                } else {
                }
            }

    }
}