package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.ui.contacts.ContactViewModel
import java.util.*


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var contactViewModel: ContactViewModel

    //    private var contact: ContactCacheEntity? = null
    private var bundle = ArrayList<ContactCacheEntity>()
    var strList = java.util.ArrayList<String>()
//    var list: List<String> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        isCancelable = false
        bundle =
            arguments?.getParcelableArrayList<ContactCacheEntity>("deleteContact") as ArrayList<ContactCacheEntity>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.fragment_bottom_sheet, container, false
        )
        dismiss()
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

//        val noButton: Button = v.findViewById(R.id.noButton)
//        val yesButton: Button = v.findViewById(R.id.yesButton)

        bundle.forEach {
            strList.add(it.number)
        }
        contactViewModel.deleteMultiple(strList.toList())
        findNavController().navigate(R.id.contactFragment)
        dismiss()

        /*yesButton.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        noButton.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.contactFragment)
            dismiss()
        })*/
        return v
    }

    companion object {
        const val TAG = "BottomSheetFragment"
    }

}