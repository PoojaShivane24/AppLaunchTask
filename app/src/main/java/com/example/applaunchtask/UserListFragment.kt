package com.example.applaunchtask

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applaunchtask.adapter.UserAdapter
import com.example.applaunchtask.databinding.FragmentUserListBinding
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.viewmodel.UserViewModel
import java.util.*
import kotlin.collections.ArrayList

class UserListFragment : Fragment(), RecyclerViewClickInterface {

    private var list : MutableList<UserDetail> = ArrayList()
    private lateinit var viewModel: UserViewModel
    lateinit var fragmentUserListBinding : FragmentUserListBinding
    var userAdapter : UserAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentUserListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user_list, container, false)

        return fragmentUserListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.getUserList()

        viewModel.userList.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.e("TAG", "onViewCreated: livedata " + it.toString())
                setAdapter(it)
                list = it as MutableList<UserDetail>
                Log.e("TAG", "onViewCreated: list " + list.toString())
            } else {

            }
        }

        val itemTouchHelper : ItemTouchHelper = ItemTouchHelper((simpleCallBack))
        itemTouchHelper.attachToRecyclerView(fragmentUserListBinding.rvUserList)
    }

    val simpleCallBack : ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    viewModel.deleteUserData(list[position])
                    userAdapter?.removeData(position)
                }
            }
        }

    }


    private fun setAdapter(list: List<UserDetail>) {
        if (userAdapter == null) {
            userAdapter = UserAdapter(requireContext(), list, this)
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
    }


    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_userListFragment_to_weatherDataFragment)
    }
}