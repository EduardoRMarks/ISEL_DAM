package dam_a45977.projeto.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.data.model.Collection

class CollectionViewModel : ViewModel() {

    private val fAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _collections = MutableLiveData<List<Collection>?>()
    val collections: LiveData<List<Collection>?> get() = _collections

    fun fetchCollections() {
        val currentUserId = fAuth.currentUser?.uid ?: return

        db.collection("collection")
            .whereEqualTo("owner", currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                val collectionsList = documents.map { document ->
                    Collection(
                        owner = document.getString("owner") ?: "",
                        collectionName = document.getString("name") ?: "",
                        collectionGenre = document.getString("genre") ?: "",
                    )
                }
                _collections.value = collectionsList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }
}