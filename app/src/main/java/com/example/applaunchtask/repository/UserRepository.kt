package com.example.applaunchtask.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.applaunchtask.model.Daily
import com.example.applaunchtask.model.Weather
import com.example.applaunchtask.retrofit.ApiClient
import com.example.applaunchtask.retrofit.ApiInterface
import com.example.applaunchtask.roomdatabase.UserDatabase
import com.example.applaunchtask.roomdatabase.UserDetail
import com.example.applaunchtask.roomdatabase.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private var dataList: MutableList<Daily> = ArrayList()
    private val weatherLiveData = MutableLiveData<List<Daily>>()
    val weather : LiveData<List<Daily>>
        get() {
            return weatherLiveData
        }

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

    fun getWeatherData(context: Context) {
        val apiInterface = ApiClient.getInstance().create(ApiInterface::class.java)
//        val call = apiInterface.getWeatherData(Config.LAT, Config.LON, Config.UNITS, Config.APP_ID)
        val call = apiInterface.getWeatherData()
        call.enqueue(object : Callback<Weather>{
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                Log.e("TAG", "onResponse: url "+call.request() )
                Log.e("TAG", "onResponse: body "+response.body() )
                val weather = response.body()
                val weatherList = weather?.daily
                if (weatherList != null) {
                    for (i in 0..2) {
                        val data = weatherList[i]
                        dataList.add(data)
                        weatherLiveData.postValue(dataList)
                    }

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.e("TAG", "onFailure: url "+call.request() )

                Log.e("TAG", "onFailure: "+t.message )
            }

        })
    }

    fun deleteUserData(context: Context, userDetail: UserDetail) {
        coroutineScope.launch {
            UserDatabase.getInstance(context).getUserDetailDao().deleteUser(userDetail)
        }
    }
}