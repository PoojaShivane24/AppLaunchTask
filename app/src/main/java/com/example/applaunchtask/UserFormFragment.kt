package com.example.applaunchtask

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.applaunchtask.databinding.FragmentUserFormBinding
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.viewmodel.UserViewModel

class UserFormFragment : Fragment() {


    private lateinit var viewModel: UserViewModel
    private lateinit var fragmentUserFormBinding: FragmentUserFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserFormBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_form, container, false)
        return fragmentUserFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        fragmentUserFormBinding.btnSave.setOnClickListener {
            val name = fragmentUserFormBinding.tvName.text.toString()
            val lastName = fragmentUserFormBinding.tvLastName.text.toString()
            val email = fragmentUserFormBinding.tvEmail.text.toString()

            when {
                name.isEmpty() -> {
                    fragmentUserFormBinding.tvName.error = "Enter User Name"
                    fragmentUserFormBinding.tvName.requestFocus()
                }
                lastName.isEmpty() -> {
                    fragmentUserFormBinding.tvLastName.error = "Enter Last Name"
                    fragmentUserFormBinding.tvLastName.requestFocus()
                }
                email.isEmpty() -> {
                    fragmentUserFormBinding.tvEmail.error = "Enter Email Id"
                    fragmentUserFormBinding.tvEmail.requestFocus()
                }
                else -> {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        val entity = UserDetail(name, lastName, email)
                        viewModel.saveUser(entity)
                    } else {
                        Toast.makeText(requireContext(), "Enter valid Email Id", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        fragmentUserFormBinding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_userFormFragment_to_userListFragment)
        }

        viewModel.isInserted.observe(viewLifecycleOwner) {
            if (it) {
                fragmentUserFormBinding.tvName.text?.clear()
                fragmentUserFormBinding.tvLastName.text?.clear()
                fragmentUserFormBinding.tvEmail.text?.clear()
                fragmentUserFormBinding.tvName.requestFocus()

                Toast.makeText(requireContext(), "User saved", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(requireContext(), "User exist already", Toast.LENGTH_SHORT).show()
        }


    }

    companion object {

    }
}