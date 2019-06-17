package pe.edu.upc.gamarraapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shop_item.view.*

import pe.edu.upc.gamarraapp.R

import pe.edu.upc.gamarraapp.models.Shop
import androidx.core.content.ContextCompat.startActivity
import android.net.Uri


class ShopAdapter(var shops: List<Shop>) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Nivel de zoom del mapa
        private val ZOOM = 18;
        val shopNameTextView = itemView.shopNameTextView

        fun bindTo(shop: Shop) {
            shopNameTextView.text = shop.address

            itemView.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${shop.latitude},${shop.longitude}?z=${ZOOM}&q=${shop.latitude},${shop.longitude}(${shop.businessId?.name})")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                it.context.startActivity(mapIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_item, parent, false))
    }

    override fun getItemCount(): Int {
        return shops.size
    }

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        holder.bindTo(shops[position])
    }
}