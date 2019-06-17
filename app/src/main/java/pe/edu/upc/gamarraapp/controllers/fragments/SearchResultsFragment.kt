    package pe.edu.upc.gamarraapp.controllers.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_results.*

import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.adapters.ClothesAdapter
import pe.edu.upc.gamarraapp.models.Clothes
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
class SearchResultsFragment(var name: String?) : Fragment() {

    lateinit var clotheAdapter: ClothesAdapter
    lateinit var service: GamarraApi

    var clothes: List<Clothes> = ArrayList()
    val TAG_LOGS = "clothes"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clotheAdapter = ClothesAdapter(clothes)
        searchResultsRecyclerView.apply{
            adapter = clotheAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)

        service.getAllClothesFilterByName(name).enqueue(object: Callback<List<Clothes>> {
            override fun onResponse(call: Call<List<Clothes>>?, response: Response<List<Clothes>>?) {
                var clothesBody = response?.body()
                Log.i(TAG_LOGS, Gson().toJson(clothesBody))
                clotheAdapter.clothes = clothesBody.orEmpty()
                clotheAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Clothes>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}
