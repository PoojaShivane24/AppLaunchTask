package com.example.applaunchtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.applaunchtask.R
import com.example.applaunchtask.databinding.FragmentUserListBinding
import com.example.applaunchtask.databinding.UserItemBinding
import com.example.applaunchtask.roomdatabase.UserDetail

class UserAdapter(context: Context, userList : List<UserDetail>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList : MutableList<UserDetail> = ArrayList()
    private var context : Context
    init {
        if (userList != null) {
            this.userList.addAll(userList)
        }
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val userItemBinding : UserItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_item, parent, false)
        return UserViewHolder(userItemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.view.tvName.text = user.userName
        holder.view.tvLastName.text = user.lastName
        holder.view.tvEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(list: List<UserDetail>) {
        userList.addAll(list)
        notifyItemInserted(list.size-1)
    }

    class UserViewHolder(val view : UserItemBinding) : RecyclerView.ViewHolder(view.root)


}