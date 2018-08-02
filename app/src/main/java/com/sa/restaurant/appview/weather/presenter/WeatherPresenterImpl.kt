package com.sa.restaurant.appview.weather.presenter

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.appview.map.presenter.MapPresenterImpl
import com.sa.restaurant.appview.weather.WeatherApiClient
import com.sa.restaurant.appview.weather.WeatherFragment
import com.sa.restaurant.appview.weather.model.City
import com.sa.restaurant.appview.weather.model.ListItem
import com.sa.restaurant.appview.weather.model.Weathers
import com.sa.restaurant.appview.weather.view.WeatherView
import retrofit2.Call
import retrofit2.Response
import java.util.*

class WeatherPresenterImpl: WeatherPresenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var latLng: LatLng? = null
    var location: Location? = null
    var result: Weathers = Weathers()
    val TAG: String = "WeatherPresenterImpl"


    override fun BuildLocationreq(): LocationRequest {
        locationRequest = LocationRequest()
        locationRequest.interval = 60000 * 60
        Log.e(TAG, "is Connected")
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        return  locationRequest
    }

    override fun Buildlocationcallback(weatherApiClient: WeatherApiClient, activity: Context, view: View): LocationCallback {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                location = locationResult!!.locations[locationResult.locations.size - 1]
                latLng = LatLng(location!!.latitude, location!!.longitude)

//                var weatherView: WeatherView = WeatherFragment()
//                weatherView.sendlocation(location!!, activity, weatherApiClient, view)
                Log.e(TAG, "Location : ${location!!.latitude} ${location!!.longitude}")
            }

        }
        return locationCallback
    }

    override fun createClient(context: Context): GoogleApiClient {
        Log.e(TAG, "In createClient")
        var googleApiClient: GoogleApiClient
        synchronized(this){
            googleApiClient = GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
            googleApiClient.connect()
            return googleApiClient
        }
    }

//    override fun getNameFromLatLng(location: Location, context: Context, weatherApiClient: WeatherApiClient, view: View) {
//        var geocoder: Geocoder = Geocoder(context, Locale.getDefault())
//        var address: List<Address>
//
//        address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//        Log.e(TAG, "Address : ${address.toString()}")
//
////        val addres = address[0].getAddressLine(0)
////        val state = address[0].adminArea
////        val country = address[0].countryName
////        val postalcode = address[0].postalCode
//        val city = address[0].locality
//        val knownName = address[0].featureName
//
//        Log.e(TAG, "Address : ${city} ${knownName}")
//
//        var bundle: Bundle = Bundle()
//        bundle.putString("city", city)
//        bundle.putString("place", knownName)
//
//        getWeatherInfo(context, bundle, weatherApiClient, view)
//
//    }

    override fun getWeatherInfo(context: Context, bundle: Bundle, weatherApiClient: WeatherApiClient, view: View) {
        val url = getWeatherUrl(bundle)
        Log.e(TAG, "In $url")
        weatherApiClient.getWeatherInfo(url).enqueue(object : retrofit2.Callback<Weathers>{
            override fun onFailure(call: Call<Weathers>?, t: Throwable?) {
               Log.e(TAG, "Failed")
            }

            override fun onResponse(call: Call<Weathers>?, response: Response<Weathers>?) {
                result = response!!.body()!!
                Log.e(TAG, "Response: ${result.toString()}")

                if (response!!.body()!! != null){
                    var city = result.city!!.name
                    var country = result.city!!.country
                    var temp = result.list!![1].main.temp.minus(273.15).toFloat()
                    var sky = result.list!![0].weather!![0].description
                    var  mintemp = result.list!![0].main.tempMin.toFloat()
                    var maxtemp = result.list!![1].main.tempMax.toFloat()
                    var icon = result.list!![0].weather!![0].icon

                    var bundle: Bundle = Bundle()
                    bundle.putString("city", city)
                    bundle.putString("country", country)
                    bundle.putString("temp", temp.toString())
                    bundle.putString("sky", sky)
                    bundle.putString("mintemp", mintemp.toString())
                    bundle.putString("maxtemp", maxtemp.toString())
                    bundle.putString("icon", icon)

                    Log.e(TAG, "bundle : ${bundle.toString()}")

                    var weatherView: WeatherView = WeatherFragment()
                    weatherView.sendweatherInfo(bundle, context, view)
                }else{
                    Log.e(TAG, "List Not Found - Try Again")

                }
            }

        })
    }

    fun getWeatherUrl(bundle: Bundle): String {
//        var city = bundle["city"]
//        var placename = bundle["place"]
        var weatherurl: StringBuilder = StringBuilder("http://api.openweathermap.org/data/2.5/forecast?id=1279233&APPID=6cc53e9295eaa5952fbf593861622763")
        return weatherurl.toString()
    }

    override fun onConnected(p0: Bundle?) {
        Log.e(TAG, "success")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e(TAG, "failed")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(TAG, "failed")
    }

}