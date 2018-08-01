package com.sa.restaurant.appview.weather.presenter

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.sa.restaurant.appview.weather.model.Weathers
import com.sa.restaurant.appview.weather.view.WeatherView
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
            }

        }
        return locationCallback
    }

    override fun createClient(context: Context): GoogleApiClient {
        var googleApiClient: GoogleApiClient
        synchronized(this){
            googleApiClient = GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
            googleApiClient.connect()
            return googleApiClient
        }
    }

    override fun getNameFromLatLng(location: Location, context: Context, weatherApiClient: WeatherApiClient, view: View) {
        var geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        var address: List<Address>

        address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        val addres = address[0].getAddressLine(0)
        val city = address[0].locality
        val state = address[0].adminArea
        val country = address[0].countryName
        val postalcode = address[0].postalCode
        val knownName = address[0].featureName

        var bundle: Bundle = Bundle()
        bundle.putString("city", city)
        bundle.putString("place", knownName)

        getWeatherInfo(context, bundle, weatherApiClient, view)

    }

    fun getWeatherInfo(context: Context, bundle: Bundle, weatherApiClient: WeatherApiClient, view: View) {
       val url = getWeatherUrl(bundle)
    }

    fun getWeatherUrl(bundle: Bundle): String {
        var city = bundle["city"]
        var placename = bundle["place"]
        var weatherurl: StringBuilder = StringBuilder()
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