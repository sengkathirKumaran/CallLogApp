package com.sk.calllogtaskapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sk.calllogtaskapp.R
import com.sk.calllogtaskapp.db.ContactCacheEntity
import com.sk.calllogtaskapp.ui.contacts.ContactViewModel
import com.sk.calllogtaskapp.ui.contacts.FavouritesFragment


private const val INDEX_OF_FAVOURITES = 0
private const val INDEX_OF_CONTACTS = 1
private const val NUM_TABS = 2
private val TAB_TITLES = mapOf(
    INDEX_OF_FAVOURITES to "favourites",
    INDEX_OF_CONTACTS to "contacts"
)

class TabsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = NUM_TABS
    override fun createFragment(position: Int): Fragment = if (position == INDEX_OF_FAVOURITES) {
        FavouritesFragment()
    } else {
        ContactsFragment()
    }
}


class ContactFragment : Fragment(R.layout.fragment_contacts) {
    private lateinit var viewPager: ViewPager2
    private var contact: ContactCacheEntity? = null
    private lateinit var conViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        contact = arguments?.getParcelable("favContact")
        println("contact.number=>${contact?.number}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        conViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)
//conViewModel.allContacts()

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val sdf = SimpleDateFormat("MMM dd, yyyy")
//        val currentDate = sdf.format(Date())
        println("con.num=>${contact?.number} con.name=>${contact?.name}")
        if (contact?.number.equals(null) || contact?.number.equals("null") || contact!!.number.contentEquals(
                "null"
            )
        ) {

        } else {

            val call = ContactCacheEntity(
                contact!!.id,
                contact?.name.toString(),
                contact?.number.toString(),
                contact?.image.toString(),
                contact?.favourites.toString()
            )

            if (call.number.equals(null) || call.number.contentEquals("null") || call.number.equals(
                    "null"
                )
            ) {

            } else {
//            callViewModel.addCall(call)
                println("call.num=>${call.number} call.name=>${call.name}")
                conViewModel.update(call)

            }

        }
//        println("call.number->${call.number}")

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity: AppCompatActivity = activity as AppCompatActivity
        val fragmentView =
            requireNotNull(view) { "View should not be null when calling onActivityCreated" }

//        val toolbar: Toolbar = fragmentView.findViewById(R.id.toolbar)
//        activity.setSupportActionBar(toolbar)

        val tabLayout: TabLayout = fragmentView.findViewById(R.id.tabs)
        viewPager = fragmentView.findViewById(R.id.view_pager)
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = TabsAdapter(childFragmentManager, lifecycle)


        // connect the tabs and view pager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            tab.text = TAB_TITLES[position]

            viewPager.setCurrentItem(tab.position, true)
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        tabLayout.getTabAt(INDEX_OF_FAVOURITES)?.let { tabLayout.selectTab(it) }
    }
}


