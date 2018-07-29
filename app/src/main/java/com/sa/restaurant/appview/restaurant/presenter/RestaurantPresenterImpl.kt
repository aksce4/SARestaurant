package com.sa.restaurant.appview.restaurant.presenter

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.GoogleApiServices
import com.sa.restaurant.appview.restaurant.adapter.RestaurantAdapter
import com.sa.restaurant.appview.restaurant.model.Photo
import com.sa.restaurant.appview.restaurant.model.Pojo
import com.sa.restaurant.appview.restaurant.model.RestaurantDetails
import com.sa.restaurant.appview.restaurant.view.RestaurantView
import retrofit2.Call
import retrofit2.Response

class RestaurantPresenterImpl: RestaurantPresenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    val TAG: String = "RestaurantPresenterImpl Class"
    lateinit var googleApiServices: GoogleApiServices
    lateinit var locationReq: LocationRequest
    lateinit var locationCallback: LocationCallback
    var pojo: Pojo = Pojo()
    var list: ArrayList<RestaurantDetails> = ArrayList()

    companion object {
        var hashMap:HashMap<String,Location> = HashMap()
        lateinit var gclient: GoogleApiClient
    }

    //Connection Failed Listener Methods
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e("TAG", "onConnectionFailed Called")
    }

    //Connection Callbacks Methods
    override fun onConnected(p0: Bundle?) {
        Log.e("TAG", "onConnected Called")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e("TAG", "onConnectionSuspended Called")
    }

    //Restaurant Presenter Methods
    override fun BuildLocationreq(): LocationRequest {
        locationReq = LocationRequest()
        locationReq.interval = 60000
        locationReq.fastestInterval = 5000
        locationReq.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        return locationReq
    }

//    override fun BuildLocationCallback(googleApiServices: GoogleApiServices, context: ViewGroup, activity: Context, adapter: RestaurantAdapter): LocationCallback {
//        locationCallback = object : LocationCallback(){
//
//        }
//    }

    override fun checklocationpermission(context: Activity): Boolean {
        return if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 99)
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 99)
            }
            false

        } else
            true

    }

    override fun createClient(context: Context): GoogleApiClient {
        synchronized(this){
            gclient = GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
            gclient.connect()
            return gclient
        }
    }

    //Get url
    fun geturl(lat: Double, lng: Double, nearplace: String): String{
        var placeurl: StringBuilder = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        placeurl.append("location=" + lat + "," + lng)
        placeurl.append("&radius=" + 1000)
        placeurl.append("&type=" + nearplace)
        placeurl.append("&sensor=true")
        placeurl.append("&key=" + "AIzaSyBhlgT3QHNx8NzE9yvK4kVQsSyaahXQja8")

        return  placeurl.toString()
    }

    fun nearplace(context: Context, typeplace: String, location: Location, googleApiServices: GoogleApiServices, restaurantAdapter: RestaurantAdapter){

        val url = geturl(location.latitude, location.longitude, typeplace)

        googleApiServices.getnearbyrestaurants(url).enqueue(object : retrofit2.Callback<Pojo>{

            override fun onFailure(call: Call<Pojo>?, t: Throwable?) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Pojo>?, response: Response<Pojo>?) {
                var photo: List<Photo> = ArrayList()
                var photoref: String
                pojo = response!!.body()!!

                if (response!!.body()!! != null){

                    for (i in 0 until response.body()!!.result!!.size){
                        val googleplace = response.body()!!.result!![i]
                        val address= response.body()!!.result!![i].vicinity
                        val placename = googleplace.name
                        val lat= googleplace.geometry.location.latitude
                        val lng= googleplace.geometry.location.longitude
                        var loc:Location= Location("test")
                        loc.latitude=lat
                        loc.longitude=lng
                        hashMap[placename]=loc

                        if (googleplace.photos == null){
                            photoref = "CmRaAAAARs96HXjLZFkFS1Nzb2FfsTnesaYVp-lGptxA3o-rLDlNgZJqjpse57PIB42_tUQnErkBkuWEcJMTSKBScC5eYrzLA3s4Pt8MihxpMD3gLi_7zOxD9i2-fxxOp7v9fs_pEhC7cZWc4cvi5UmJO1_IyOYsGhR2X0rUzKq54WzXiAsdUVFZwBQpHw"
                        }else{
                            photo = googleplace.photos.toList()
                            photoref = photo[0].ref_photo
                        }

                        var restaurantData:RestaurantDetails= RestaurantDetails()
                        restaurantData.Name=placename
                        restaurantData.Address=address
                        restaurantData.image=photoref
                        restaurantData.lat=lat
                        restaurantData.lng= lng
                        list.add(restaurantData)
                        HomeActivity.mcount = 0
                    }

                    var restaurantView:RestaurantView = HomeActivity()
                    restaurantView.restaurnatlist(list, context, restaurantAdapter)
                }else{
                    Log.i("List Not Found","Try again")
                }



            }

        })
    }

}