package com.sa.restaurant.appview.roomdatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(LoginTable::class), version = 1)
abstract class MyDatabase : RoomDatabase(){

    abstract fun logindao(): LoginDao
}