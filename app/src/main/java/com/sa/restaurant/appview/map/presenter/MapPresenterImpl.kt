package com.sa.restaurant.appview.map.presenter

import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MapPresenterImpl: MapPresenter{

    val TAG: String = "MapPresenterImpl"
    val ERROR_DIALOG_REQUEST: Int = 8778

    override fun checkService(activity: AppCompatActivity): Boolean {
        Log.e(TAG,"CheckService: google services version correct or not")

        var available: Int = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG,"CheckService: Google Play Services is Working")
            return true

        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "CheckService: an error occured which we can fix it")
            var dialog: Dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, available, ERROR_DIALOG_REQUEST)
            dialog.show()

        }else{

           Toast.makeText(activity, "Map request can't make", Toast.LENGTH_SHORT).show()
        }
        return false
    }


}
