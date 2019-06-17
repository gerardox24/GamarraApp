package pe.edu.upc.gamarraapp.network

import pe.edu.upc.gamarraapp.models.User
import pe.edu.upc.gamarraapp.models.Clothes
import pe.edu.upc.gamarraapp.models.Shop
import pe.edu.upc.gamarraapp.network.requests.SignInRequest
import pe.edu.upc.gamarraapp.network.responses.JwtResponse
import pe.edu.upc.gamarraapp.network.responses.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface GamarraApi {

    @GET("clothes/")
    fun getAllClothes() : Call<List<Clothes>>

    @GET("clothes/{id}")
    fun getClothesById(@Path("id") id: Int): Call<Clothes>

    @GET("clothes/{id}/shops")
    fun getShopsByClothesId(@Path("id") id: Int): Call<List<Shop>>

    @GET("clothes")
    fun getAllClothesFilterByName(@Query("name") name: String?) : Call<List<Clothes>>

    @GET("users/")
    fun getAllUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @POST("users/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<UserResponse>

    @POST("api/auth/signup")
    fun register( @Body newUser: User ): Call<UserResponse>

    @POST("api/auth/signin")
    fun signIn(@Body signInRequest: SignInRequest): Call<JwtResponse>

    @POST("users/{userId}/markers")
    fun saveMaker(@Path("userId") userId: Int, @Body cloth: Clothes, @Header("Authorization") auth: String): Call<Object>

    @GET("users/{userId}/markers")
    fun getAllMarkers(@Path("userId") userId: Int, @Header("Authorization") auth: String) : Call<List<Clothes>>

}