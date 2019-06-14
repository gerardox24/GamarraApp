package pe.edu.upc.gamarraapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

   private const val URL = "https://quiet-temple-50701.herokuapp.com/"

   private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

   private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

   private val builder = Retrofit.Builder().baseUrl(URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttp.build())

   private val retrofit = builder.build()

   fun <T> buildService(serviceType: Class<T>):T {
      return retrofit.create(serviceType)
   }

}