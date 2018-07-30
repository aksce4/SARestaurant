package com.sa.restaurant.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sa.restaurant.appview.home.HomeActivity

class PermissionUtils(private val context: Context): ActivityCompat.OnRequestPermissionsResultCallback{

    private var permissionGranted: PermissionGranted? = null
    val TAG = "PermissionUtils"
    val LOCATION_PERMISSION_REQUEST_CODE = 9001
    var nonGrantedPermissions: Array<String>? = null
    var permissionCount = 0
    lateinit var homeActivity: HomeActivity

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }

    fun checkPermissions(permissionList: ArrayList<String>): Boolean {
//        Log.d(TAG, "getLocationPermission: getting location permissions")

        homeActivity = context as HomeActivity

        for(i in 0 until permissionList.size){
            if(ContextCompat.checkSelfPermission(context, permissionList[i]) == PackageManager.PERMISSION_GRANTED) {
                permissionCount++
            }else{
                nonGrantedPermissions = arrayOf(permissionList[i])
            }
        }

//        if(permissionCount < permissionList.size){
//            ActivityCompat.requestPermissions(mainActivity, nonGrantedPermissions!!, LOCATION_PERMISSION_REQUEST_CODE)
//        }

        Log.d(TAG, "onCreate: $permissionCount ${permissionList.size}");
        return permissionCount == permissionList.size
    }

    fun askForPermissions(){
        Log.d(TAG, "askForPermissions: ");
        ActivityCompat.requestPermissions(homeActivity, nonGrantedPermissions!!, LOCATION_PERMISSION_REQUEST_CODE)
//        ActivityCompat.requestPermissions(mainActivity, )
    }

    interface PermissionGranted {
        fun onPermissionGranted(requestCode: Int)
    }


}