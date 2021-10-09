package com.example.applaunchtask

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applaunchtask.adapter.UserAdapter
import com.example.applaunchtask.databinding.FragmentUserListBinding
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.viewmodel.UserViewModel

class UserListFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var fragmentUserListBinding : FragmentUserListBinding
    var userAdapter : UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_list, container, false)

//        requireActivity().setS
        return fragmentUserListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.getUserList(requireContext())

        fragmentUserListBinding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_userFormFragment)
        }
        viewModel.userList.observe(viewLifecycleOwner) {
            setAdapter(it)
        }
    }

    private fun setAdapter(list: List<UserDetail>) {
        if (userAdapter == null) {
            userAdapter = UserAdapter(requireContext(), list)
            fragmentUserListBinding.rvUserList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            fragmentUserListBinding.rvUserList.adapter = userAdapter
        } else {
            userAdapter?.updateList(list)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_user, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                findNavController().navigate(R.id.action_userListFragment_to_userFormFragment)
            }
        }
        return true
//        return item.onNavDestinationSelected(navController)
    }

    companion object {

    }
}