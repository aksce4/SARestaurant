package com.sa.restaurant.appview.restaurant

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.sa.restaurant.R
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.adapter.RestaurantAdapter
import com.sa.restaurant.appview.restaurant.model.RestaurantDetails
import com.sa.restaurant.appview.restaurant.presenter.RestaurantPresenter
import com.sa.restaurant.appview.restaurant.presenter.RestaurantPresenterImpl
import kotlinx.android.synthetic.main.fragment_restaurant.*

class RestaurantFragment: Fragment(){

    lateinit var homeActivity: HomeActivity
    lateinit var contextRestFrag: Context
    lateinit var googleApiServices: GoogleApiServices
    var googleApiClient: GoogleApiClient? = null
    lateinit var restaurantAdapter: RestaurantAdapter
    var list: ArrayList<RestaurantDetails> = ArrayList()

    lateinit var locationreq: LocationRequest
    lateinit var locationcallback: LocationCallback


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        contextRestFrag = context!!
        homeActivity = context as HomeActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var restaurantPresenter: RestaurantPresenter = RestaurantPresenterImpl()
        googleApiClient = restaurantPresenter.createClient(homeActivity)
        googleApiClient!!.connect()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            restaurantPresenter.checklocationpermission(homeActivity!!)
        }

        val viewGroup = (homeActivity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        recyclerview.layoutManager = LinearLayoutManager(homeActivity, LinearLayout.VERTICAL, false)
        restaurantAdapter = RestaurantAdapter(homeActivity, list)
        recyclerview.adapter = restaurantAdapter


        //Getting Current Location
        googleApiServices = RetrofitnearbyClient.getClient("https://maps.google.com/").create(GoogleApiServices::class.java)
        locationreq = restaurantPresenter.BuildLocationreq()
        locationcallback = restaurantPresenter.BuildLocationCallback(googleApiServices, viewGroup, homeActivity, restaurantAdapter)

      //  fragment_restaurant_swiperefreshlayout.setOnRefreshListener()

    }

}