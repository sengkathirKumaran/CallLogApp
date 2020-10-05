package com.sk.calllogtaskapp.ui.contacts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.ContactCacheEntity
import kotlinx.android.synthetic.main.adapter_favourites.view.*
import java.util.*


class FavouritesAdapter() : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private var favList = emptyList<ContactCacheEntity>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_favourites, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = favList[position]

        holder.itemView.name.text = currentItem.name
        holder.itemView.number.text = currentItem.number

        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        if (currentItem.image.isNotEmpty() && currentItem.image.isNotBlank() && currentItem.image != "null") {
            Glide.with(holder.itemView)
                .load("https://source.unsplash.com/random")
                .transform(CircleCrop())
                .into(holder.itemView.image)
        } else {
            val drawable = TextDrawable.builder()
                .buildRound(currentItem.name[0].toString().toUpperCase(), color)
            val image = holder.itemView.findViewById(R.id.image) as ImageView
            image.setImageDrawable(drawable)

        }

        holder.itemView.call_image.setOnClickListener {

            val bundle = bundleOf("selectedContact" to currentItem)
            holder.itemView.findNavController().navigate(R.id.callFragment, bundle)
        }


    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun setData(call: List<ContactCacheEntity>) {
        this.favList = call
        notifyDataSetChanged()
    }

}