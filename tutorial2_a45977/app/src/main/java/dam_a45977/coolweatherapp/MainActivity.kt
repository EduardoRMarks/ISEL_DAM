package dam_a45977.coolweatherapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val day = false
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                if(day) setTheme(R.style.Theme_Day)
                else setTheme(R.style.Theme_Night)
            Configuration.ORIENTATION_LANDSCAPE ->
                if(day) setTheme(R.style.Theme_Day_Land)
                else setTheme(R.style.Theme_Night_Land)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}