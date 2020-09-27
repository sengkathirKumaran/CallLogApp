package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.fragments.BottomSheetFragment.Companion.TAG
import com.sk.calllogtaskapp.ui.contacts.ContactViewModel
import kotlinx.android.synthetic.main.fragment_calls.view.*
import java.util.*


class ContactsFragment() : Fragment() {

    private lateinit var contactViewModel: ContactViewModel
    private val adapter = ContactsAdapter()
    var searchList = ArrayList<ContactCacheEntity>()
    private var displayList = ArrayList<ContactCacheEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        var rv = root.findViewById<RecyclerView>(R.id.recView)

        println("1st-searchList=>$searchList")
        val recyclerView = root.recView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)


        contactViewModel.allContacts()

        contactViewModel.readAllData.observe(
            viewLifecycleOwner,
            Observer { user -> adapter.setData(user) })


//        searchList.addAll()
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
                            println("displayLIST=>$displayList")
                        }
                    }

                    adapter.notifyDataSetChanged()
                } else {
                    displayList.clear()
                    displayList.addAll(searchList)
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.delete) {
            BottomSheetFragment().show(fragmentManager!!, TAG)
        } else {
            findNavController().navigate(R.id.contactsFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}




