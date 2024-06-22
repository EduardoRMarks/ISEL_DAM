package dam_a45977.projeto.ui

import android.os.Bundle
import androidx.activity.viewModels
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityHomePageBinding
import dam_a45977.projeto.databinding.UserActivityBinding
import dam_a45977.projeto.ui.viewModel.HomePageViewModel

class UserActivity : BottomNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homePage = binding as UserActivityBinding


        val userData: HashMap<String, Any>? = intent.getSerializableExtra("userData") as? HashMap<String, Any>

        if (userData != null) {
            val email = userData["email"] as String
            val firstName = userData["firstName"] as String
            val lastName = userData["lastName"] as String
            //homePage.username.text = "Welcome, $firstName $lastName!"
        }

        setContentView(binding.root)
    }

    override val contentViewId: Int
        get() = R.layout.user_activity
    override val navigationMenuItemId: Int
        get() = R.id.navigation_user
}