package com.example.applaunchtask.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.applaunchtask.roomdatabase.UserDatabase
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.roomdatabase.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class UserRepository {
    var coroutineScope = CoroutineScope(SupervisorJob())
    private val isInsertedLiveData = MutableLiveData<Boolean>()
    val isInserted : LiveData<Boolean>
        get() {
            return isInsertedLiveData
        }
    private val isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedIn : LiveData<Boolean>
        get() {
            return isLoggedInLiveData
        }
    private val userListLiveData = MutableLiveData<List<UserDetail>>()
    val userList : LiveData<List<UserDetail>>
        get() {
            return userListLiveData
        }

    fun signUpUser(context: Context, entity: UserEntity) {
        coroutineScope.launch {
             if (UserDatabase.getInstance(context).getUserDetailDao().insert(entity) > 0L) {
                 isInsertedLiveData.postValue(true)
             } else isInsertedLiveData.postValue(false)

        }
    }

    fun loginUser(context: Context, userName: String, password: String) {
        coroutineScope.launch {
            val userInfo =  UserDatabase.getInstance(context).getUserDetailDao().getUser(userName, password)
            if (userInfo != null) {
                isLoggedInLiveData.postValue(true)
            } else isLoggedInLiveData.postValue(false)
        }
    }

    fun getUserList(context: Context) {
        coroutineScope.launch {
            val userList = UserDatabase.getInstance(context).getUserDetailDao().getUserList()
            userListLiveData.postValue(userList)
        }
    }

    fun saveUser(context: Context, entity : UserDetail) {
        coroutineScope.launch {
            if (UserDatabase.getInstance(context).getUserDetailDao().saveUser(entity) > 0L) {
                isInsertedLiveData.postValue(true)
            } else isInsertedLiveData.postValue(false)
        }
    }
}