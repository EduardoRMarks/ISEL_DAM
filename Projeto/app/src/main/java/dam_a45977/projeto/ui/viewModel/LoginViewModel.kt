package dam_a45977.projeto.ui.viewModel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.data.model.User

class LoginViewModel : ViewModel(){

    private val TAG = "Login"
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var email = ""
    var password = ""

    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> get() = _showMessage

    private val _homeScreen = MutableLiveData<Boolean>()
    val homeScreen: LiveData<Boolean> get() = _homeScreen

    private val _userData = MutableLiveData<Map<String, Any>>()
    val userData: LiveData<Map<String, Any>> get() = _userData

    fun login(){
        if (email.isBlank() || password.isBlank()) {
            _showMessage.value = "Both email and password are required"
            return
        }
        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = fAuth.currentUser
                    if (user != null && user.isEmailVerified) {
                        _showMessage.value = "Login successful"
                        fetchUserData()
                    } else {
                        _showMessage.value = "Email not verified"
                        fAuth.signOut()
                    }
                } else {
                    _showMessage.value = "Authentication failed: ${task.exception?.message}"
                }
            }
    }

    private fun fetchUserData() {
        val user = fAuth.currentUser
        val userId = user?.uid ?: return

        db.collection("client").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    _userData.value = document.data
                    _homeScreen.value = true
                } else {
                    Log.d(TAG, "No such document")
                    fAuth.signOut()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

}