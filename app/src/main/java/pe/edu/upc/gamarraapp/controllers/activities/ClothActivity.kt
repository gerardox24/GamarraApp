package pe.edu.upc.gamarraapp.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_cloth.*
import pe.edu.upc.gamarraapp.controllers.fragments.ShopsResultsFragment
import pe.edu.upc.gamarraapp.models.Clothe
import pe.edu.upc.gamarraapp.network.GamarraApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClothActivity : AppCompatActivity() {

    lateinit var service: GamarraApi
    // TODO revisar la forma adecuada de inicializar la variable
    var cloth: Clothe? = Clothe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth)

        intent?.extras?.apply {
            val clothId = getInt("id")
            //clothNameTextView.text = clothId.toString()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://quiet-temple-50701.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create<GamarraApi>(GamarraApi::class.java)

            service.getClothesById(clothId).enqueue(object: Callback<Clothe>{
                override fun onResponse(call: Call<Clothe>, response: Response<Clothe>) {
                    cloth = response?.body()
                    cloth?.apply{
                        clothNameTextView.text = name
                    }
                }

                override fun onFailure(call: Call<Clothe>, t: Throwable) {

                }
            })

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.shopsContent, ShopsResultsFragment())
                .commit() > 0
        }
    }
}
