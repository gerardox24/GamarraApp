package pe.edu.upc.gamarraapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_clothe.view.*
import pe.edu.upc.gamarraapp.R
import pe.edu.upc.gamarraapp.models.Clothe

class ClotheAdapter(var clothes: List<Clothe>) : RecyclerView.Adapter<ClotheAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pictureImageView = itemView.pictureImageView
        val titleTextView = itemView.titleTextView
        val moreButton = itemView.moreButton

        fun bindTo(clothe: Clothe) {
            pictureImageView.apply {
                setDefaultImageResId(R.mipmap.ic_launcher)
                setErrorImageResId(R.mipmap.ic_launcher)
                setImageUrl(clothe.urlphoto)
            }
            titleTextView.text = clothe.name
            moreButton.setOnClickListener{

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