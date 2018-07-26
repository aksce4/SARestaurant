package com.sa.restaurant.appview.roomdatabase

import androidx.room.*


@Dao
interface LoginDao {

    @Insert
    fun insertUser(loginTable: LoginTable)

    @Delete
    fun deleteUser(loginTable: LoginTable)

    @Update
    fun updateUser(loginTable: LoginTable)

    @Query("Select * from Logintable")
    fun getUser(): List<LoginTable>

    @Query("Select * from Logintable where Username=:username or Email=:email")
    fun checkUser(username: String, email: String): List<LoginTable>

    @Query("Select * from Logintable where Username=:username and Password=:password")
    fun userLogin(username: String, password: String): List<LoginTable>
}