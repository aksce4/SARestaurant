package com.sa.restaurant.appview.roomdatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sa.restaurant.R.string.email
import com.sa.restaurant.R.string.username

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