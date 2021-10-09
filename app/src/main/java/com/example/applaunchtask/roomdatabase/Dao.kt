package com.example.applaunchtask.roomdatabase

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert
    fun insert(entity: UserEntity) : Long

    @Query("Select * from UserEntity where userName = :userName AND password = :password")
    fun getUser(userName : String, password : String) : UserEntity?

    @Query("Select * from UserDetail")
    fun getUserList() : List<UserDetail>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUser(entity : UserDetail) : Long

    @Delete
    fun deleteUser(userDetail: UserDetail)
}