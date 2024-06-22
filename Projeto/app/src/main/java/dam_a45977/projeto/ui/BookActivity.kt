package dam_a45977.projeto.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.R
import dam_a45977.projeto.data.model.Collection
import dam_a45977.projeto.databinding.ActivityBookBinding
import dam_a45977.projeto.ui.viewModel.BookViewModel

class BookActivity : AppCompatActivity(){

    private val fAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val viewModel: BookViewModel by viewModels()
    private lateinit var binding : ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_book)
        val bookDataBinding = binding as ActivityBookBinding

        viewModel.book.observe(this) {
            if (it != null) {
                bookDataBinding.book = it[0]
            }
        }

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val userId = fAuth.currentUser?.uid ?: return@setOnClickListener

            fetchCollections(userId) { collections ->
                showCollectionDialog(collections)
            }
        }

        val isbn = intent.getStringExtra("isbn")

        if (isbn != null) {
            viewModel.fetchBook(isbn)
        }
    }

    private fun fetchCollections(userId: String, callback: (List<Collection>) -> Unit) {
        db.collection("collection")
            .whereEqualTo("owner", userId)
            .get()
            .addOnSuccessListener { documents ->
                val collectionsList = documents.map { document ->
                    Collection(
                        owner = document.getString("owner") ?: "",
                        collectionName = document.getString("name") ?: "",
                        collectionGenre = document.getString("genre") ?: "",
                    )
                }
                callback(collectionsList)
            }
            .addOnFailureListener { exception ->
                callback(emptyList())
            }
    }

    private fun showCollectionDialog(collections: List<Collection>) {
        if (collections.isEmpty()) {
            Toast.makeText(this, "No collections found", Toast.LENGTH_SHORT).show()
            return
        }

        val collectionNames = collections.map { it.collectionName }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Add to Collection")
            .setItems(collectionNames) { dialog, which ->
                val collection = collections[which]
                addBookToCollection(collection)
            }
            .show()
    }

    private fun addBookToCollection(collection: Collection) {
        val book = viewModel.book.value?.get(0) ?: return

        val user = fAuth.currentUser
        val userId = user?.uid ?: return

        val bookData = hashMapOf(
            "owner" to userId,
            "collection" to collection.collectionName,
            "title" to book.title,
            "author" to book.authorName,
            "cover" to book.bookCover,
            "pages" to book.numberOfPages,
            "isbn" to (book.isbn?.get(0) ?: "")
        )

        db.collection("bookInCollection")
            .add(bookData)
            .addOnSuccessListener {
                Toast.makeText(this, "Book added to $collection.collectionName", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("BookActivity", "Error adding document", e)
                Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show()
            }
    }
}