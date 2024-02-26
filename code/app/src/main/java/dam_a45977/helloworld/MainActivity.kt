package dam_a45977.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var btn_sh_info: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sh_info = findViewById(R.id.button_info)

        btn_sh_info?.setOnClickListener {
            val dialog = CustomDialog()
            dialog.show(supportFragmentManager, "CustomDialog")
        }
    }

    class CustomDialog {
        fun show(supportFragmentManager: FragmentManager, s: String) {
            TODO("Not yet implemented")
        }

    }
}