package com.sa.restaurant.appview.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable

@Dao
interface FavoriteRestaurantDao {

    @Insert
    fun insertItem(favoriteRestaurantTable: FavoriteRestaurantTable)

    @Query("DELETE from FavoriteRestaurant WHERE adapter_position = :position")
    fun removeItem(position: Int)

    @Update
    fun updateData(favoriteRestaurantTable: FavoriteRestaurantTable)

    @Query("SELECT * FROM FavoriteRestaurant")
    fun getAll(): List<FavoriteRestaurantTable>
}