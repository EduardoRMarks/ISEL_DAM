package dam_a45977.projeto.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.initialize
import dam_a45977.projeto.R
import dam_a45977.projeto.databinding.ActivityLoginBinding
import dam_a45977.projeto.ui.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = LoginViewModel()
        binding.viewModel = viewModel

        setContentView(binding.root)

        setupPasswordVisibilityToggle(binding)
        setupRegisterButton(binding)

        viewModel.showMessage.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.homeScreen.observe(this, Observer { go ->
            if (go){
                goToHomePage(viewModel.userData.value)
            }
        })
    }

    private fun setupPasswordVisibilityToggle(binding: ActivityLoginBinding) {
        binding.showHidePassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                binding.loginPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide password
                binding.loginPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.loginPassword.setSelection(binding.loginPassword.text.length)
        }
    }

    private fun setupRegisterButton(binding: ActivityLoginBinding) {
        binding.newAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToHomePage(userData: Map<String, Any>?) {
        val intent = Intent(this, HomePageActivity::class.java).apply {
            putExtra("userData", userData?.let { HashMap(it) })
        }
        startActivity(intent)
        finish()
    }
}