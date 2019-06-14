package pe.edu.upc.gamarraapp.controllers.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bag.*

import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.adapters.ClotheAdapter
import pe.edu.upc.gamarraapp.models.Clothe
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
class BagFragment : Fragment() {

    lateinit var clothAdapter: ClotheAdapter
    lateinit var service: GamarraApi

    var clothes: List<Clothe> = ArrayList()
    val TAG_LOGS = "BAG_SECTION"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clothAdapter = ClotheAdapter(clothes)
        bagClothRecyclerView.apply{
            adapter = clothAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://quiet-temple-50701.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<GamarraApi>(GamarraApi::class.java)
        val sharedPref = activity?.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE) ?: return
        val id = sharedPref.getInt("id", 0)
        val accessToken = sharedPref.getString("accessToken", "")
        Log.d("BAG_SECTION", id.toString())
        service.getAllMarkers(id, "Bearer ${accessToken}").enqueue(object: Callback<List<Clothe>> {
            override fun onResponse(call: Call<List<Clothe>>?, response: Response<List<Clothe>>?) {
                var clothesBody = response?.body()
                Log.i(TAG_LOGS, Gson().toJson(clothesBody))
                clothAdapter.clothes = clothesBody.orEmpty()
                clothAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Clothe>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}
