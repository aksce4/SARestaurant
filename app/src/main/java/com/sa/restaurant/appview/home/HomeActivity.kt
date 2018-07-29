package com.sa.restaurant.appview.home

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
import com.sa.restaurant.R
import com.sa.restaurant.appview.MainActivity
import com.sa.restaurant.appview.map.MapFragment
import com.sa.restaurant.appview.map.presenter.MapPresenterImpl
import com.sa.restaurant.appview.restaurant.GoogleApiServices
import com.sa.restaurant.appview.restaurant.RestaurantFragment
import com.sa.restaurant.appview.restaurant.adapter.RestaurantAdapter
import com.sa.restaurant.appview.restaurant.model.RestaurantDetails
import com.sa.restaurant.appview.restaurant.view.RestaurantView
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RestaurantView {
    override fun currentlatlng(location: Location, googleApiServices: GoogleApiServices, context: ViewGroup, activity: Context, adapter: RestaurantAdapter) {

    }

    override fun restaurnatlist(list: ArrayList<RestaurantDetails>, activity: Context, adapter: RestaurantAdapter) {
    }

    lateinit var mapPresenterImpl: MapPresenterImpl
    var mapFragment:MapFragment = MapFragment()
    var restaurantFragment: RestaurantFragment = RestaurantFragment()

    companion object {
        var mcount:Int=0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        mapPresenterImpl = MapPresenterImpl()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

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
                if (mapPresenterImpl.checkService(this)){
                    FragmentUtils.replaceFragment(supportFragmentManager, mapFragment, R.id.content_home_holder, this)
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
               FragmentUtils.replaceFragment(supportFragmentManager, restaurantFragment, R.id.content_home_holder, this)
            }
            R.id.nav_favourite -> {

            }
            R.id.nav_weather -> {

            }
            R.id.nav_logout -> {
               var intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
