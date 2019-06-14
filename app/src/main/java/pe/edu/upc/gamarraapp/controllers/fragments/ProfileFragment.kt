package pe.edu.upc.gamarraapp.controllers.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pe.edu.upc.gamarraapp.R
import android.app.ProgressDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer
import com.google.gson.Gson
import kotlinx.android.synthetic.main.user_login.*
import kotlinx.android.synthetic.main.user_login.view.*
import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.network.GamarraApi
import pe.edu.upc.gamarraapp.network.ServiceBuilder
import pe.edu.upc.gamarraapp.network.responses.UserResponse
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

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://gamarra-app.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        getAllUsers()
        getUserById()

        //showRegisterLogin()
        //login()

        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRegisterLogin()

        btn_register.setOnClickListener {
            register()
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


    fun register() {
        val newUser = User()
        newUser.username = register_username.text.toString()
        newUser.password = register_password.text.toString()
        newUser.email = register_email.text.toString()
        newUser.name = register_fullname.text.toString()
        newUser.role = arrayOf("USER")

        val password = register_password.text.toString()
        val confirm_password = register_confirm_password.text.toString()

        if(password.equals(confirm_password)){
            val clothesService = ServiceBuilder.buildService(GamarraApi::class.java)
            val requestCall = clothesService.register(newUser)

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
