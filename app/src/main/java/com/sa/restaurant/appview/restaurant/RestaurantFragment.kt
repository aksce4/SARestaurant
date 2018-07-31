package com.sa.restaurant.appview.restaurant

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.R
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.adapter.RestaurantListAdapter
import com.sa.restaurant.appview.restaurant.model.ResponseModelClass
import com.sa.restaurant.appview.restaurant.presenter.LocationData
import com.sa.restaurant.appview.restaurant.presenter.LocationDataImpl
import com.sa.restaurant.appview.restaurant.presenter.RestaurantPresenterImp
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import kotlinx.android.synthetic.main.fragment_restaurant.*
import restaurant.sa.com.sarestaurant.appview.restaurant.GooglePlacesClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestaurantFragment : Fragment(){

    val TAG = "RestaurantFragment"
    lateinit var homeActivity: HomeActivity
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var userDataBase: MyDatabase

    var granted = false
    lateinit var listOfPlacesLocation: ArrayList<LatLng>
    lateinit var restaurantPresenterImp: RestaurantPresenterImp
    lateinit var locationCommunication: LocationData
    lateinit var contextRestFrag: Context
    val result_type = "restaurant"
    val radius = 2500
    val sensor = true
    var currentLocation: Location? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        contextRestFrag = context!!
        homeActivity = context as HomeActivity
        homeActivity.supportActionBar?.show()
        locationCommunication = homeActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        restaurantPresenterImp = RestaurantPresenterImp()
        granted = true

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(homeActivity)
        var getLocation = LocationDataImpl(granted, mFusedLocationProviderClient, contextRestFrag)
        getLocation.sendLocation(object: LocationData.OnReceiveLocation{
            override fun getDeviceLastLocation(location: Location) {
                Log.e(TAG, "getDeviceLastLocation: $location Mil");
                currentLocation = location
                retrofitCall(location)
            }

            override fun receiveLocationUpdatesFun() {

            }

            override fun onError() {

            }

        })

        fragment_restaurant_swiperefreshlayout.setOnRefreshListener {
            Handler().postDelayed({
                fragment_restaurant_swiperefreshlayout.setRefreshing(false);

                retrofitCall(currentLocation)

                Log.e(TAG, "onActivityCreated: $currentLocation")
            }, 3000)
        }

        userDataBase = Room.databaseBuilder(homeActivity, MyDatabase::class.java, "AppData")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()
    }

    fun retrofitCall(location: Location?){
        var builder = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())

        var retrofit: Retrofit = builder.build()

        var client: GooglePlacesClient = retrofit.create(GooglePlacesClient::class.java)

        var call = client.sendRequestForPlaces("${location?.latitude},${location?.longitude}", radius.toString(), result_type, sensor.toString(), resources.getString(R.string.google_map_API_key))
        call.enqueue(object : Callback<ResponseModelClass> {
            override fun onFailure(call: Call<ResponseModelClass>?, t: Throwable?) {
                Log.e(TAG, "onFailure: $t");
            }

            override fun onResponse(call: Call<ResponseModelClass>?, responseModelClass: Response<ResponseModelClass>?) {
                Log.d(TAG, "onResponse: ${responseModelClass!!.body()}")
                val layout = LinearLayoutManager(activity)
                listOfPlacesLocation = restaurantPresenterImp.getListOfLocations(responseModelClass.body()!!)
                locationCommunication.sendLocationFromRestaurant(listOfPlacesLocation)
                recyclerview.adapter = RestaurantListAdapter(responseModelClass.body()!!, userDataBase.favoriteRestaurantDao().getAll(), homeActivity, userDataBase)
                recyclerview.layoutManager = layout
            }
        })
    }

}
