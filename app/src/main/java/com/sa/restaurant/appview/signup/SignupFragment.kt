package com.sa.restaurant.appview.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sa.restaurant.R
import com.sa.restaurant.utils.FragmentUtils

class SignupFragment: Fragment(), View.OnClickListener{

    //onCreateView method is called when Fragment should create its View object
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var view: View = inflater.inflate(R.layout.fragment_signup, container, false)
        return view
    }


    override fun onClick(view: View?) {

        when(view!!.id){
            R.id.fragment_signup_txt_signin -> {
                var loginFragment: LoginFragment = LoginFragment()
                FragmentUtils.replaceFragment(fragmentManager!!, loginFragment, R.id.framelayout_main, activity!!)

            }
        }
    }


}