package pe.edu.upc.gamarraapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_clothe.view.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.controllers.activities.ClothActivity
import pe.edu.upc.gamarraapp.models.Clothe
import pe.edu.upc.gamarraapp.network.GamarraApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClotheAdapter(var clothes: List<Clothe>) : RecyclerView.Adapter<ClotheAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pictureImageView = itemView.pictureImageView
        val titleTextView = itemView.titleTextView
        val moreButton = itemView.moreButton
        val clothCard = itemView.clothItem
        // TODO Revisar la forma adecuada de inicializar la variable
        var clothId: Int = 5
        lateinit var service: GamarraApi

        fun bindTo(clothe: Clothe) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://quiet-temple-50701.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create<GamarraApi>(GamarraApi::class.java)

            clothId = clothe.id
            pictureImageView.apply {
                setDefaultImageResId(R.mipmap.ic_launcher)
                setErrorImageResId(R.mipmap.ic_launcher)
                setImageUrl(clothe.urlphoto)
            }
            titleTextView.text = clothe.name
            moreButton.setOnClickListener{
                // Empieza una nueva actividad para mostra el detalle de la ropa
                val intent = Intent(it.context, ClothActivity::class.java)
                intent.putExtra("id", clothId)
                Log.d("cloth", clothId.toString())
                it.context.startActivity(intent)
            }

            clothCard.setOnLongClickListener {
                val text = "Se agregó a la bolsa!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(it.context, text, duration)
                toast.show()
                val sharedPref = itemView.context.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE)
                val accessToken = sharedPref.getString("accessToken", "")
                val userId = sharedPref.getInt("id", 0)
                Log.d("BAG", accessToken)
                service.saveMaker(userId, clothe, "Bearer ${accessToken}").enqueue(object: Callback<Object> {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClotheAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clothe, parent, false))
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

    override fun onBindViewHolder(holder: ClotheAdapter.ViewHolder, position: Int) {
        holder.bindTo(clothes[position])
    }
}