package pe.edu.upc.gamarraapp.controllers.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pe.edu.upc.gamarraapp.R
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.user_login.*
import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.network.GamarraApi
import pe.edu.upc.gamarraapp.network.ServiceBuilder
import pe.edu.upc.gamarraapp.network.responses.UserResponse
import pe.edu.upc.gamarraapp.network.requests.SignInRequest
import pe.edu.upc.gamarraapp.network.responses.JwtResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment(var supportFragmentManager: FragmentManager) : Fragment() {

    val TAG = "Login"

    lateinit var service: GamarraApi


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            return inflater.inflate(R.layout.user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE) ?: return


        // Revisa si el usuario ya se encuentra autenticado
        val accessTokenSharedPref = sharedPref.getString("accessToken", "")
        val idSharedPref = sharedPref.getInt("id", 0)

        if(idSharedPref == 0) {
            Log.d("Login", "El usuario no se encuentra autenticado")
            // Inflate the layout for this fragment

        } else {
            Log.d("Login", "El usuario se encuentra autenticado")
            Log.d("Login", "El token del usuario es ${accessTokenSharedPref}")
            goToProfileSignIn()
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        showRegisterLogin()

        btn_register.setOnClickListener {
            register()
        }

        btn_login.setOnClickListener {
            var signInRequest = SignInRequest(input_username.text.toString(), input_password.text.toString())

            service.signIn(signInRequest).enqueue(object: Callback<JwtResponse> {
                override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<JwtResponse>, response: Response<JwtResponse>) {
                    val jwtResponseBody: JwtResponse? = response?.body()
                    Log.i(TAG,Gson().toJson(jwtResponseBody))

                    jwtResponseBody?.apply {
                        with (sharedPref.edit()) {
                            putString("accessToken", accessToken)
                            commit()

                            id?.apply {
                                putInt("id",this)
                            }
                            commit()
                        }

                        goToProfileSignIn()
                    }
                }
            })
        }
    }

    fun goToProfileSignIn() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, ProfileSignInFragment(supportFragmentManager))
            .commit() > 0
    }

    fun register() {
        var newUser = User()
        newUser.username = register_username.text.toString()
        newUser.password = register_password.text.toString()
        newUser.email = register_email.text.toString()
        newUser.name = register_fullname.text.toString()
        newUser.role = arrayOf("USER")

        val password = register_password.text.toString()
        val confirm_password = register_confirm_password.text.toString()

        val clothesService = ServiceBuilder.buildService(GamarraApi::class.java)
        val requestCall = clothesService.register(newUser)

        val log = requestCall.request().body()
        Log.d("SUPERTAG", "TAGTAG")
        Log.d("HELP", Gson().toJson(log))

        if(password.equals(confirm_password)){

            requestCall.enqueue(object: Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if(response.isSuccessful) {
                        val result = response.body()!!
                        Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show()
                        register_layout.visibility = View.GONE
                        login_layout.visibility = View.VISIBLE
                    }else{
                        Toast.makeText(activity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(activity, "Failed to create new user 2", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(activity,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
    }

    fun showRegisterLogin() {
        link_register.setOnClickListener {
            login_layout.visibility = View.GONE
            register_layout.visibility = View.VISIBLE
        }
        link_login.setOnClickListener {
            register_layout.visibility = View.GONE
            login_layout.visibility = View.VISIBLE
        }
    }
}
