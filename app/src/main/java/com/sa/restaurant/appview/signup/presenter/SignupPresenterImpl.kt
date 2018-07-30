package com.sa.restaurant.appview.signup.presenter

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.sa.restaurant.appview.login.SignupFragment
import com.sa.restaurant.appview.roomdatabase.model.LoginTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.sa.restaurant.appview.signup.view.SignupView

class SignupPresenterImpl: SignupPresenter{
    lateinit var singnupView: SignupView

    override fun checkUser(name: String, username: String, password: String, email: String, myDatabase: MyDatabase, activity: Activity, fragmentManager: FragmentManager) {

        var result: List<LoginTable> = myDatabase.logindao().checkUser(username, email)

        if (result.isNotEmpty()){
            Toast.makeText(activity, "User Already registered!!....", Toast.LENGTH_SHORT).show()
        }else{
            var loginTable: LoginTable = LoginTable()
            loginTable.name = name
            loginTable.username = username
            loginTable.password = password
            loginTable.email = email

            myDatabase.logindao().insertUser(loginTable)

            singnupView = SignupFragment()

            singnupView.showInfo(activity, fragmentManager)
        }
    }

}