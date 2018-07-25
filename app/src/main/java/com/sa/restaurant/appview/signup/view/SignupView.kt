package com.sa.restaurant.appview.signup.view

import android.app.Activity
import androidx.fragment.app.FragmentManager

interface SignupView {

    fun showInfo(activity: Activity, fragmentManager: FragmentManager)
}