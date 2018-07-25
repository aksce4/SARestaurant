package com.sa.restaurant.appview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sa.restaurant.R
import com.sa.restaurant.appview.login.LoginFragment
import com.sa.restaurant.appview.login.SignupFragment
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var loginFragment: LoginFragment = LoginFragment()
        FragmentUtils.addFragment(supportFragmentManager, loginFragment, R.id.framelayout_main, this)
    }



}
