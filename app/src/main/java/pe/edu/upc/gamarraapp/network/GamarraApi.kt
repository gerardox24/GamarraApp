package pe.edu.upc.gamarraapp.network

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import org.json.JSONArray
import pe.edu.upc.gamarraapp.GamarraApp
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.User
import com.androidnetworking.interfaces.JSONArrayRequestListener
import pe.edu.upc.gamarraapp.models.Clothe
import pe.edu.upc.gamarraapp.network.responses.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface GamarraApi {

    @GET("users/")
    fun getAllUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("users/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<UserResponse>

    @POST("users/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String
    ): Call<UserResponse>

    @GET("cloth/")
    fun getAllClothes() : Call<List<Clothe>>

    @GET("clothe/{id}")
    fun getClotheById(@Path("id") id: Int): Call<Clothe>
}