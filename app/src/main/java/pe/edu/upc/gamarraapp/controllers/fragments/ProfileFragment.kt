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
import pe.edu.upc.gamarraapp.network.requests.SignInRequest
import pe.edu.upc.gamarraapp.network.requests.SignUpRequest
import pe.edu.upc.gamarraapp.network.responses.JwtResponse
import pe.edu.upc.gamarraapp.network.responses.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.HashSet


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

        btn_login.setOnClickListener {
            /* TODO Prueba crear una cuenta
            var signUpRequest = SignUpRequest()
            signUpRequest.name = "dasdsas"
            signUpRequest.email = "asdasd@gmail.com"
            signUpRequest.username = "sdasdasds"
            signUpRequest.role = HashSet<String>(Arrays.asList("user"))
            signUpRequest.password = "sdasdasdasd1"

            service.signUp(signUpRequest).enqueue(object : Callback<SignUpResponse> {
                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    val jwtResponseBody: SignUpResponse? = response?.body()
                    Log.i(TAG,Gson().toJson(jwtResponseBody))
                }
            })

             TODO Termina la prueba*/

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

    fun getAllUsers() {
        service.getAllUsers().enqueue(object: Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response?.body()
                Log.i(TAG,Gson().toJson(users))
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t?.printStackTrace()
            }
        })
    }

    fun getUserById() {
        service.getUserById(1).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response?.body()
                Log.i(TAG,Gson().toJson(user))
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t?.printStackTrace()
            }
        })
    }


    fun register_fragment() {

    }


}
