package com.sa.restaurant.appview.login

import android.arch.persistence.room.Room
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sa.restaurant.R
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment: Fragment(){

    lateinit var myDatabase: MyDatabase

    //onCreateView method is called when Fragment should create its View object
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myDatabase = Room.databaseBuilder(activity!!, MyDatabase::class.java, "Database").allowMainThreadQueries().build()


        fragment_signup_txt_signin.setOnClickListener {
            var loginFragment: LoginFragment = LoginFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, loginFragment, R.id.framelayout_main, activity!!)
        }

        fragment_signup_btn_appcompact.setOnClickListener {
            var name: String = fragment_signup_edt_name.text.toString()
            var username: String = fragment_signup_edt_username.text.toString()
            var email: String = fragment_signup_edt_email.text.toString()
            var password: String = fragment_signup_edt_password.text.toString()
        }
    }








}