package com.sa.restaurant.appview.roomdatabase

import android.arch.persistence.room.*

@Dao
interface LoginDao {

    @Insert
    fun addUser(loginTable: LoginTable)

    @Delete
    fun deleteUser(loginTable: LoginTable)

    @Update
    fun updateUser(loginTable: LoginTable)

    @Query("Select * from Logintable")
    fun getUser(): List<LoginTable>

    @Query("Select * from Logintable where Username=:username and Password=:password")
    fun userLogin(username: String, password: String): List<LoginTable>
}