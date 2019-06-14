package pe.edu.upc.gamarraapp.network

import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.models.Clothes
import pe.edu.upc.gamarraapp.network.responses.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface GamarraApi {

    @GET("clothes/")
    fun getAllClothes() : Call<List<Clothes>>

    @GET("clothes/{id}")
    fun getClotheById(@Path("id") id: Int): Call<Clothes>

    @GET("users/")
    fun getAllUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("users/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<UserResponse>

    @POST("api/auth/signup")
    fun register( @Body newUser: User ): Call<UserResponse>

}