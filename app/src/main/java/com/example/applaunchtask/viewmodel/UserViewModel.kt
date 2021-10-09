package com.example.applaunchtask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.applaunchtask.model.Daily
import com.example.applaunchtask.repository.UserRepository
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.roomdatabase.UserEntity

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var context : Application = application

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
    val weather : LiveData<List<Daily>>
        get() {
            return repo.weather
        }

    fun signUpUser(entity: UserEntity) {
        repo.signUpUser(context, entity)
    }

    fun loginUser(userName: String, password: String) {
        repo.loginUser(context, userName, password)
    }

    fun getUserList() {
        repo.getUserList(context)
    }

    fun saveUser(entity: UserDetail) {
        repo.saveUser(context, entity)
    }

    fun getWeatherData() {
        repo.getWeatherData(context)
    }

    fun deleteUserData(userDetail: UserDetail) {
        repo.deleteUserData(context, userDetail)
    }

}