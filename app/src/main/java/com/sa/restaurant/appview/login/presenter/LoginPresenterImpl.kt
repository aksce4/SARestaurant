package com.sa.restaurant.appview.login.presenter

import android.app.Activity
import android.widget.Toast
import com.sa.restaurant.appview.MainActivity
import com.sa.restaurant.appview.login.LoginFragment
import com.sa.restaurant.appview.roomdatabase.model.LoginTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase

class LoginPresenterImpl: LoginPresenter{

    override fun validateuser(username: String, password: String, myDatabase: MyDatabase, activity: Activity): Boolean {
        var result: List<LoginTable> = myDatabase.logindao().userLogin(username, password)
       // var result1: List<LoginTable> = myDatabase.logindao().fbLogin(email, password)

        if (result.isNotEmpty()){
            var loginFragment: LoginFragment = LoginFragment()
            var Name = result[0].name
          //  var Username: String = result[0].username!!
            var Email = result[0].email
            loginFragment.authUser(activity as MainActivity, Name, Email)
            return true
        }else{
            Toast.makeText(activity, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            return false
        }
    }


}

