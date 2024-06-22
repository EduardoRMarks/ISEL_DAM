package dam_a45977.projeto.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.databinding.ActivityHomePageBinding

class NewCollectionViewModel : ViewModel() {

    private val TAG = "NewCollection"
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var collectionName = ""
    var collectionGenre = ""

    private val _toCollectionsPage = MutableLiveData<Boolean>()
    val toCollectionsPage: LiveData<Boolean> get() = _toCollectionsPage

    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> get() = _showMessage

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> get() = _userEmail

    fun createCollection(){
        if (collectionName.isBlank() || collectionGenre.isBlank()) {
            _showMessage.value = "All fields are required"
            return
        }
        saveCollection()
    }

    private fun saveCollection() {
        val user = fAuth.currentUser
        val userId = user?.uid ?: return

        val collection = hashMapOf(
            "owner" to userId,
            "name" to collectionName,
            "genre" to collectionGenre
        )

        db.collection("collection")
            .add(collection)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                _toCollectionsPage.value = true
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}