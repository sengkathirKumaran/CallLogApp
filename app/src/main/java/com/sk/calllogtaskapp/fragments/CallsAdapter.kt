package com.sk.calllogtaskapp.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallCacheEntity
import kotlinx.android.synthetic.main.adapter_calls.view.*
import java.text.SimpleDateFormat
import java.util.*


public class CallsAdapter : RecyclerView.Adapter<CallsAdapter.ViewHolder>() {

    private var callList = emptyList<CallCacheEntity>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_calls, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = callList[position]

        holder.itemView.name.text = currentItem.name
        holder.itemView.number.text = currentItem.number

        val sdf = SimpleDateFormat("MMM dd, yyyy")
        val currentDate = sdf.format(Date())

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val t = sdf.format(cal.time)

        when {
            currentDate == currentItem.date -> {
                holder.itemView.date.text = "Today"
            }
            t == currentItem.date -> {
                holder.itemView.date.text = "Yesterday"
            }
            else -> {
                holder.itemView.date.text = currentItem.date
            }
        }

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

    }

    override fun getItemCount(): Int {
        return callList.size
    }

    fun setData(call: List<CallCacheEntity>) {
        this.callList = call
        notifyDataSetChanged()
    }

}
