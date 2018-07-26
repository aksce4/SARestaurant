package com.sa.restaurant.appview.map

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.sa.restaurant.R
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*

class MapFragment: Fragment(), OnMapReadyCallback {

    val TAG: String = "MapActivity"
    lateinit var mMap: GoogleMap
    val FINE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    val COARSE_LOCATION: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    var mLocationGranted: Boolean = false
    val LOCATION_PERMISSION_REQUEST_CODE: Int = 321
    val DEFAULT_ZOOM:Float = 15f

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getLocationPermission()

        fragment_map_img_gps.setOnClickListener {
            Log.d(TAG, "onClick: clicked gps location")
            getDeviceLocation()
        }

        fragment_map_btn_changemap.setOnClickListener {
            Log.d(TAG, "onClick: Clicked the change map type")
            chngeMapType()
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        Toast.makeText(activity,"Map is Ready", Toast.LENGTH_SHORT).show()

        try{
            var success: Boolean = googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))

            if(!success){
                Log.e(TAG, "style parsing failed")
            }
        }catch (e: Resources.NotFoundException){
            Log.e(TAG, "can't find style. Error $e")
        }

        mMap = googleMap!!


        Log.d(TAG,"onMapReady: Map is ready")

        if (mLocationGranted){
            getDeviceLocation()

            if(ActivityCompat.checkSelfPermission(activity!!, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity!!,COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return
            }

            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false

        }
    }

    fun rand(a: Int, b: Int) = Random().nextInt(b + 1 - a) + a

    private fun chngeMapType() {
        when (rand(0, 4)) {
            0 -> mMap.mapType = GoogleMap.MAP_TYPE_NONE
            1 -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            2 -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            3 -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            4 -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            else -> Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_LONG).show()
        }
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission : here")
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
        Log.d(TAG, "initMap: initializing Map")
        // var mapFrag: SupportMapFragment =
        
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

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String){
        Log.d(TAG, "movecamera: Moving the camera to: lat: ${latLng.latitude} , lag: ${latLng.longitude}")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))

        var c_icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)

        if (!title.equals("My Location")){
            var options: MarkerOptions = MarkerOptions().position(latLng).title(title).icon(c_icon)
            mMap.addMarker(options)
        }

        //    hideSoftKeyboard()


    }
}