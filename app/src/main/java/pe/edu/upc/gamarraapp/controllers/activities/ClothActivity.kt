package pe.edu.upc.gamarraapp.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_cloth.*

class ClothActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth)

        intent?.extras?.apply {
            val itemId = getInt("id")
            clothNameTextView.text = itemId.toString()
        }
    }
}
