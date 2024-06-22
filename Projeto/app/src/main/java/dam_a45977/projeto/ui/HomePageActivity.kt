package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityHomePageBinding
import dam_a45977.projeto.ui.viewModel.HomePageViewModel

class HomePageActivity : BottomNavActivity() {
    val viewModel: HomePageViewModel by viewModels()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homePage = binding as ActivityHomePageBinding
        fetchUserData(homePage)

        val logOutButton: Button = findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {
            fAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun fetchUserData(homePage: ActivityHomePageBinding) {
        val user = fAuth.currentUser
        val userId = user?.uid ?: return

        db.collection("client").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")
                    homePage.username.text = "Welcome, $firstName $lastName!"
                } else {
                    Log.d("HomePageActivity", "No such document")
                    fAuth.signOut()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("HomePageActivity", "get failed with ", exception)
            }
    }

    override val contentViewId: Int
        get() = R.layout.activity_home_page
    override val navigationMenuItemId: Int
        get() = R.id.navigation_home
}