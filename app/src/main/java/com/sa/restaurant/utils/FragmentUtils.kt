package com.sa.restaurant.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object FragmentUtils {

    fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, id: Int){
        callMethod(fragmentManager).add(id, fragment).commit()
    }

    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, id: Int){
        callMethod(fragmentManager).addToBackStack(null).replace(id, fragment).commit()
    }

    fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment){
        callMethod(fragmentManager).remove(fragment).commit()
    }

    fun addFragWithBackStack(fragmentManager: FragmentManager, fragment: Fragment, id: Int){
        callMethod(fragmentManager).addToBackStack(null).add(id, fragment).commit()
    }


    fun callMethod(fragmentManager: FragmentManager): FragmentTransaction {
        var fragmentManager: FragmentManager = fragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        return fragmentTransaction
    }

}