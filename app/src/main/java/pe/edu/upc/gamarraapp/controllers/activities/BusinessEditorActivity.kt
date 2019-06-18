package pe.edu.upc.gamarraapp.controllers.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson
import pe.edu.upc.gamarraapp.R

import kotlinx.android.synthetic.main.activity_business_editor.*
import kotlinx.android.synthetic.main.content_business_editor.*
import okhttp3.ResponseBody
import pe.edu.upc.gamarraapp.models.Business
import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.network.GamarraApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessEditorActivity : AppCompatActivity() {

    lateinit var service: GamarraApi
    final var TAG = "business-editor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_editor)
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

            service.getBusinessById(id).enqueue(object: Callback<Business> {
                override fun onFailure(call: Call<Business>, t: Throwable) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<Business>, response: Response<Business>) {
                    val business = response.body()
                    Log.d(TAG, Gson().toJson(business))
                    business?.apply {
                        supportActionBar?.title = name
                        editBusinessNameEditText.setText(name)
                        editBusinessURLLogoEditText.setText(urllogo)
                        editBusinessNameEditText.clearFocus()
                        val pos = editBusinessNameEditText.text.length
                        editBusinessNameEditText.setSelection(pos)
                    }
                }
            })

            editBusinessUpdateButton.setOnClickListener {

                val businessName = editBusinessNameEditText.text.toString()
                val businessURLLogo = editBusinessURLLogoEditText.text.toString()

                var business = Business()
                business.id = id
                business.name = businessName
                business.urllogo = businessURLLogo
                business.userId = User()
                business.userId?.id = userIdShared

                service.updateBusiness(business, "Bearer ${accessToken}").enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t?.printStackTrace()
                        val t = Toast.makeText(it.context, "Ocurrió un problema, no se pudo actualizar", Toast.LENGTH_SHORT)
                        t.show()
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val responseCode = response.code()
                        Log.d(TAG, "response's code: ${responseCode}")
                        val t = Toast.makeText(it.context, "Se actualizó", Toast.LENGTH_SHORT)
                        t.show()
                        // TODO se debe verificar si el código de la respuesta es satisfactorio
                        finish()
                    }
                })
            }
        }
    }

}
