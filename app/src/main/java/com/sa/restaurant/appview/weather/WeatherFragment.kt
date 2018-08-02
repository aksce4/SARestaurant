package com.sa.restaurant.appview.weather

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.sa.restaurant.R
import com.sa.restaurant.appview.weather.presenter.WeatherPresenter
import com.sa.restaurant.appview.weather.presenter.WeatherPresenterImpl
import com.sa.restaurant.appview.weather.view.WeatherView
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Retrofit

class WeatherFragment: Fragment(), WeatherView{

    lateinit var weatherApiClient: WeatherApiClient
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var passed_view: View

//    companion object {
//        lateinit var dialog: ProgressDialog
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View =  inflater.inflate(R.layout.fragment_weather, container, false)
        passed_view = view

//        dialog = ProgressDialog(activity)
//        dialog.setMessage("Wait")
//        dialog.setCancelable(false)
//        dialog.isIndeterminate = true
//        dialog.show()

        var weatherPresenter: WeatherPresenter = WeatherPresenterImpl()
        weatherPresenter.createClient(activity!!)

        weatherApiClient = RetrofitnearbyClient.getClient("http://api.openweathermap.org").create(WeatherApiClient::class.java)

        locationRequest = weatherPresenter.BuildLocationreq()
        locationCallback = weatherPresenter.Buildlocationcallback(weatherApiClient, activity!!, passed_view)

        if(ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
        return  view
    }

    override fun sendlocation(location: Location, context: Context, weatherApiClient: WeatherApiClient, view: View) {
       var weatherPresenter: WeatherPresenter = WeatherPresenterImpl()
        weatherPresenter.getNameFromLatLng(location, context, weatherApiClient, view)
    }

    override fun sendweatherInfo(bundle: Bundle, context: Context, view: View) {

        var cityname = bundle["city"]
        var countryname = bundle["country"]
        var tempvalue = bundle["temp"]
        var skytype = bundle["sky"]
        var mintempvalue = bundle["mintemp"]
        var maxtempvalue = bundle["maxtemp"]

        fragment_weather_txt_city.text = cityname.toString() + " " + countryname.toString()
        fragment_weather_txt_temp.text = tempvalue.toString()
        fragment_weather_txt_skydesc.text = skytype.toString() + " " + mintempvalue.toString() + " - " + maxtempvalue.toString()


     //   dialog.dismiss()



    }


}