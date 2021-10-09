package com.example.applaunchtask.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.applaunchtask.repository.UserRepository
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.roomdatabase.UserEntity

class UserViewModel : ViewModel() {

    var repo : UserRepository = UserRepository()
    val isInserted : LiveData<Boolean>
        get() {
            return repo.isInserted
        }
    val isLoggedIn : LiveData<Boolean>
        get() {
            return repo.isLoggedIn
        }
    val userList : LiveData<List<UserDetail>>
        get() {
            return repo.userList
        }

    fun signUpUser(context: Context, entity: UserEntity) {
        repo.signUpUser(context, entity)
    }

    fun loginUser(context: Context, userName: String, password: String) {
        repo.loginUser(context, userName, password)
    }

    fun getUserList(context: Context) {
        repo.getUserList(context)
    }

    fun saveUser(context: Context, entity: UserDetail) {
        repo.saveUser(context, entity)
    }

}