package com.sa.restaurant.appview.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.sa.restaurant.R
import com.sa.restaurant.appview.login.presenter.LoginPresenter
import com.sa.restaurant.appview.login.presenter.LoginPresenterImpl
import com.sa.restaurant.appview.login.view.LoginView
import com.sa.restaurant.appview.restaurant.RestaurantActivity
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_signin.*

class LoginFragment: Fragment(), LoginView{

    lateinit var myDatabase: MyDatabase
    lateinit var loginPresenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myDatabase = Room.databaseBuilder(activity!!, MyDatabase::class.java, "AppData").allowMainThreadQueries().build()

        fragment_signin_txt_signup.setOnClickListener {
            var signupFragment: SignupFragment = SignupFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, signupFragment, R.id.framelayout_main, activity!!)
        }

        fragment_signin_btn_appcompact.setOnClickListener {
            var username: String = fragment_signin_edt_name.text.toString()
            var password: String = fragment_signin_edt_password.text.toString()

            loginPresenter = LoginPresenterImpl()

            var success: Boolean = loginPresenter.validateuser(username, password, myDatabase, activity!!)

            if (success){
                var intent: Intent = Intent(activity,RestaurantActivity::class.java)
                startActivity(intent)
            }



        }
    }

    override fun authUser(activity: Activity, username: String?, password: String?) {
        Toast.makeText(activity, "Login successfull..!!", Toast.LENGTH_SHORT).show()
    }




}