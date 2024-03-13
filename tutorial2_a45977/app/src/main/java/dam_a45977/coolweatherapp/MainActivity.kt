package dam_a45977.coolweatherapp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val day = true
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

        setDayNightTheme(day)
        setStatusBarColor(day)
    }

    private fun setStatusBarColor(day: Boolean) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (day){
            window.statusBarColor = ContextCompat.getColor(this, R.color.button_light)
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.button_dark)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setDayNightTheme(day: Boolean) {
        val buttonUpdate: Button = findViewById(R.id.update_location)
        val locationIcon : ImageView = findViewById(R.id.location_icon)
        val layout: ConstraintLayout = findViewById(R.id.weatherConstraintLayout)
        val windIcon: ImageView = findViewById(R.id.wind_icon)
        val humidityIcon: ImageView = findViewById(R.id.humidity_icon)

        val titles = intArrayOf(R.id.latitude_title, R.id.longitude_title, R.id.thermal_sensation_title,
            R.id.wind_title, R.id.km_h1, R.id.km_h2, R.id.gusts, R.id.wind, R.id.humidity_title, R.id.pressure_title)
        val values = intArrayOf(R.id.latitude, R.id.longitude, R.id.thermal_sensation, R.id.min_max, R.id.temp,
            R.id.location, R.id.wind_value, R.id.wind_gust_value, R.id.humidity_value, R.id.pressure_value, R.id.time_value)


        if(day) {
            layout.setBackgroundColor(resources.getColor(R.color.background_light))
            buttonUpdate.setBackgroundColor(resources.getColor(R.color.button_light))
            buttonUpdate.setTextColor(resources.getColor(R.color.black))
            locationIcon.setImageResource(R.drawable.location_black)
            windIcon.setImageResource(R.drawable.wind_black)
            humidityIcon.setImageResource(R.drawable.humidity_black)

            for (title in titles) {
                findViewById<TextView>(title).setTextColor(resources.getColor(R.color.black))
            }
            for (value in values) {
                findViewById<TextView>(value).setTextColor(resources.getColor(R.color.black))
            }
        } else {
            layout.setBackgroundColor(resources.getColor(R.color.background_dark))
            buttonUpdate.setBackgroundColor(resources.getColor(R.color.button_dark))
            buttonUpdate.setTextColor(resources.getColor(R.color.white))
            locationIcon.setImageResource(R.drawable.location_white)
            windIcon.setImageResource(R.drawable.wind_white)
            humidityIcon.setImageResource(R.drawable.humidity_white)

            for (title in titles) {
                findViewById<TextView>(title).setTextColor(resources.getColor(R.color.white))
            }
            for (value in values) {
                findViewById<TextView>(value).setTextColor(resources.getColor(R.color.white))
            }
        }
    }
}