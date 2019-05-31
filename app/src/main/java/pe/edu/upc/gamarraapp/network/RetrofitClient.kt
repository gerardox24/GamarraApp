package pe.edu.upc.gamarraapp.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*object RetrofitClient{
    private var retrofit:Retrofit?=null
    fun getClient(baseUrl: String) :Retrofit{
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}*/

class RetrofitClient {
    companion object {
        fun create(): GamarraApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://quiet-temple-50701.herokuapp.com/")
                .build()
            return retrofit.create(GamarraApi::class.java!!)
        }
    }
}