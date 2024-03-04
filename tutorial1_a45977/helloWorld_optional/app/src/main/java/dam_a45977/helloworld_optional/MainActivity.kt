package dam_a45977.helloworld_optional

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceInfo: TextView = findViewById(R.id.model_info)
        deviceInfo.inputType = InputType.TYPE_NULL
        deviceInfo.isSingleLine = false

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