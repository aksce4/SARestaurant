package com.sa.restaurant.appview.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sa.restaurant.R
import kotlinx.android.synthetic.main.fragment_splash_screen.*

class SplashFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen,container,false);

    }

}