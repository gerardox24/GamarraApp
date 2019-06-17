package pe.edu.upc.gamarraapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_business.view.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.Business
import pe.edu.upc.gamarraapp.network.GamarraApi

data class BusinessAdapter(var businesses: List<Business>) : RecyclerView.Adapter<BusinessAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val businessNameTextView = itemView.businessNameTextView

        fun bindTo(business: Business) {
            businessNameTextView.text = business.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_business, parent, false))
    }

    override fun getItemCount(): Int {
        return businesses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(businesses[position])
    }
}