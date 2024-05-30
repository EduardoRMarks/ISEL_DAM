package dam_a45977.projeto.ui

import android.os.Bundle
import androidx.activity.viewModels
import dam_a45977.projeto.ui.viewModel.SearchPageViewModel
import dam_a45977.projeto.R

class HomePageActivity : BottomNavActivity() {
    //val viewModel: SearchPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel.fetchBooks()
    }

    override val contentViewId: Int
        get() = R.layout.home_page_activity
    override val navigationMenuItemId: Int
        get() = R.id.navigation_home
}