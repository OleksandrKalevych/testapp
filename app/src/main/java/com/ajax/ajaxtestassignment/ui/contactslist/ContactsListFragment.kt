package com.ajax.ajaxtestassignment.ui.contactslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajax.ajaxtestassignment.R
import com.ajax.ajaxtestassignment.api.RetrofitServicesProvider
import com.ajax.ajaxtestassignment.databinding.FragmentContactsListBinding
import com.ajax.ajaxtestassignment.db.getDatabase
import com.ajax.ajaxtestassignment.repository.ContactsRepository
import com.ajax.ajaxtestassignment.ui.contactslist.adapter.OnContactItemClickListener
import com.ajax.ajaxtestassignment.ui.details.DetailsFragment.Companion.CONTACT_ID
import com.ajax.ajaxtestassignment.ui.model.ContactPresentation

open class ContactsListFragment : Fragment() {
    private lateinit var contactAdapter: ContactAdapter

    private var binding: FragmentContactsListBinding? = null
    private var viewModel: ContactsViewModel? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactAdapter = ContactAdapter(listOf(), requireActivity(),
            object : OnContactItemClickListener{
                override fun onItemClick(item: ContactPresentation) {
                    findNavController().navigate(R.id.action_contactList_to_contactDetails, bundleOf(CONTACT_ID to item.id))
                }
            },
            object : OnContactItemClickListener{
                override fun onItemClick(item: ContactPresentation) {
                    viewModel?.delete(item.id)
                }
            }
        )



        //move to DI
        val database = getDatabase(requireContext())
        val repository = ContactsRepository(RetrofitServicesProvider().contactsService, database.userDao())

        //move to DI
        viewModel = ViewModelProviders
            .of(this, ContactsViewModel.FACTORY(repository))
            .get(ContactsViewModel::class.java)

        viewModel?.contacts?.observe(viewLifecycleOwner) { value ->
            value?.let {
                if (it.isEmpty()) {
                    viewModel?.onReload()
                }

                val newItems = it
                contactAdapter.apply {
                    this.items = newItems
                    notifyDataSetChanged()
                }
            }
        }

        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter
                refresh.setOnClickListener { viewModel?.deleteAll() }
            }
            .also {
                binding = it
            }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        viewModel = null
    }
}