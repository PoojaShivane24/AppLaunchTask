package com.example.applaunchtask.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetail(@PrimaryKey val userName: String, val lastName: String, val email : String)