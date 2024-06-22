package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dam_a45977.projeto.R

abstract class BottomNavActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var binding : ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(contentViewId)
        this.binding = DataBindingUtil.setContentView(this, contentViewId)
        navigationView = findViewById(R.id.navigation)
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    // Navigate to RegionsActivity
                    startActivity(Intent(this, HomePageActivity::class.java))
                    true
                }
                R.id.navigation_collections -> {
                    // Navigate to RegionsActivity
                    startActivity(Intent(this, CollectionActivity::class.java))
                    true
                }
                R.id.navigation_search -> {
                    // Navigate to RegionsActivity
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.navigation_user -> {
                    // Navigate to TeamActivity
                    startActivity(Intent(this, UserActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }


    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item = navigationView!!.menu.findItem(itemId)
        item.setChecked(true)
    }

    abstract val contentViewId: Int
    abstract val navigationMenuItemId: Int
}