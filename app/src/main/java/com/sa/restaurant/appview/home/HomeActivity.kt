package com.sa.restaurant.appview.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.R
import com.sa.restaurant.appview.MainActivity
import com.sa.restaurant.appview.location.GetLocation
import com.sa.restaurant.appview.location.GetLocationImpl
import com.sa.restaurant.appview.location.LocationCommunication
import com.sa.restaurant.appview.map.MapFragment
import com.sa.restaurant.appview.map.presenter.MapPresenterImpl
import com.sa.restaurant.appview.restaurant.FavoriteFragment
import com.sa.restaurant.appview.restaurant.RestaurantFragment
import com.sa.restaurant.utils.FragmentUtils
import com.sa.restaurant.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, LocationCommunication {

    var listOfLocations: java.util.ArrayList<LatLng> = java.util.ArrayList()
    lateinit var mapPresenterImpl: MapPresenterImpl
    var mapFragment:MapFragment = MapFragment()
    var permissionList = arrayListOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    val TAG = "HomeActivity"
    var fav: Boolean = false
    var restora: Boolean = false
    var weather: Boolean = false

    override fun sendLocationFromRestaurant(listOfLocations: java.util.ArrayList<LatLng>) {
        this.listOfLocations = listOfLocations
    }

    override fun getLocationFromRestaurant(): java.util.ArrayList<LatLng> {
        return listOfLocations
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        mapPresenterImpl = MapPresenterImpl()
        var permissionUtils = PermissionUtils(this)
        var granted = permissionUtils.checkPermissions(permissionList)

        if(!granted){
            permissionUtils.askForPermissions()
        }

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        var getLocation = GetLocationImpl(granted, mFusedLocationProviderClient, this)
        getLocation.sendLocation(object: GetLocation.OnReceiveLocation{
            override fun getDeviceLastLocation(location: Location) {
                Log.d(TAG, "getDeviceLastLocation: $location ");
            }

            override fun receiveLocationUpdatesFun() {

            }

            override fun onError() {

            }

        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        FragmentUtils.replaceFragment(supportFragmentManager, RestaurantFragment(), R.id.content_home_holder, this)
        //set user info into navigation bar
        var shared: SharedPreferences = this.getSharedPreferences("UserInfo", 0)
        var Name: String = shared.getString("name", "name")
     //   var Name = shared.getString("name","")
        var Email = shared.getString("email", "email")

        Log.d("TAG", "$Name $Email")

//        txt_header_name.setText(Name)
//        txt_header_email.setText(Email)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.restaurant, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.maps -> {
                supportActionBar!!.title = "Location"
                if (mapPresenterImpl.checkService(this)){
                    if(content_home_holder == null){
                        FragmentUtils.addFragWithBackStack(supportFragmentManager, mapFragment, this,R.id.content_home_holder)
                    }else{
                        FragmentUtils.removeFragment(supportFragmentManager, RestaurantFragment())
                        FragmentUtils.removeFragment(supportFragmentManager, FavoriteFragment())
                        FragmentUtils.replaceFragment(supportFragmentManager, mapFragment, R.id.content_home_holder, this)
                    }

                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportActionBar!!.title = "Restaurants"

                if (content_home_holder == null){
                    FragmentUtils.addFragment(supportFragmentManager, RestaurantFragment(), R.id.content_home_holder, this)
                }else{
                    FragmentUtils.removeFragment(supportFragmentManager, FavoriteFragment())
                    FragmentUtils.removeFragment(supportFragmentManager, MapFragment())
                    //  FragmentUtils.removeFragment(supportFragmentManager, weatherFragment)
                    FragmentUtils.replaceFragment(supportFragmentManager, RestaurantFragment(), R.id.content_home_holder, this)
                }
            }
            R.id.nav_favourite -> {
                supportActionBar!!.title = "Favourite Restaurants"
                if (content_home_holder == null){
                    FragmentUtils.addFragment(supportFragmentManager, FavoriteFragment(), R.id.content_home_holder, this)
                }else{
                    FragmentUtils.removeFragment(supportFragmentManager, RestaurantFragment())
                    FragmentUtils.removeFragment(supportFragmentManager, MapFragment())
                    //  FragmentUtils.removeFragment(supportFragmentManager, weatherFragment)
                    FragmentUtils.replaceFragment(supportFragmentManager, FavoriteFragment(), R.id.content_home_holder, this)
                }
            }
            R.id.nav_weather -> {
                supportActionBar!!.title = "Weather"
            }
            R.id.nav_logout -> {
                FragmentUtils.removeFragment(supportFragmentManager, MapFragment())
                FragmentUtils.removeFragment(supportFragmentManager, RestaurantFragment())
                FragmentUtils.removeFragment(supportFragmentManager, FavoriteFragment())
                var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
