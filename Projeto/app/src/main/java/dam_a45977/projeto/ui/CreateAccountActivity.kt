package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityCreateAccountBinding
import dam_a45977.projeto.ui.viewModel.CreateAccountViewModel

class CreateAccountActivity : AppCompatActivity(){
    private lateinit var viewModel: CreateAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCreateAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account)
        viewModel = CreateAccountViewModel()
        binding.viewModel = viewModel

        setContentView(binding.root)

        setupPasswordVisibilityToggle(binding)

        viewModel.showMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.toLoginPage.observe(this, Observer { navigate ->
            if (navigate) {
                goToLoginPage()
            }
        })
    }

    private fun setupPasswordVisibilityToggle(binding: ActivityCreateAccountBinding) {
        binding.showHidePassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                binding.registerPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide password
                binding.registerPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.registerPassword.setSelection(binding.registerPassword.text.length)
        }
    }

    private fun goToLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}