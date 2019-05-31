package pe.edu.upc.gamarraapp.controllers.activities

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import pe.edu.upc.gamarraapp.R
    import pe.edu.upc.gamarraapp.controllers.fragments.SearchResultsFragment

class SearchClothesActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search_clothes)

            intent?.extras?.apply {
                val cloth: String = getSerializable("cloth") as String
                Log.d("SearchClothesActivity", "search: " + cloth)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contentSearchClothes, SearchResultsFragment(cloth))
                    .commit()
            }
        }
}
