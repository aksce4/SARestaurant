package com.sa.restaurant.appview.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sa.restaurant.appview.roomdatabase.dao.FavoriteRestaurantDao
import com.sa.restaurant.appview.roomdatabase.dao.LoginDao
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable
import com.sa.restaurant.appview.roomdatabase.model.LoginTable


@Database(entities = arrayOf(LoginTable::class, FavoriteRestaurantTable::class), version = 1)
abstract class MyDatabase : RoomDatabase(){

    abstract fun logindao(): LoginDao

    abstract fun favoriteRestaurantDao(): FavoriteRestaurantDao
}