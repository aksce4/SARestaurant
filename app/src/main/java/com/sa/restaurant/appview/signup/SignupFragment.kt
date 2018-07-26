package com.sa.restaurant.appview.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.sa.restaurant.R
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.sa.restaurant.appview.signup.presenter.SignupPresenter
import com.sa.restaurant.appview.signup.presenter.SignupPresenterImpl
import com.sa.restaurant.appview.signup.view.SignupView
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment: Fragment(), SignupView{

    lateinit var myDatabase: MyDatabase

    //onCreateView method is called when Fragment should create its View object
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myDatabase = Room.databaseBuilder(activity!!, MyDatabase::class.java, "AppData").allowMainThreadQueries().build()


        fragment_signup_txt_signin.setOnClickListener {
            var loginFragment: LoginFragment = LoginFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, loginFragment, R.id.framelayout_main, activity!!)
        }

        fragment_signup_btn_appcompact.setOnClickListener {
            var name: String = fragment_signup_edt_name.text.toString()
            var username: String = fragment_signup_edt_username.text.toString()
            var email: String = fragment_signup_edt_email.text.toString()
            var password: String = fragment_signup_edt_password.text.toString()

            var signupPresenter: SignupPresenter = SignupPresenterImpl()
            signupPresenter.checkUser(name, username, password, email, myDatabase, activity!!, fragmentManager!!)
        }
    }

    override fun showInfo(activity: Activity, fragmentManager: FragmentManager) {

        var loginFragment: LoginFragment = LoginFragment()

        Toast.makeText(activity, "Register Successfully..!!", Toast.LENGTH_SHORT).show()

        FragmentUtils.replaceFragment(fragmentManager, loginFragment, R.id.framelayout_main, activity)
    }










}