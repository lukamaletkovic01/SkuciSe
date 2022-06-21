package com.example.skucise.LayoutActivities.LoginActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity

class SessionManager {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATE_MODE: Int = 0

    constructor(con: Context)
    {
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()

    }
    companion object
    {
        val PREF_NAME: String = "Skuci se"
        val IS_LOGIN: String = "isLoggedIn"
        val KEY_ROLE: String = "role"
        val KEY_TOKEN: String = "token"
        val KEY_EMAIL: String = "email"
        val KEY_ID: String = "id"
    }

    fun createLoginSession(email: String, role: Int, token: String, id : Long)
    {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_EMAIL, email)
        editor.putInt(KEY_ROLE, role)
        editor.putString(KEY_TOKEN, token)
        editor.putLong(KEY_ID, id)
        editor.commit()
    }

    fun checkLogin()
    {
        if(!this.isLoggedIn())
        {
            var i:Intent = Intent(con, LoginScreenActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            con.startActivity(i)
        }
        else
        {
            var i:Intent = Intent(con, HomeScreenActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            con.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String,String>
    {
        var user: Map<String, String> = HashMap<String, String>()
        (user as HashMap).put(KEY_EMAIL, pref.getString(KEY_EMAIL, null)!!)
        (user as HashMap).put(KEY_ROLE.toString(), pref.getInt(KEY_ROLE.toString(), 0).toString())
        (user as HashMap).put(KEY_TOKEN, pref.getString(KEY_TOKEN, null)!!)
        (user as HashMap).put(KEY_ID, pref.getLong(KEY_ID, -1)!!.toString())

        return user
    }

    fun LogoutUser()
    {
        editor.clear()
        editor.commit()

        var i:Intent = Intent(con, LoginScreenActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(i)
    }

    fun isLoggedIn(): Boolean
    {
        return pref.getBoolean(IS_LOGIN, false)
    }
}