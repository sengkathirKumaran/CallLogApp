package com.sk.calllogtaskapp.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallDatabase
import com.sk.calllogtaskapp.db.ContactCacheEntity
import kotlinx.android.synthetic.main.adapter_calls.view.name
import kotlinx.android.synthetic.main.adapter_calls.view.number
import kotlinx.android.synthetic.main.adapter_contacts.view.*
import java.util.*
import kotlin.collections.ArrayList


class ContactsAdapter(
    private val contactsList: ArrayList<ContactCacheEntity>,
    val context: Context?
) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private var contactList = emptyList<ContactCacheEntity>()
    private var click: Boolean = true
    var bundle = Bundle()
    val dao = CallDatabase.getDatabase(context!!).contactDao()

    //    var contactViewModel = ViewModelProvider(ContactsFragment()).get(ContactViewModel::class.java)
    var selectedList = ArrayList<ContactCacheEntity>()
    var strList = java.util.ArrayList<String>()
    lateinit var actionMode: ActionMode
    var counter = 0
    var checked = false
    private lateinit var builder: androidx.appcompat.app.AlertDialog.Builder

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val favBtn = itemView.findViewById<Button>(R.id.star_btn)




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_contacts, parent, false) as View

        return ViewHolder(view)*/


        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_contacts, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = contactsList[position]

        if (currentItem.favourites == "Y")
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_star_24)
        else if (currentItem.favourites == "N")
            holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_star_border_24)

        holder.favBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (currentItem.favourites == "Y") {
                    currentItem.favourites = "N"
                    holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
                } else {
                    currentItem.favourites = "Y"
                    holder.favBtn.setBackgroundResource(R.drawable.ic_baseline_star_24)
                }
                val bundle = bundleOf("favContact" to currentItem)
                holder.itemView.findNavController().navigate(R.id.contactFragment, bundle)
            }
        })


        holder.itemView.name.text = currentItem.name
        holder.itemView.number.text = currentItem.number
        println("img-str=>${currentItem.image}")

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


        /*fun destroy() {
            holder.favBtn.visibility = View.VISIBLE
            holder.itemView.call_image.visibility = View.VISIBLE
            holder.itemView.checkbox.visibility = View.GONE
//            holder.itemView.checkbox.isChecked = true
        }*/


        holder.itemView.cardView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {


                holder.favBtn.visibility = View.GONE
                holder.itemView.call_image.visibility = View.GONE
                holder.itemView.checkbox.visibility = View.VISIBLE
                holder.itemView.checkbox.isChecked = true

                counter++
                selectedList.add(currentItem)


                builder = AlertDialog.Builder(context!!)
                //set title for alert dialog
                builder.setTitle("Delete")
                //set message for alert dialog
                builder.setMessage("Are you sure do you want to delete the contact?")
//                    builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes, Delete") { dialogInterface, which ->
                    Toast.makeText(context, "clicked yes", Toast.LENGTH_SHORT).show()
//                        println("AM")

//                        selectedList.forEach {
//                            strList.add(it.number)
//                        }

//                        println("delete-CONTACT=> selectedList =>$selectedList")
//                        contactViewModel.deleteMultiple(strList.toList())
                    if (selectedList.isNotEmpty()) {
                        println("not empty")
                        val bundle = bundleOf("deleteContact" to selectedList)
                        holder.itemView.findNavController()
                            .navigate(R.id.bottomSheetFragment, bundle)
                    } else {
                        println("empty")
                        Toast.makeText(context, "Please select item to delete", Toast.LENGTH_LONG)
                            .show()

                    }
                }
                //performing cancel action
                /*builder.setNeutralButton("Cancel") { dialogInterface, which ->
                    Toast.makeText(
                        context,
                        "clicked cancel\n operation cancel",
                        Toast.LENGTH_LONG
                    ).show()

                }*/
                //performing negative action
                builder.setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(context, "clicked No", Toast.LENGTH_LONG).show()
                    holder.itemView.findNavController().navigate(R.id.contactFragment)
                }



                actionMode = holder.itemView.startActionMode(actionModeCallback)

                println("after AM")

                return true
            }
        })


        holder.itemView.call_image.setOnClickListener {
            val bundle = bundleOf("selectedContact" to currentItem)
            holder.itemView.findNavController().navigate(R.id.callFragment, bundle)
        }
    }


    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            mode.setTitle(counter.toString())

            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_main, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete -> {
//                    shareCurrentItem()

                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    val window: Window = alertDialog.getWindow()!!
                    val wlp: WindowManager.LayoutParams = window.getAttributes()

                    wlp.gravity = Gravity.BOTTOM
                    alertDialog.setCancelable(false)
                    alertDialog.show()




                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {

        }
    }


    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun setData(call: List<ContactCacheEntity>) {
        println("CALL=>$call")
        this.contactList = call
        println("CA--contactList=>$contactList")
        println("this.contactList=>${this.contactList}")
        notifyDataSetChanged()
    }

    /*fun delete(al: ArrayList<ContactCacheEntity>) {

        al.forEach{
            strList.add(it.number)
        }
        dao.deleteMultiple(strList.toList())
    }*/


}