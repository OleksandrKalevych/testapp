package com.ajax.ajaxtestassignment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ajax.ajaxtestassignment.api.RetrofitServicesProvider
import com.ajax.ajaxtestassignment.databinding.FragmentDetailsBinding
import com.ajax.ajaxtestassignment.db.getDatabase
import com.ajax.ajaxtestassignment.repository.ContactsRepository
import com.ajax.ajaxtestassignment.ui.model.ContactPresentation
import com.bumptech.glide.Glide


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    private var viewModel: DetailsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //move to DI
        val database = getDatabase(requireContext())

        // no need to do INSTANCE for now - super lightweight class (yet), better move to DI
        val repository = ContactsRepository(RetrofitServicesProvider().contactsService, database.userDao())

        //move to DI
        viewModel = ViewModelProviders
            .of(this, DetailsViewModel.FACTORY(repository))
            .get(DetailsViewModel::class.java)


        var newContact = ContactPresentation()

        viewModel?.contact?.observe(viewLifecycleOwner) { value ->
            binding?.apply {
                newContact = value.copy()
                name.setText(value.firstName)
                surname.setText(value.lastName)
                mail.setText(value.email)

                name.doOnTextChanged { text, start, before, count ->
                    newContact = newContact.copy(firstName = text.toString())
                    viewModel?.update(newContact.toDbContact())
                }

                surname.doOnTextChanged { text, start, before, count ->
                    newContact = newContact.copy(lastName = text.toString())
                    viewModel?.update(newContact.toDbContact())
                }

                mail.doOnTextChanged { text, start, before, count ->
                    newContact = newContact.copy(email = text.toString())
                    viewModel?.update(newContact.toDbContact())
                }

                Glide.with(picture)
                    .load(value.photo)
                    .into(picture)

                delete.setOnClickListener {
                    arguments?.getInt(CONTACT_ID)?.let { it1 -> viewModel?.delete(it1) }
                }
            }
        }

        if(arguments?.containsKey(CONTACT_ID) == true){
            // if somehow crash here better fail fast imho thats why "!!"
            viewModel?.requestContact(arguments?.getInt(CONTACT_ID)!!)
        }else{
            //??
        }

        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }
            .root
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
        const val CONTACT_ID = "contact_Id"
    }
}