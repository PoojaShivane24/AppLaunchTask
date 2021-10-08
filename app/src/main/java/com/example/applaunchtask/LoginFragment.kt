package com.example.applaunchtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.applaunchtask.databinding.FragmentLoginSignupBinding
import com.example.applaunchtask.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LoginFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var fragmentLoginSignupBinding: FragmentLoginSignupBinding
    val coroutineScope = CoroutineScope(SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoginSignupBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_signup, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return fragmentLoginSignupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentLoginSignupBinding.btnLogReg.setOnClickListener {
            loginUser()
        }
        fragmentLoginSignupBinding.tvLogReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Log In Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
            }
            else Toast.makeText(requireContext(), "Log In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val userName = fragmentLoginSignupBinding.tvName.text.toString()
        val password = fragmentLoginSignupBinding.tvPassword.text.toString()

        when {
            userName.isEmpty() -> {
                fragmentLoginSignupBinding.textInputLayout2.isEndIconVisible = true
                fragmentLoginSignupBinding.tvName.error = "Enter User Name"
                fragmentLoginSignupBinding.tvName.requestFocus()
            }
            password.isEmpty() -> {
                fragmentLoginSignupBinding.textInputLayout2.isEndIconVisible = false
                fragmentLoginSignupBinding.tvPassword.error = "Enter Password"
                fragmentLoginSignupBinding.tvPassword.requestFocus()
            }
            else -> {
                fragmentLoginSignupBinding.textInputLayout2.isEndIconVisible = true

                viewModel.loginUser(requireContext(), userName, password)

            }
        }
    }

    companion object {

    }
}