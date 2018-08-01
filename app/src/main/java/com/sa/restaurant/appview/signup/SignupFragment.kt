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
    lateinit var name: String
    lateinit var username: String
    lateinit var email: String
    lateinit var  password: String
    var valid: Boolean = false

    //onCreateView method is called when Fragment should create its View object
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myDatabase = Room.databaseBuilder(activity!!, MyDatabase::class.java, "AppData").allowMainThreadQueries().build()


        fragment_signup_txt_signin.setOnClickListener {
            var loginFragment: LoginFragment = LoginFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, loginFragment, R.id.framelayout_main)
        }

        fragment_signup_btn_appcompact.setOnClickListener {
            name = fragment_signup_edt_name.text.toString()
            username = fragment_signup_edt_username.text.toString()
            email = fragment_signup_edt_email.text.toString()
            password= fragment_signup_edt_password.text.toString()

            if (valid){
               validation()
            }

            var signupPresenter: SignupPresenter = SignupPresenterImpl()
            signupPresenter.checkUser(name, username, password, email, myDatabase, activity!!, fragmentManager!!)
        }
    }

    override fun showInfo(activity: Activity, fragmentManager: FragmentManager) {

        var loginFragment: LoginFragment = LoginFragment()

        Toast.makeText(activity, "Register Successfully..!!", Toast.LENGTH_SHORT).show()

        FragmentUtils.replaceFragment(fragmentManager, loginFragment, R.id.framelayout_main)
    }

    fun validation(): Boolean{
        valid = true

        if(name.isEmpty()){
            fragment_signup_edt_username.setError("Please Enter Name")
        }else{
            fragment_signup_edt_username.setError(null)
        }

        if(username.isEmpty()){
            fragment_signup_edt_username.setError("Please Enter Username")
        }else{
            fragment_signup_edt_username.setError(null)
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            fragment_signup_edt_email.setError("enter a valid email address");
        } else {
            fragment_signup_edt_email.setError(null);
        }


        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            fragment_signup_edt_password.setError("Between 4 and 10 alphanumeric characters");
        } else {
            fragment_signup_edt_password.setError(null);
        }

        return valid
    }











}