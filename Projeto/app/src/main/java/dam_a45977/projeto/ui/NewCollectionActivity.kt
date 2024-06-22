package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityNewCollectionBinding
import dam_a45977.projeto.ui.viewModel.NewCollectionViewModel

class NewCollectionActivity : AppCompatActivity() {
    private lateinit var viewModel: NewCollectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNewCollectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_collection)
        viewModel = NewCollectionViewModel()
        binding.viewModel = viewModel

        setContentView(binding.root)
        viewModel.showMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.toCollectionsPage.observe(this, Observer { navigate ->
            if (navigate) {
                goToCollectionsPage()
            }
        })
    }

    private fun goToCollectionsPage() {
        val intent = Intent(this, CollectionActivity::class.java)
        startActivity(intent)
        finish()
    }
}