package com.sa.restaurant.appview.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sa.restaurant.R
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_signin.*

class LoginFragment: Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_signin_txt_signup.setOnClickListener {
            var signupFragment: SignupFragment = SignupFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, signupFragment, R.id.framelayout_main, activity!!)
        }
    }


}