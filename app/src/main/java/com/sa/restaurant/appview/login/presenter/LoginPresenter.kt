package com.sa.restaurant.appview.login.presenter

import android.app.Activity
import com.sa.restaurant.appview.roomdatabase.MyDatabase

interface LoginPresenter {

    fun validateuser(username: String, password: String, myDatabase: MyDatabase, activity: Activity): Boolean
}