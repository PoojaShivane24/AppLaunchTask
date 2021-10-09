package com.example.applaunchtask

import android.content.Context
import android.content.SharedPreferences
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
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoginSignupBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_signup, container, false)


        return fragmentLoginSignupBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        fragmentLoginSignupBinding.btnLogReg.setOnClickListener {
            loginUser()
        }
        fragmentLoginSignupBinding.tvLogReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        sharedPreferences = requireContext().getSharedPreferences("UserSharedPreference", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Log In Successfully", Toast.LENGTH_SHORT).show()
                editor.putBoolean("isLoggedIn", it)
                editor.apply()
                findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
            } else Toast.makeText(requireContext(), "Log In Failed", Toast.LENGTH_SHORT).show()
        }


    }


    override fun onStart() {
        super.onStart()
        val isUserLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isUserLoggedIn)
            findNavController().navigate(R.id.action_loginFragment_to_userListFragment)

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