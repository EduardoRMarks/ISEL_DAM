package dam_a45977.pokedex.ui

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import dam_a45977.pokedex.R
import dam_a45977.pokedex.data.model.mocks.MockData
import dam_a45977.pokedex.ui.region.RegionAdapter

class RegionsActivity : BottomNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var listView = findViewById<RecyclerView>(R.id.regionsRecyclerView)
        listView.adapter = RegionAdapter(pkRegionList = MockData.regions, context = this)
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}