package pe.edu.upc.gamarraapp.controllers.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson

import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_business_register.*
import kotlinx.android.synthetic.main.content_business_register.*
import okhttp3.ResponseBody
import pe.edu.upc.gamarraapp.models.Business
import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.network.GamarraApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessRegisterActivity : AppCompatActivity() {

    lateinit var service: GamarraApi
    private val CREATED_STATUS_CODE = 201
    private val TAG = "BUSINESS_REGISTER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_register)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nuevo negocio"

        /* Datos guardados localmente*/
        val sharedPref = applicationContext.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE)
        val accessToken = sharedPref.getString("accessToken", "")
        val userIdShared = sharedPref.getInt("id", 0)
        /* Servicio web API RESTful*/
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        /* Eventos de los elemento del Layout*/

        businessRegisterButton.setOnClickListener {
            val businessName = businessNameEditText.text.toString()
            val businessURLLogo = businessURLLogoEditText.text.toString()

            if (businessName.equals("")) {
                val toast = Toast.makeText(it.context, "Ingrese un nombre", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                if(businessURLLogo.equals("")){
                    val toast = Toast.makeText(it.context, "Ingrese URL del logo", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    var business = Business()
                    business.apply {
                        id = 0
                        name = businessName
                        urllogo = businessURLLogo
                        userId = User()
                        userId?.id = userIdShared
                    }
                    Log.d(TAG, Gson().toJson(business))
                    service.saveBusiness(business, "Bearer ${accessToken}").enqueue(object: Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            t?.printStackTrace()
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            val responseCode = response.code()
                            Log.d(TAG, responseCode.toString())
                            if(responseCode != CREATED_STATUS_CODE) {
                                val toast = Toast.makeText(it.context, "Ocurrió un problema, no se pudo registrar", Toast.LENGTH_SHORT)
                                toast.show()
                                Log.i(TAG, Gson().toJson(response.body()))
                            } else {
                                Log.i(TAG, "Se creó el negocio ${businessName}")
                                Log.i(TAG,"location: ${response.headers()["location"]}, id del recurso creado: ${response.headers()["location"]?.substringAfterLast("/","")}")
                                finish()
                            }
                        }
                    })
                }
            }
        }
    }

}
