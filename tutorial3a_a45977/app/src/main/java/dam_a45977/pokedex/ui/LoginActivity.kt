package dam_a45977.pokedex.ui

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import dam_a45977.pokedex.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val passwordSwitch = findViewById<Switch>(R.id.showHidePassword)
        val passwordEditText = findViewById<EditText>(R.id.registerPassword)

        passwordSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show password
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide password
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }


}