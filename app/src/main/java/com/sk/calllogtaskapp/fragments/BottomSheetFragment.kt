package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.ui.contacts.ContactViewModel


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var contactViewModel: ContactViewModel
    private var contact: ContactCacheEntity? = null
    var list: List<String> = emptyList()
//    var noClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = arguments?.getParcelable("deleteContact")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.fragment_bottom_sheet, container, false
        )

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        val noButton: Button = v.findViewById(R.id.noButton)
        val yesButton: Button = v.findViewById(R.id.yesButton)
        yesButton.setOnClickListener(View.OnClickListener {
//            println("contact.number=>${contact!!.number}")

            contactViewModel.delete(contact!!)
            dismiss()
        })
        noButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.contactFragment)
            dismiss()
        })
        return v
    }

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

}