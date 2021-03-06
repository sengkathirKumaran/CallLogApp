package com.sk.calllogtaskapp.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.calllogtaskapp.R
import kotlinx.android.synthetic.main.fragment_calls.view.*


class FavouritesFragment : Fragment() {
    private lateinit var favViewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.callFragment)
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

        val adapter = FavouritesAdapter()


        val recyclerView = root.recView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        favViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        favViewModel.readFavs.observe(
            viewLifecycleOwner,
            Observer { user -> adapter.setData(user) })
        return root
    }
}