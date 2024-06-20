package dam_a45977.projeto.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateAccountViewModel : ViewModel(){

    private val TAG = "CreateAccount"
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var firstName = ""
    var lastName = ""
    var email = ""
    var password = ""

    private val _toLoginPage = MutableLiveData<Boolean>()
    val toLoginPage: LiveData<Boolean> get() = _toLoginPage

    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String> get() = _showMessage

    fun signUp(){
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            _showMessage.value = "All fields are required"
            return
        }
        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    sendEmailVerification()
                    saveUser()
                } else {
                    _showMessage.value = "Authentication failed: ${task.exception?.message}"
                }
            }
    }

    private fun sendEmailVerification() {
        val user = fAuth.currentUser
        user?.let {
            it.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "sendEmailVerification:success")
                        _showMessage.value = "Email verification sent"
                    } else {
                        Log.w(TAG, "sendEmailVerification:failure", task.exception)
                        _showMessage.value = "Failed to send verification email"
                    }
                }
        }
    }

    private fun saveUser() {
        val user = fAuth.currentUser
        val userId = user?.uid ?: return

        val userMap = hashMapOf(
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
            "password" to password
        )

        db.collection("client")
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                _toLoginPage.value = true
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
                _showMessage.value = "Failed to save user data"
            }
    }

}