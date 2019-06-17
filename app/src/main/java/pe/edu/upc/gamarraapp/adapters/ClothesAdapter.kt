package pe.edu.upc.gamarraapp.adapters

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_clothe.view.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.Clothes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import pe.edu.upc.gamarraapp.controllers.activities.ItemDetailActivity
import pe.edu.upc.gamarraapp.controllers.activities.MainActivity
import pe.edu.upc.gamarraapp.network.GamarraApi
import pe.edu.upc.gamarraapp.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClothesAdapter(var clothes: List<Clothes>) : RecyclerView.Adapter<ClothesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pictureImageView = itemView.pictureImageView
        val titleTextView = itemView.titleTextView
        val categoryTextView = itemView.itemCategory
        val button_aux = itemView.btn_aux
        val clothCard = itemView.clothItem


        fun bindTo(clothes: Clothes) {
            pictureImageView.apply {
                setDefaultImageResId(R.mipmap.ic_launcher)
                setErrorImageResId(R.mipmap.ic_launcher)
                setImageUrl(clothes.urlphoto)
            }
            titleTextView.text = clothes.name
            categoryTextView.text = "Categoría: " + clothes.categoryId.name

            button_aux.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, ItemDetailActivity::class.java)
                intent.putExtra(ItemDetailActivity.ARG_ITEM_ID, clothes.id)

                context.startActivity(intent)
            }
            clothCard.setOnLongClickListener {
                val text = "Se agregó a la bolsa!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(it.context, text, duration)
                toast.show()
                val sharedPref =
                    itemView.context.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE)
                val accessToken = sharedPref.getString("accessToken", "")
                val userId = sharedPref.getInt("id", 0)
                Log.d("BAG", accessToken)

                val clothesService = ServiceBuilder.buildService(GamarraApi::class.java)
                val requestCall = clothesService.saveMaker(userId, clothes, "Bearer ${accessToken}")

                requestCall.enqueue(object : Callback<Object> {
                    override fun onFailure(call: Call<Object>, t: Throwable) {
                        t?.printStackTrace();
                    }

                    override fun onResponse(call: Call<Object>, response: Response<Object>) {
                        Log.d("BAG", "Estado de la respuesta: ${response.code()}")
                        var responseBody = response?.body()
                        Log.i("BAG", Gson().toJson(responseBody))
                    }
                })

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothesAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clothe, parent, false))
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

    override fun onBindViewHolder(holder: ClothesAdapter.ViewHolder, position: Int) {
        holder.bindTo(clothes[position])
    }
}
