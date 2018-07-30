package com.sa.restaurant.appview.login.view

import android.app.Activity
import com.sa.restaurant.appview.MainActivity

interface LoginView {

    fun authUser(activity: MainActivity, username: String?, email: String?){

    }
}
