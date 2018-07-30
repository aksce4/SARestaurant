package com.sa.restaurant.appview.restaurant.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.restaurant.R
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list.view.*


class FavoriteListAdapter(var items: List<FavoriteRestaurantTable>, var context: Context, var userDatabase: MyDatabase) : RecyclerView.Adapter<FavoriteListAdapter.CustomViewHolder>(){

    val TAG = "FavoriteListAdapter"
    lateinit var favoriteRestaurantTable: FavoriteRestaurantTable
    lateinit var photoReference: String
    lateinit var imgUrl: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListAdapter.CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        favoriteRestaurantTable = FavoriteRestaurantTable()
        Log.d(TAG, "onBindViewHolder: called/n");
        holder.restaurantAddress.text = items[holder.adapterPosition].restaurantAddress
        holder.restaurantName.text = items[holder.adapterPosition].restaurantName
        imgUrl = items[holder.adapterPosition].restaurantImagePath
        Picasso.get().load(imgUrl)
                .into(holder.restaurantImage)
        if(items[holder.adapterPosition].isFavorite!!){
            holder.favoriteButton.setBackgroundResource(R.drawable.ic_restaurant_fav)
        }

        holder.favoriteButton.setOnClickListener {

            var adapterPos = items[holder.adapterPosition].adapterPosition
            items = removeItem(adapterPos)
            notifyItemRemoved(holder.adapterPosition)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun removeItem(position: Int): ArrayList<FavoriteRestaurantTable> {

        Log.d(TAG, "removeItem: $position");

        userDatabase.favoriteRestaurantDao().removeItem(position)
        return userDatabase.favoriteRestaurantDao().getAll() as ArrayList<FavoriteRestaurantTable>
    }

    inner class CustomViewHolder(view : View): RecyclerView.ViewHolder(view) {
        var restaurantName = view.txt_restaurant_name
        var restaurantAddress = view.txt_restaurant_address
        var favoriteButton = view.toggleButton
        var restaurantImage = view.img_restaurant
    }

}

