package com.ajax.ajaxtestassignment.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajax.ajaxtestassignment.databinding.FragmentContactsListBinding

open class ContactsListFragment : Fragment() {
    private lateinit var contactAdapter: ContactAdapter

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactAdapter = ContactAdapter(
            listOf("Text"),
            requireActivity()
        )

        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter
            }
            .also {
                binding = binding
            }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}