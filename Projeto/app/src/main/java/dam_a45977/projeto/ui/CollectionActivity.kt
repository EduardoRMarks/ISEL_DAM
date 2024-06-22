package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityCollectionBinding
import dam_a45977.projeto.databinding.ActivityLoginBinding
import dam_a45977.projeto.ui.adpaters.CollectionAdapter
import dam_a45977.projeto.ui.viewModel.CollectionViewModel

class CollectionActivity : BottomNavActivity() {
    val viewModel: CollectionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collectionList = binding as ActivityCollectionBinding

        val newCollectionButton: Button = findViewById(R.id.newCollectionButton)
        newCollectionButton.setOnClickListener {
            val intent = Intent(this, NewCollectionActivity::class.java)
            startActivity(intent)
        }

        viewModel.collections.observe(this) {
            if (it != null) {
                collectionList.collectionRecyclerView.adapter =
                    viewModel.collections.value?.let { it1 -> CollectionAdapter(collectionList = it1, context = this) }
            }
        }

        viewModel.fetchCollections()
    }


    override val contentViewId: Int
        get() = R.layout.activity_collection
    override val navigationMenuItemId: Int
        get() = R.id.navigation_collections
}