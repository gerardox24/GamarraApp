package pe.edu.upc.gamarraapp.controllers.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pe.edu.upc.gamarraapp.R
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.user_login.*
import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.network.GamarraApi
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
class ProfileFragment : Fragment() {

    val TAG = "Login"

    lateinit var service: GamarraApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //getAllUsers()
        //getUserById()
        //login()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        btn_login.setOnClickListener {
            var signInRequest = SignInRequest(input_username.text.toString(), input_password.text.toString())

            service.signIn(signInRequest).enqueue(object: Callback<JwtResponse> {
                override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<JwtResponse>, response: Response<JwtResponse>) {
                    val jwtResponseBody = response?.body()
                    Log.i(TAG,Gson().toJson(jwtResponseBody))
                    
                }
            })
        }
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
