package pe.edu.upc.gamarraapp.controllers.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_business_detail.*
import kotlinx.android.synthetic.main.content_business_detail.*
import okhttp3.ResponseBody
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

        val sharedPref = applicationContext.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE)
        val accessToken = sharedPref.getString("accessToken", "")
        val userIdShared = sharedPref.getInt("id", 0)

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
                        supportActionBar?.title = name
                    }
                }
            })

            businessDeleteButton.setOnClickListener {
                service.deleteBusiness(id, "Bearer ${accessToken}").enqueue(object: Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t?.printStackTrace()
                        val toast = Toast.makeText(it.context, "Ocurrió un problema, no se pudo eliminar", Toast.LENGTH_SHORT)
                        toast.show()
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val toast = Toast.makeText(it.context, "Se eliminó", Toast.LENGTH_SHORT)
                        toast.show()
                        Log.d(TAG, "response's code: ${response.code()}")
                        Log.d(TAG, "response's body: ${response.body()}")
                        finish()
                    }
                })
            }
        }
    }
}
