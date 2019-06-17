package pe.edu.upc.gamarraapp.controllers.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_businesses.*
import pe.edu.upc.gamarraapp.controllers.fragments.BusinessesFragment

class BusinessesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_businesses)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            val intent = Intent(view.context, BusinessRegisterActivity::class.java)
            view.context.startActivity(intent)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Negocios"

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.businessContent, BusinessesFragment())
            .commit() > 0
    }

}
