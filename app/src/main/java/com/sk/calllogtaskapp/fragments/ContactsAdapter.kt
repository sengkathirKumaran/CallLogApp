package com.sk.calllogtaskapp.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.ContactCacheEntity
import kotlinx.android.synthetic.main.adapter_calls.view.name
import kotlinx.android.synthetic.main.adapter_calls.view.number
import kotlinx.android.synthetic.main.adapter_contacts.view.*
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*


class ContactsAdapter() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private var contactList = emptyList<ContactCacheEntity>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_contacts, parent, false) as View

        return ViewHolder(view)*/

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_contacts, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = contactList[position]

        holder.itemView.name.text = currentItem.name
        holder.itemView.number.text = currentItem.number
        if (currentItem.image.isNotEmpty() && currentItem.image.isNotBlank() && currentItem.image != "null") {
            val inputStream =
                holder.itemView.context.contentResolver.openInputStream(currentItem.image.toUri())
            val drawable = Drawable.createFromStream(inputStream, currentItem.image)
            holder.itemView.image.setImageDrawable(drawable)
        }

        holder.itemView.cardView.setOnClickListener {
            holder.itemView.star_image.visibility = View.GONE
            holder.itemView.call_image.visibility = View.GONE
            holder.itemView.checkbox.visibility = View.VISIBLE
            holder.itemView.checkbox.isChecked = true

//            var bundle = Bundle()
//            bundle.putParcelable("deleteContact", currentItem)
            val bundle = bundleOf("deleteContact" to currentItem)
            println("delete-CONTACT=>$bundle")
            holder.itemView.findNavController().navigate(R.id.bottomSheetFragment, bundle)

        }

//        if (BottomSheetFragment().noClicked) {
//            holder.itemView.findNavController().navigate(R.id.contactFragment)
//        }


        holder.itemView.star_image.setOnClickListener {

            val sdf = SimpleDateFormat("MMM dd, yyyy")
            val currentDate = sdf.format(Date())
            val call = CallCacheEntity(
                0,
                currentItem.name,
                currentItem.number,
                currentDate,
                currentItem.image
            )


            val bundle = bundleOf("favContact" to currentItem)
            println("CONTACT=>$bundle")
            holder.itemView.findNavController().navigate(R.id.contactFragment, bundle)
        }


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

//            println("1===>" + R.drawable.ic_baseline_star_border_24)
//            println("2===>" + R.drawable.ic_baseline_star_24)

//            val arr = arrayOf("2131165296", "2131165297")


        /*val drawRes = when (arr[]) {
            "2131165296" -> holder.itemView.star_image.setImageResource(R.drawable.ic_baseline_star_24)

            "2131165297" -> holder.itemView.star_image.setImageResource(R.drawable.ic_baseline_star_border_24)

            else -> holder.itemView.star_image.setImageResource(R.drawable.ic_baseline_star_border_24)

        }*/

//            if (holder.itemView.star_image.equals(R.drawable.ic_baseline_star_border_24)) {
//                println("<-----IF--->")
//
//                holder.itemView.star_image.setImageResource(R.drawable.ic_baseline_star_24)
//            } else {
//                println("<-----ELSE--->")
//                holder.itemView.star_image.setImageResource(R.drawable.ic_baseline_star_border_24)
//            }

//        holder.itemView.image.drawable = currentItem.image


    }


    override fun getItemCount(): Int {
        return contactList.size
    }

    fun getList(): List<ContactCacheEntity> {
        println("contactList=>$contactList")
        return contactList

    }


    fun setData(call: List<ContactCacheEntity>) {
        this.contactList = call
        notifyDataSetChanged()
    }


}