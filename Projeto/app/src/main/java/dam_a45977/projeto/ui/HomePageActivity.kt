package dam_a45977.projeto.ui

import android.os.Bundle
import dam_a45977.projeto.R

class HomePageActivity : BottomNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val contentViewId: Int
        get() = R.layout.home_page_activity
    override val navigationMenuItemId: Int
        get() = R.id.navigation_home
}