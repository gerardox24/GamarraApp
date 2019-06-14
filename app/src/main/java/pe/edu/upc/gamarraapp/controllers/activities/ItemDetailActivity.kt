package pe.edu.upc.gamarraapp.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.Clothes
import pe.edu.upc.gamarraapp.network.GamarraApi
import pe.edu.upc.gamarraapp.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val bundle: Bundle? = intent.extras

        if(bundle?.containsKey(ARG_ITEM_ID)!!) {
            val id = intent.getIntExtra(ARG_ITEM_ID,0)

            loadDetails(id)
        }
    }

    private fun loadDetails(id: Int) {

        val clothesService = ServiceBuilder.buildService(GamarraApi::class.java)
        val requestCall = clothesService.getClotheById(id)

        requestCall.enqueue(object: retrofit2.Callback<Clothes> {
            override fun onResponse(call: Call<Clothes>, response: Response<Clothes>) {
                if(response.isSuccessful) {
                    val clothes = response.body()

                    clothes?.let {
                        Log.i("Clothe", clothes.toString())
                        Toast.makeText(this@ItemDetailActivity, "URL Image: " + clothes.sizeId.name, Toast.LENGTH_LONG).show()

                        item_img.setImageUrl(clothes.urlphoto)
                        item_category.text = clothes.categoryId.name
                        item_description.text = clothes.description
                        item_name.text = clothes.name
                        item_size.text = "Talla: " + clothes.sizeId.name
                    }
                }else{
                    Toast.makeText(this@ItemDetailActivity, "Failed to retrieve details", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Clothes>, t: Throwable) {
                Toast.makeText(this@ItemDetailActivity, "Failed to retrieve details", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        const val ARG_ITEM_ID = "id"
    }
}
