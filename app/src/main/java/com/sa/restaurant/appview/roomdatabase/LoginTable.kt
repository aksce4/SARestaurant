package com.sa.restaurant.appview.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Logintable")
 class LoginTable{

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "Name")
    var name:String? = null

    @ColumnInfo(name = "Username")
    var username: String? = null

    @ColumnInfo(name = "Password")
    var password: String? = null

    @ColumnInfo(name = "Email")
    var email: String? = null



 }