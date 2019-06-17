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
import okhttp3.ResponseBody
import pe.edu.upc.gamarraapp.models.Business
import pe.edu.upc.gamarraapp.models.Clothe
import pe.edu.upc.gamarraapp.models.Shop
import pe.edu.upc.gamarraapp.network.requests.SignInRequest
import pe.edu.upc.gamarraapp.network.requests.SignUpRequest
import pe.edu.upc.gamarraapp.network.responses.JwtResponse
import pe.edu.upc.gamarraapp.network.responses.SignUpResponse
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

    @GET("clothes/{id}")
    fun getClothesById(@Path("id") id: Int): Call<Clothe>

    @GET("clothes")
    fun getAllClothesFilterByName(@Query("name") name: String?) : Call<List<Clothe>>

    @GET("clothes/{id}/shops")
    fun getShopsByClothId(@Path("id") id: Int) : Call<List<Shop>>

    @POST("api/auth/signin")
    fun signIn(@Body signInRequest: SignInRequest): Call<JwtResponse>

    @POST("api/auth/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("users/{userId}/markers")
    fun saveMaker(@Path("userId") userId: Int, @Body cloth: Clothe, @Header("Authorization") auth: String): Call<Object>

    @GET("users/{userId}/markers")
    fun getAllMarkers(@Path("userId") userId: Int, @Header("Authorization") auth: String) : Call<List<Clothe>>

    @GET("users/{userId}/businesses")
    fun getAllBusinessByUserId(@Path("userId") userId: Int, @Header("Authorization") auth: String): Call<List<Business>>

    /* Se puede usar ResponseBody para endpoints que no retornan JSON */
    @POST("businesses")
    fun saveBusiness(@Body business: Business, @Header("Authorization") auth: String): Call<ResponseBody>

    @GET("businesses/{id}")
    fun getBusinessById(@Path("id") id: Int): Call<Business>
}