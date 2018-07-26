package com.sa.restaurant.appview.login.presenter

import android.app.Activity
import android.widget.Toast
import com.sa.restaurant.appview.login.LoginFragment
import com.sa.restaurant.appview.roomdatabase.LoginTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase

class LoginPresenterImpl: LoginPresenter{

    override fun validateuser(username: String, password: String, myDatabase: MyDatabase, activity: Activity): Boolean {
        var result: List<LoginTable> = myDatabase.logindao().userLogin(username, password)

        if (result.isNotEmpty()){
            var loginFragment: LoginFragment = LoginFragment()
            var Username = result[0].username
            var Password = result[0].password
            loginFragment.authUser(activity, Username, Password)
            return true
        }else{
            Toast.makeText(activity, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            return false
        }
    }


}

