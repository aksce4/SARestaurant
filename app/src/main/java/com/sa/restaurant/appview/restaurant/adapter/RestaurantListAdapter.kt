package com.sa.restaurant.appview.restaurant.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.share.widget.ShareDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.sa.restaurant.R
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.model.ResponseModelClass
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list.view.*


class RestaurantListAdapter(var items: ResponseModelClass, var favItems: List<FavoriteRestaurantTable>, var context: Context, var userDatabase: MyDatabase) : RecyclerView.Adapter<RestaurantListAdapter.CustomViewHolder>(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    val TAG = "RestaurantListAdapter"
    lateinit var favoriteRestaurantTable: FavoriteRestaurantTable

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
        Log.e(TAG, "onConnected: YES");
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var gclient = GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        gclient.connect()
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.results!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val shareDialog = ShareDialog(context as HomeActivity)
        favoriteRestaurantTable = FavoriteRestaurantTable()
        holder.restaurantAddress.text = items.results!![holder.adapterPosition].vicinity
        holder.restaurantName.text = items.results!![holder.adapterPosition].name
        if (items.results!![holder.adapterPosition].photos != null){
            val photoReference = items.results!![holder.adapterPosition].photos!![0].photoReference
            holder.restaurantImgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&sensor=false&key=${context.resources.getString(R.string.google_map_API_key)}"
        }else{
            holder.restaurantImgUrl = "http://2016.foodhawkers.co.uk/list-traders-profile.php?profileID=2344"
        }
        Picasso.get().load(holder.restaurantImgUrl)
                .into(holder.restaurantImage)

        for (i in favItems){
            if(items.results!![holder.adapterPosition].name == i.restaurantName
                    && items.results!![holder.adapterPosition].vicinity == i.restaurantAddress){
                if(i.isFavorite!!){
                    holder.favoriteButton.setBackgroundResource(R.drawable.ic_restaurant_fav)
                }
            }
        }


        holder.favoriteButton.setOnClickListener {

            var isFavorite = holder.favoriteButton.isChecked
            favoriteRestaurantTable.adapterPosition = holder.adapterPosition
            favoriteRestaurantTable.restaurantName = items.results!![holder.adapterPosition].name!!
            favoriteRestaurantTable.restaurantAddress = items.results!![holder.adapterPosition].vicinity!!
            favoriteRestaurantTable.restaurantImagePath = holder.restaurantImgUrl

            favoriteRestaurantTable.latitude = items.results!![holder.adapterPosition].geometry!!.location!!.lat
            favoriteRestaurantTable.longitude = items.results!![holder.adapterPosition].geometry!!.location!!.lng
            favoriteRestaurantTable.isFavorite = isFavorite

            if(isFavorite){
                holder.favoriteButton.setBackgroundResource(R.drawable.ic_restaurant_fav)
                addItem(favoriteRestaurantTable)
            }else{
                holder.favoriteButton.setBackgroundResource(R.drawable.ic_restaurant_fav_off)
                removeItem(holder.adapterPosition)
            }

        }
    }

    private fun removeItem(position: Int) {

        Log.d(TAG, "removeItem: $favoriteRestaurantTable");

        userDatabase.favoriteRestaurantDao().removeItem(position)
    }

    private fun addItem(favoriteRestaurantTable: FavoriteRestaurantTable) {
        Log.d(TAG, "addItem: $favoriteRestaurantTable")
        userDatabase.favoriteRestaurantDao().insertItem(favoriteRestaurantTable)
        Log.d(TAG, "addItem: ${userDatabase.favoriteRestaurantDao().getAll()}")
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class CustomViewHolder(view : View): RecyclerView.ViewHolder(view) {
        var restaurantImgUrl:String = ""
        var restaurantName = view.txt_restaurant_name
        var restaurantAddress = view.txt_restaurant_address
        var favoriteButton = view.toggleButton
        var restaurantImage = view.img_restaurant
       // var sharePost = view.sharePost
    }

}



