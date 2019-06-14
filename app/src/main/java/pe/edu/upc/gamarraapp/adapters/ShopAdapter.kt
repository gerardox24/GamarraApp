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

class ShopAdapter(var shops: List<Shop>) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shopNameTextView = itemView.shopNameTextView

        fun bindTo(shop: Shop) {
            shopNameTextView.text = shop.address
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