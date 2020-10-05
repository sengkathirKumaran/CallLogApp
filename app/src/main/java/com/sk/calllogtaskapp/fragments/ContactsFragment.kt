package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallDatabase
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.ui.contacts.ContactViewModel
import kotlinx.android.synthetic.main.fragment_calls.*
import kotlinx.android.synthetic.main.fragment_calls.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class ContactsFragment() : Fragment() {

    private lateinit var contactViewModel: ContactViewModel

    var searchList = ArrayList<ContactCacheEntity>()
    private var displayList = ArrayList<ContactCacheEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
        println("retainInstance=>$retainInstance")

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.contactFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        var rv = root.findViewById<RecyclerView>(R.id.recView)
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        val recyclerView = root.recView


        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            val dao = CallDatabase.getDatabase(requireContext()).contactDao()
            val sen = dao.allContacts()
            val array = arrayListOf<ContactCacheEntity>()
            array.addAll(sen)
            searchList.addAll(array)
            println("coro-searchList=>=>>>$searchList")
            displayList.addAll(searchList)
            val adapter = ContactsAdapter(displayList, context)


        }

        contactViewModel.readAllData.observe(
            viewLifecycleOwner,
            Observer { user -> ContactsAdapter(displayList, context).setData(user) })
        recyclerView.adapter = ContactsAdapter(displayList, context)




        return root
    }








    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val search = menu?.findItem(R.id.miSearch)
        val searchView = search?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.isNotEmpty()) {
                    displayList.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    println("searchList=>$searchList")
                    searchList.forEach {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(search)) {
                            displayList.add(it)
                        }
                    }

                    recView.adapter!!.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(searchList)
                    recView.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.delete) {

        } else {
            findNavController().navigate(R.id.contactFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}




