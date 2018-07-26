package com.sa.restaurant.appview.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(LoginTable::class), version = 2)
abstract class MyDatabase : RoomDatabase(){

    abstract fun logindao(): LoginDao
}