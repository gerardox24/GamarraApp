package pe.edu.upc.gamarraapp.adapters

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_clothe.view.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.Clothes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pe.edu.upc.gamarraapp.controllers.activities.ItemDetailActivity
import pe.edu.upc.gamarraapp.controllers.activities.MainActivity


class ClothesAdapter(var clothes: List<Clothes>) : RecyclerView.Adapter<ClothesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pictureImageView = itemView.pictureImageView
        val titleTextView = itemView.titleTextView
        val categoryTextView = itemView.itemCategory
        //val buttonItem = itemView.btn_item
        val button_aux = itemView.btn_aux

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
