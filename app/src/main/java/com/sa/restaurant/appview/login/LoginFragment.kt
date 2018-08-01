package com.sa.restaurant.appview.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginResult
import com.sa.restaurant.R
import com.sa.restaurant.appview.MainActivity
import com.sa.restaurant.appview.login.presenter.LoginPresenter
import com.sa.restaurant.appview.login.presenter.LoginPresenterImpl
import com.sa.restaurant.appview.login.view.LoginView
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import com.sa.restaurant.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_signin.*
import java.util.*

class LoginFragment: Fragment(), LoginView{

    lateinit var myDatabase: MyDatabase
    lateinit var loginPresenter: LoginPresenter
    lateinit var callbackManager: CallbackManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myDatabase = Room.databaseBuilder(activity!!, MyDatabase::class.java, "AppData").allowMainThreadQueries().build()


        callbackManager = CallbackManager.Factory.create();
        fragment_fb_login_button.loginBehavior = LoginBehavior.WEB_ONLY
        fragment_fb_login_button.setReadPermissions(Arrays.asList("email"))

        fragment_fb_login_button.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{

            override fun onSuccess(result: LoginResult?) {
                Log.i("Login", "Success")

                var request: GraphRequest = GraphRequest.newMeRequest(result!!.accessToken) { `object`, response ->
                    var email = `object`.getString("email")
                    var name = `object`.getString("name")

                    var image: String = `object`.getString("id")
                    Log.i("FB Image", image)

                    var sharedpref: SharedPreferences = activity!!.getSharedPreferences("UserInfo", 0)
                    var editor: SharedPreferences.Editor = sharedpref.edit()
                    editor.putString("username", name)
                    editor.putString("email", email)
                    editor.putString("number", "Permission required from facebook")
                    editor.apply()
                    Toast.makeText(activity, "Login Successfull", Toast.LENGTH_SHORT).show()
                    var intent: Intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                }

                var bundle:Bundle= Bundle()
                bundle.putString("fields","id,name,email")
                request.parameters=bundle
                request.executeAsync()
            }

            override fun onCancel() {
                Toast.makeText(activity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(activity, "Login Failed", Toast.LENGTH_LONG).show()
            }

        })





        fragment_signin_txt_signup.setOnClickListener {
            var signupFragment: SignupFragment = SignupFragment()
            FragmentUtils.replaceFragment(fragmentManager!!, signupFragment, R.id.framelayout_main)
        }

        fragment_signin_btn_appcompact.setOnClickListener {
            var username: String = fragment_signin_edt_name.text.toString()
            var password: String = fragment_signin_edt_password.text.toString()

            if(username.isEmpty()){
                fragment_signin_edt_name.setError("Please Enter Username")
            }else{
                fragment_signin_edt_name.setError(null)
            }


            if (password.isEmpty() || password.length < 4 || password.length > 10) {
                fragment_signin_edt_password.setError("Between 4 and 10 alphanumeric characters");
            } else {
                fragment_signin_edt_password.setError(null);
            }

            loginPresenter = LoginPresenterImpl()

            var success: Boolean = loginPresenter.validateuser(username, password, myDatabase, activity!!)

            if (success){
                var intent: Intent = Intent(activity,HomeActivity::class.java)
                startActivity(intent)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Fragment","OnActivity Result")
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun authUser(activity: MainActivity, name: String?, email: String?) {
        Toast.makeText(activity, "Login successfull..!!", Toast.LENGTH_SHORT).show()
        var sharedPreferences:SharedPreferences= activity.getSharedPreferences("UserInfo",0)
        var info: SharedPreferences.Editor = sharedPreferences.edit()
        info.putString("name", name)
        info.putString("email", email)
        info.apply()
    }




}