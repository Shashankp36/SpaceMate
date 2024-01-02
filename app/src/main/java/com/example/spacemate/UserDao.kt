package com.example.spacemate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
     fun insertUser(user: User)
    @Update
    fun update(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
     fun getUserByEmail(email: String): User?
}