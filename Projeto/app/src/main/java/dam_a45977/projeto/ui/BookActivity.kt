package dam_a45977.projeto.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityBookBinding
import dam_a45977.projeto.ui.viewModel.BookViewModel

class BookActivity : AppCompatActivity(){

    val viewModel: BookViewModel by viewModels()
    private lateinit var binding : ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_book)
        val bookDataBinding = binding as ActivityBookBinding

        viewModel.book.observe(this) {
            if (it != null) {
                bookDataBinding.book = it[0]
            }
        }

        val isbn = intent.getStringExtra("isbn")

        if (isbn != null) {
            viewModel.fetchBook(isbn)
        }
    }
}