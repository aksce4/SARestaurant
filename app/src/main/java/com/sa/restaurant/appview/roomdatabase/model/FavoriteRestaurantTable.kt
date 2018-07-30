package com.sa.restaurant.appview.roomdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteRestaurant")
data class FavoriteRestaurantTable(@PrimaryKey(autoGenerate = true) var _id: Long?,
                                   @ColumnInfo(name = "adapter_position") var adapterPosition: Int,
                                   @ColumnInfo(name = "restaurant_name") var restaurantName: String,
                                   @ColumnInfo(name = "restaurant_address") var restaurantAddress: String,
                                   @ColumnInfo(name = "restaurant_img_path") var restaurantImagePath: String,
                                   @ColumnInfo(name = "is_favorite") var isFavorite: Boolean?,
                                   @ColumnInfo(name = "latitude") var latitude: Double?,
                                   @ColumnInfo(name = "longitude") var longitude: Double?
){
    constructor():this(null,0, "", "", "", null, null, null)
}