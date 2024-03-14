package dam_a45977.coolweatherapp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import java.io.Console
import java.io.InputStreamReader
import java.lang.Math.round
import java.net.URL
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var latitude:Double = 39.74
    private var longitude:Double = -9.15

    override fun onCreate(savedInstanceState: Bundle?) {

        fetchWeatherData(latitude.toFloat(), longitude.toFloat()).start()

        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        val day = currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_NO
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

        val latitudetext: TextView = findViewById(R.id.latitude)
        val longitudetext: TextView = findViewById(R.id.longitude)
        latitudetext.text = latitude.toString()
        longitudetext.text = longitude.toString()

        setDayNightTheme(day)
        setStatusBarColor(day)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
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

    private fun WeatherAPI_Call(lat: Float, long: Float) : WeatherData {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            //append("current_weather=true&")
            append("current=temperature_2m,relative_humidity_2m,apparent_temperature," +
                    "is_day,precipitation,weather_code,pressure_msl,wind_speed_10m," +
                    "wind_direction_10m,wind_gusts_10m&daily=temperature_2m_max," +
                    "temperature_2m_min,uv_index_max&timezone=auto")
    }
        val url = URL(reqString)
        url.openStream().use {
            return Gson().fromJson(InputStreamReader(it,"UTF-8"),WeatherData::class.java)
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float) : Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat,long)
            updateUI(weather)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(request: WeatherData) {
        runOnUiThread() {
            val mapt = getWeatherCodeMap()

            val time: TextView = findViewById(R.id.time_value)
            val min_max: TextView = findViewById(R.id.min_max)
            val temperature: TextView = findViewById(R.id.temp)
            val thermalSensation: TextView = findViewById(R.id.thermal_sensation)
            val wind: TextView = findViewById(R.id.wind_value)
            val wind_gust: TextView = findViewById(R.id.wind_gust_value)
            val humidity: TextView = findViewById(R.id.humidity_value)
            val pressure: TextView = findViewById(R.id.pressure_value)

            time.text = request.current.time.substring(11)
            min_max.text = "(" + round(request.daily.temperature_2m_min[0]).toString() + "º/" + round(request.daily.temperature_2m_max[0]).toString() + "º)"
            temperature.text = round(request.current.temperature_2m).toString() + "º"
            thermalSensation.text = round(request.current.apparent_temperature).toString() + "º"
            wind.text = request.current.wind_speed_10m.toString()
            wind_gust.text = request.current.wind_gusts_10m.toString()
            humidity.text = request.current.relative_humidity_2m.toString() + "%"
            pressure.text = request.current.pressure_msl.toString() + "hPa"
            Log.d("aaaaaaaaaaaaaa", request.current.weathercode.toString())
        }
    }
    }