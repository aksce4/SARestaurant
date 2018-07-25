package com.sa.restaurant.appview.login.view

import android.app.Activity

interface LoginView {

    fun authUser(activity: Activity, username: String?, password: String?){

    }
}
