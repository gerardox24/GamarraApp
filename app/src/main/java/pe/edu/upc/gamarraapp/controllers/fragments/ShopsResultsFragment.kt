package pe.edu.upc.gamarraapp.controllers.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_shops_results.*

import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.adapters.ShopAdapter
import pe.edu.upc.gamarraapp.models.Shop
import pe.edu.upc.gamarraapp.network.GamarraApi
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
class ShopsResultsFragment(val clothId : Int) : Fragment() {

    lateinit var shopAdapter: ShopAdapter
    lateinit var service: GamarraApi

    var shops: List<Shop> = ArrayList()
    val TAG_LOGS = "shops"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shops_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopAdapter = ShopAdapter(shops)
        shopsResultsRecyclerView.apply{
            adapter = shopAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        service.getShopsByClothesId(clothId).enqueue(object: Callback<List<Shop>> {
            override fun onResponse(call: Call<List<Shop>>?, response: Response<List<Shop>>?) {
                var shopsBody = response?.body()
                Log.i(TAG_LOGS, Gson().toJson(shopsBody))
                shopAdapter.shops = shopsBody.orEmpty()
                shopAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Shop>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}
