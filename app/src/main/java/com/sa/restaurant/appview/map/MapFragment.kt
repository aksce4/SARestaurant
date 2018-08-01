package com.sa.restaurant.appview.map

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.sa.restaurant.R
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.presenter.LocationCommunication
import com.sa.restaurant.appview.restaurant.presenter.LocationData
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment: Fragment(),  OnMapReadyCallback{


    val FINE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    val COARSE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    val TAG: String = "MapFragment"
    val LOCATION_PERMISSION_REQUEST_CODE: Int = 321
    var mLocationGranted: Boolean = false
    lateinit var mMap: GoogleMap
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val DEFAULT_ZOOM:Float = 15f
    lateinit var f_View: View
    var m_View: MapView? = null
    var listOfLocations: ArrayList<LatLng> = ArrayList()
    lateinit var locationData: LocationCommunication
    lateinit var homeActivity: HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        f_View =  inflater.inflate(R.layout.fragment_map, container, false)
        m_View = f_View.findViewById(R.id.fragment_map)
        return f_View
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listOfLocations = locationData.getLocationFromRestaurant()
        getLocationPermission()

        fragment_map_btn_changemap.setOnClickListener {
            Log.e(TAG, "onClick: Clicked the change map type")
            changeMapType()
        }

        fragment_map_img_gps.setOnClickListener {
            Log.e(TAG, "onClick: clicked gps location")
            getDeviceLocation()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
        locationData = homeActivity
    }

    private fun changeMapType() {
        fun rand(a: Int, b: Int) = Random().nextInt(b + 1 - a) + a

        when (rand(0, 4)) {
            0 -> mMap.mapType = GoogleMap.MAP_TYPE_NONE
            1 -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            2 -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            3 -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            4 -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            else -> Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        Toast.makeText(activity,"Map is Ready", Toast.LENGTH_SHORT).show()

        try{
            var success: Boolean = p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.style_json))

            if(!success){
                Log.e(TAG, "style parsing failed")
            }
        }catch (e: Resources.NotFoundException){
            Log.e(TAG, "can't find style. Error $e")
        }

        Log.e(TAG,"onMapReady: Map is ready")

        if (mLocationGranted){
            getDeviceLocation()

            if(ActivityCompat.checkSelfPermission(activity!!, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity!!,COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return
            }

            mMap.isMyLocationEnabled = true
            mMap.uiSettings?.isMyLocationButtonEnabled = false

            restaurantMarkers()

        }

    }

    private fun restaurantMarkers() {
        var restaurant_icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_marker)
        var option = MarkerOptions()

        if(listOfLocations.isEmpty()){
            for (i in listOfLocations){
                option.position(i).icon(restaurant_icon)
                mMap.addMarker(option)
            }
        }else{
            Toast.makeText(this.activity, "Empty..!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDeviceLocation() {
        Log.d(TAG, "getDevicelocation: getting the device location")

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

        try {
            if (mLocationGranted) {
                var location = mFusedLocationProviderClient.lastLocation as com.google.android.gms.tasks.Task
                location.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getDeviceLocation: Found Location")
                        var currentLocation = it.result as Location
                        //currentLocation = it.result
                        moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), DEFAULT_ZOOM, "My Location")
                    } else {
                        Log.d(TAG, "OnComplete: Current loation is null")
                        Toast.makeText(activity, "Unable to get current location", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: SecurityException ${e.message}")
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.e(TAG, "movecamera: Moving the camera to: lat: ${latLng.latitude} , lag: ${latLng.longitude}")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))

        var c_icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_current_marker)
        var options: MarkerOptions = MarkerOptions().position(latLng).title(title).icon(c_icon)
        mMap.addMarker(options)



    }

    private fun getLocationPermission() {
        Log.e(TAG, "getLocationPermission : here")
        var permissions = Array<String>(2) {
            android.Manifest.permission.ACCESS_FINE_LOCATION;
            android.Manifest.permission.ACCESS_FINE_LOCATION
        }

        if (ContextCompat.checkSelfPermission(activity!!.applicationContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationGranted = true
                initMap()
            } else {
                ActivityCompat.requestPermissions(activity!!, permissions, LOCATION_PERMISSION_REQUEST_CODE)
            }

        } else {
            ActivityCompat.requestPermissions(activity!!, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun initMap() {
        Log.e(TAG, "initMap: initializing Map")
        m_View?.onCreate(null)
        m_View?.onResume()
        m_View?.getMapAsync(this)
    }


}