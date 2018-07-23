package com.sa.restaurant.appview.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sa.restaurant.R
import com.sa.restaurant.appview.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Thread(Runnable {
            try {
                val visibility = if (progress_bar.visibility == View.GONE) View.VISIBLE else View.GONE
                progress_bar.visibility = visibility
                Thread.sleep(5000)
            }catch (e: InterruptedException){
                Log.e("SplashActivity","Interrupt Occur $e")
            }finally {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }).start()
    }

}