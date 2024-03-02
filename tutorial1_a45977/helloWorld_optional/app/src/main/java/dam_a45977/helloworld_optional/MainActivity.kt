package dam_a45977.helloworld_optional

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.bold
import dam_a45977.helloworld_optional.R.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val deviceInfo: TextView = findViewById(id.model_info)

        val deviceInfoAux = "Manufacturer: ${Build.MANUFACTURER}\n" +
                "Model: ${Build.MODEL}\n" +
                "Brand: ${Build.BRAND}\n" +
                "Type: ${Build.TYPE}\n" +
                "User: ${Build.USER}\n" +
                "Base: ${Build.VERSION_CODES.BASE}\n" +
                "Incremental: ${Build.VERSION.INCREMENTAL}\n" +
                "SDK: ${Build.VERSION.SDK}\n" +
                "Version Code: ${Build.VERSION.CODENAME}\n" +
                "Display: ${Build.DISPLAY}\n"

        deviceInfo.text = deviceInfoAux
    }
}