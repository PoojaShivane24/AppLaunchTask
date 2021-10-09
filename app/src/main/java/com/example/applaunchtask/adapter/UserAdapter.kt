package com.example.applaunchtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.applaunchtask.R
import com.example.applaunchtask.RecyclerViewClickInterface
import com.example.applaunchtask.databinding.UserItemBinding
import com.example.applaunchtask.roomdatabase.UserDetail

class UserAdapter(context: Context, userList : List<UserDetail>, recyclerViewClickInterface: RecyclerViewClickInterface) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList : MutableList<UserDetail> = ArrayList()
    private var context : Context
    private var recyclerViewClickInterface: RecyclerViewClickInterface? = null
    init {
        if (userList != null) {
            this.userList.addAll(userList)
        }
        this.context = context
        this.recyclerViewClickInterface = recyclerViewClickInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val userItemBinding : UserItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_item, parent, false)
        return UserViewHolder(userItemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        val name = "Name: "+user.userName
        val lastName = "Last Name: "+user.lastName
        val email = "Email: " + user.email
        holder.view.tvName.text = name
        holder.view.tvLastName.text = lastName
        holder.view.tvEmail.text = email

        holder.view.cvContainer.setOnClickListener {
            recyclerViewClickInterface?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(list: List<UserDetail>) {
        userList = list as MutableList<UserDetail>
        notifyItemInserted(list.size-1)
    }

    fun removeData(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

    class UserViewHolder(val view : UserItemBinding) : RecyclerView.ViewHolder(view.root)


}