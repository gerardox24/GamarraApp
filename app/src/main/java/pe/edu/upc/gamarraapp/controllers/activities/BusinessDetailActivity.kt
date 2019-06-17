package pe.edu.upc.gamarraapp.controllers.activities

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_business_detail.*
import kotlinx.android.synthetic.main.content_business_detail.*
import pe.edu.upc.gamarraapp.models.Business
import pe.edu.upc.gamarraapp.network.GamarraApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessDetailActivity : AppCompatActivity() {

    lateinit var service: GamarraApi
    final val TAG = "BUSINESS_DETAIL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.extras?.apply {
            val id = getInt("id")
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://quiet-temple-50701.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create<GamarraApi>(GamarraApi::class.java)

            service.getBusinessById(id).enqueue(object: Callback<Business>{
                override fun onFailure(call: Call<Business>, t: Throwable) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<Business>, response: Response<Business>) {
                    val business = response.body()
                    Log.d(TAG, Gson().toJson(business))
                    business?.apply {
                        businessDetailNameTextView.text = name
                    }
                }
            })
        }
    }
}
