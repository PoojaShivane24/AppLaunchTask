package com.example.applaunchtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.applaunchtask.databinding.FragmentUserFormBinding

class UserFormFragment : Fragment() {


    private lateinit var fragmentUserFormBinding: FragmentUserFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserFormBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_form, container, false)
        return fragmentUserFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

    }
}