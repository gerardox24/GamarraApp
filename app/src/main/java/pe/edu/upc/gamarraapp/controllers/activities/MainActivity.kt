package pe.edu.upc.gamarraapp.controllers.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.controllers.fragments.BagFragment
import pe.edu.upc.gamarraapp.controllers.fragments.CategoriesFragment
import pe.edu.upc.gamarraapp.controllers.fragments.ProfileFragment
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateTo(navView.menu.findItem(R.id.navigation_categories))

        val myToolbar: Toolbar  = findViewById(R.id.my_toolbar)
        setSupportActionBar(myToolbar)
    }

    private fun getFragmentFor(item: MenuItem): Fragment {
        return when(item.itemId) {
            R.id.navigation_categories -> CategoriesFragment()
            R.id.navigation_bag -> BagFragment()
            R.id.navigation_profile -> ProfileFragment()
            else -> CategoriesFragment()
        }
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.isChecked = true

        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, getFragmentFor(item))
            .commit() > 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }


        val searchView: SearchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.setIconifiedByDefault(false)
        /*searchView.setOnSearchClickListener {
            Log.d("search","Buscar: " + searchView.query)
        }

        searchView.setOnQueryTextFocusChangeListener { view: View, b: Boolean ->
            Log.d("search","Buscar: " + searchView.query.toString())
        }*/

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("search","Buscar: " + searchView.query.toString())

                val intent = Intent(searchView.getContext(), SearchClothesActivity::class.java)
                intent.putExtra("cloth",  query)
                searchView.getContext().startActivity(intent)

                return false
            }

        })

        return true
    }
}
