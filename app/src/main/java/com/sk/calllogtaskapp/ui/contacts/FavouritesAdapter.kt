package com.sk.calllogtaskapp.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.fragments.ContactsAdapter
import kotlinx.android.synthetic.main.adapter_calls.view.*
import kotlinx.android.synthetic.main.adapter_calls.view.name
import kotlinx.android.synthetic.main.adapter_calls.view.number
import kotlinx.android.synthetic.main.adapter_contacts.view.*
import java.text.SimpleDateFormat
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

        holder.itemView.call_image.setOnClickListener {
            val sdf = SimpleDateFormat("MMM dd, yyyy")
            val currentDate = sdf.format(Date())
            val call = CallCacheEntity(
                0,
                currentItem.name,
                currentItem.number,
                currentDate,
                currentItem.image
            )


            val bundle = bundleOf("selectedContact" to currentItem)
            println("CONTACT=>$bundle")
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