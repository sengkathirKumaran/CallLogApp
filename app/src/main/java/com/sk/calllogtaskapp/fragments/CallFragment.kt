package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.CallCacheEntity
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.ui.calls.CallViewModel
import kotlinx.android.synthetic.main.fragment_calls.view.*
import java.text.SimpleDateFormat
import java.util.*


class CallFragment : Fragment() {

    private lateinit var callViewModel: CallViewModel
    private var contact: ContactCacheEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = arguments?.getParcelable("selectedContact")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calls, container, false)
        val adapter = CallsAdapter()
        val recyclerView = root.recView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        callViewModel =
            ViewModelProvider(this).get(CallViewModel::class.java)

//        callViewModel.addCall()
        callViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("MMM dd, yyyy")
        val currentDate = sdf.format(Date())

        val call = CallCacheEntity(
            0,
            contact?.name.toString(),
            contact?.number.toString(),
            currentDate,
            contact?.image.toString()
        )

        if (call.number.equals(null) || call.number.contentEquals("null") || call.number.equals("null")) {

        } else {
            callViewModel.addCall(call)
        }
    }
}
