package com.sa.restaurant.appview.signup.presenter

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.sa.restaurant.appview.roomdatabase.MyDatabase

interface SignupPresenter {

    fun checkUser(name: String, username: String, password: String ,email: String, myDatabase: MyDatabase, activity: Activity, fragmentManager: FragmentManager)
}