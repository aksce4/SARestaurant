package com.sa.restaurant.appview.restaurant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sa.restaurant.R
import com.sa.restaurant.appview.restaurant.model.RestaurantDetails

class RestaurantAdapter(var context: Context, var array: ArrayList<RestaurantDetails>): RecyclerView.Adapter<RestaurantAdapter.Vholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder{

        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list, parent, false)
        return Vholder(v)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {

    }

    inner class Vholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        override fun onLongClick(view: View?): Boolean {
           return true
        }

    }

}