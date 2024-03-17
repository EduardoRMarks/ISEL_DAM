package dam_a45977.coolweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Math.round
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude:Double = 1.0
    private var longitude:Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState != null) {
            latitude = savedInstanceState.getDouble("latitude", 0.0)
            longitude = savedInstanceState.getDouble("longitude", 0.0)
        }
        else {
            getLocationAndPermission()
        }

        if (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) {
            fetchWeatherData(latitude.toFloat(), longitude.toFloat())
        }
        setContentView(R.layout.activity_main)

        val buttonUpdate: Button = findViewById(R.id.update_location)
        val weather_icon: ImageView = findViewById(R.id.weather_icon)

        buttonUpdate.setOnClickListener {
            latitude = findViewById<EditText>(R.id.latitude).text.toString().toDouble()
            longitude = findViewById<EditText>(R.id.longitude).text.toString().toDouble()
            if (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) {
                fetchWeatherData(latitude.toFloat(), longitude.toFloat())
            }
        }

        weather_icon.setOnClickListener {
            getLocationAndPermission()
        }

        setDayNightTheme(day)
        setStatusBarColor(day)
        //setupUpdateButton()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun getLocationAndPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the state of the activity
        outState.putDouble("longitude", longitude)
        outState.putDouble("latitude", latitude)
        super.onSaveInstanceState(outState)
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
        val layout: ConstraintLayout = findViewById(R.id.layout)
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

    private fun fetchWeatherData(lat: Float, long: Float) {
        return Thread {
            val weather = WeatherAPI_Call(lat,long)
            updateUI(weather)
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(request: WeatherData) {
        runOnUiThread() {

            val time: TextView = findViewById(R.id.time_value)
            val weatherIcon: ImageView = findViewById(R.id.weather_icon)
            val minMax: TextView = findViewById(R.id.min_max)
            val temperature: TextView = findViewById(R.id.temp)
            val thermalSensation: TextView = findViewById(R.id.thermal_sensation)
            val wind: TextView = findViewById(R.id.wind_value)
            val windGust: TextView = findViewById(R.id.wind_gust_value)
            val humidity: TextView = findViewById(R.id.humidity_value)
            val pressure: TextView = findViewById(R.id.pressure_value)
            val latText: TextView = findViewById(R.id.latitude)
            val longText: TextView = findViewById(R.id.longitude)
            val location: TextView = findViewById(R.id.location)
            val windDir: ImageView = findViewById(R.id.wind_dir)

            val day = request.current.is_day == 1

            weatherIcon.setImageResource(resources.getIdentifier(getImage(request.current.weather_code, day), "drawable", packageName))
            windDir.setImageResource(resources.getIdentifier(degreesToDirection(request.current.wind_direction_10m.toFloat(), day), "drawable", packageName))
            latText.text = latitude.toString()
            longText.text = longitude.toString()
            time.text = request.current.time.substring(11)
            minMax.text = "(" + round(request.daily.temperature_2m_min[0]).toString() + "º/" + round(request.daily.temperature_2m_max[0]).toString() + "º)"
            temperature.text = round(request.current.temperature_2m).toString() + "º"
            thermalSensation.text = round(request.current.apparent_temperature).toString() + "º"
            wind.text = round(request.current.wind_speed_10m).toString()
            windGust.text = round(request.current.wind_gusts_10m).toString()
            humidity.text = request.current.relative_humidity_2m.toString() + "%"
            pressure.text = request.current.pressure_msl.toString() + "hPa"
            location.text = getLocationName(request.latitude.toDouble(), request.longitude.toDouble())

            setDayNightTheme(day)
            setStatusBarColor(day)
            setBackgroundImage(day)
        }
    }

    private fun getImage(code: Int, day: Boolean): String {
        val mapt = getWeatherCodeMap()
        val mapt_img = mapt[code]?.image
        if (mapt_img != null) {
            return if(mapt_img.endsWith("_")){
                if (day) mapt_img + "day"
                else mapt_img + "night"
            } else {
                mapt_img
            }
        }
        else {
            return if (day) "weather_unknown_day"
            else "weather_unknown_night"
        }
    }

    private fun setBackgroundImage(day: Boolean) {
        val layout = findViewById<ConstraintLayout>(R.id.container)
        val layoutResourceId = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (day) R.drawable.daytime_horizontal else R.drawable.nighttime_horizontal
        } else {
            if (day) R.drawable.daytime_vertical else R.drawable.nighttime_vertical
        }
        layout.setBackgroundResource(layoutResourceId)
    }

    fun degreesToDirection(degrees: Float, day: Boolean): String {
        val directions = arrayOf("s", "sw", "w", "nw", "n", "ne", "e", "se", "s")
        val index = ((((degrees % 360 + 360)) % 360 + 22.5) / 45).toInt()
        return if (day) directions[index] + "_day"
        else directions[index] + "_night"
    }


    private fun getLocationName(lat: Double, long: Double): String{
        if (lat > -90 && lat < 90 && long > -180 && long < 180) {
            val gcd = Geocoder(this, Locale.getDefault())
            var addresses: List<Address>? = null

            try {
                addresses = gcd.getFromLocation(lat, long, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (addresses != null && addresses.isNotEmpty() && addresses[0].locality != null) {
                return addresses[0].locality
            }
        }
        return "Unknown"
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (Build.MODEL.startsWith("sdk")
            || "google_sdk".equals(Build.MODEL.toString())
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK")) {
            fetchWeatherData(latitude.toFloat(), longitude.toFloat())
            return
        }

        //fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
        //val task: Task<Location> = fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                longitude = location.longitude
                latitude = location.latitude
                fetchWeatherData(latitude.toFloat(), longitude.toFloat())
            }
        }.addOnFailureListener() {
            longitude = 3.0
            latitude = 4.0
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}