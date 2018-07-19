package com.sa.restaurant.appview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sa.restaurant.R
import com.sa.restaurant.appview.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                splashhandle(view)
            }

        })
    }

    private fun splashhandle(view: View?) {
        var intent: Intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }
}
