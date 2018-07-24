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

class LoginFragment: Fragment(), View.OnClickListener{


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_signin, container, false)
        return view
    }

    override fun onClick(view: View?){

        when(view!!.id){
            R.id.btn_login -> {
                Log.e("LoginFragment","btn login called may be ")
                var signupFragment: SignupFragment = SignupFragment()
                FragmentUtils.replaceFragment(fragmentManager!!, signupFragment, R.id.framelayout_main, activity!!)
                Log.e("LoginFragment","btn login called may be 2")

            }
        }
    }








}