package pe.edu.upc.gamarraapp.controllers.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.item_clothe.*
import kotlinx.android.synthetic.main.item_clothe.view.*

import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.adapters.ClothesAdapter
import pe.edu.upc.gamarraapp.controllers.activities.ItemDetailActivity
import pe.edu.upc.gamarraapp.controllers.activities.MainActivity
import pe.edu.upc.gamarraapp.models.Clothes
import pe.edu.upc.gamarraapp.network.GamarraApi
import pe.edu.upc.gamarraapp.network.RetrofitClient
import pe.edu.upc.gamarraapp.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CategoriesFragment : Fragment() {

    lateinit var clothesAdapter: ClothesAdapter
    lateinit var service: GamarraApi

    var clothes: List<Clothes> = ArrayList()
    val TAG_LOGS = "clothes"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clothesAdapter = ClothesAdapter(clothes)
        clothesRecyclerView.apply{
            adapter = clothesAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        /*service = RetrofitClient.create()
        service.getAllClothes().enqueue(object: Callback<List<Clothes>>{
            override fun onResponse(call: Call<List<Clothes>>?, response: Response<List<Clothes>>?) {
                var clothesBody = response?.body()
                Log.i(TAG_LOGS, Gson().toJson(clothesBody))
                clothesAdapter.clothes = clothesBody.orEmpty()
                clothesAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Clothes>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })*/

        loadClothes()
    }

    private fun loadClothes() {
        val clothesService = ServiceBuilder.buildService(GamarraApi::class.java)
        val requestCall = clothesService.getAllClothes()


        requestCall.enqueue(object : Callback<List<Clothes>> {
            override fun onResponse(call: Call<List<Clothes>>, response: Response<List<Clothes>>) {
                if(response.isSuccessful) {
                    var clothesList = response.body()!!

                    clothesRecyclerView.adapter = ClothesAdapter(clothesList)
                }else{
                    Toast.makeText(activity,"Failed to retrieve clothes", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Clothes>>, t: Throwable) {
                Toast.makeText(activity,"Failed to retrieve clothes", Toast.LENGTH_LONG).show()
            }
        })
    }

    /*val mLayoutManager = GridLayoutManager(activity, 2)
    clothesRecyclerView.setLayoutManager(mLayoutManager)*/

    /*GamarraConnection.apply {
        requestClothes({
            it?.apply {
                clothesAdapter.clothes = clothes
                clothesAdapter.notifyDataSetChanged()
            }
        },{
            clothesAdapter.clothes = clothes
            clothesAdapter.notifyDataSetChanged()
        })
    }*/
}
