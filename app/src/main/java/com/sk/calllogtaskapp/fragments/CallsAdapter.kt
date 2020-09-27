package com.sk.calllogtaskapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallCacheEntity
import kotlinx.android.synthetic.main.adapter_calls.view.*


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
        holder.itemView.date.text = currentItem.date
        println("Name=>" + currentItem.name)
        println("Number=>" + currentItem.number)
        println("date=>" + currentItem.date)
//        holder.itemView.image.drawable = currentItem.image

        /*holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return callList.size
    }

    fun setData(call: List<CallCacheEntity>) {
        this.callList = call
//        println("0=>"+this.callList.get(0).name)
        println("size=>" + this.callList.size)
        notifyDataSetChanged()
    }

}
