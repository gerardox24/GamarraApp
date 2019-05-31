package pe.edu.upc.gamarraapp.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import pe.edu.upc.gamarraapp.network.responses.ClothesResponse

class GamarraConnection {
    companion object {
        private const val BASE_URL = "https://gamarra-app.herokuapp.com/api"
        val clothesUrl = "$BASE_URL/clothe"
        const val TAG = "GAMARRAAPP"

        fun requestClothes(responseHandler: (ClothesResponse) -> Unit, errorHandler: (ANError) -> Unit) {
            request(clothesUrl, mapOf("" to ""),responseHandler,errorHandler)
        }

        private inline fun <reified T> request(url: String, parameter: Map<String, String>?, crossinline responseHandler: (T) -> Unit, crossinline errorHandler: (ANError) -> Unit) {
            AndroidNetworking.get(url)
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(T::class.java,
                    object : ParsedRequestListener<T> {
                        override fun onResponse(response: T) {
                            response.apply {
                                responseHandler(this)
                                Log.d(TAG,response.toString())
                            }
                        }

                        override fun onError(anError: ANError?) {
                            anError?.apply {
                                errorHandler(this)
                            }
                        }
                    })
        }
    }
}