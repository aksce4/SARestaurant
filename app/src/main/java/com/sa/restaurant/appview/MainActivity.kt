package com.sa.restaurant.appview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sa.restaurant.R
import com.sa.restaurant.appview.login.SignupFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                signup(view)
            }

        })
    }

    private fun signup(view: View?) {
        val intent: Intent = Intent(this,SignupFragment::class.java)
        startActivity(intent)
    }


}
