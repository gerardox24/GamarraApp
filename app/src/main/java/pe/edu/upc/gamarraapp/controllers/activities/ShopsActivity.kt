package pe.edu.upc.gamarraapp.controllers.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_shops.*

class ShopsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shops)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent?.extras?.apply {
            val businessName = getString("businessName")
            supportActionBar?.title = "Tiendas de ${businessName}"
        }
    }

}
