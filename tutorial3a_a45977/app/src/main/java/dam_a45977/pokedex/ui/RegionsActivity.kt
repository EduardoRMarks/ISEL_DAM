package dam_a45977.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.databinding.ActivityRegionsBinding
import dam_a45977.pokedex.ui.adapters.RegionAdapter
import dam_a45977.pokedex.ui.viewModel.RegionsViewModel



class RegionsActivity : BottomNavActivity() {
    val viewModel: RegionsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val regionBinding = binding as ActivityRegionsBinding
        var listView = regionBinding.regionsRecyclerView

        viewModel.regions.observe(this) {
            listView.adapter =
                viewModel.regions.value?.let { it1 -> RegionAdapter(pkRegionList = it1, context = this) }
        }

        viewModel.fetchRegions()
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions

}