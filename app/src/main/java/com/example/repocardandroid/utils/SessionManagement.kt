package com.example.repocardandroid.utils

import android.content.Context

class SessionManagement(context: Context?) {

    private val sharedPrefName   = "repocard"
    val keyUsername      = "user_username"
    val keyPassword      = "user_password"
    val keyMatpel         = "user_matpel"
    val keyLevel          = "user_level"
    val keyUserID        = "user_userid"
    val keyServerAddress = "server_address"
    val keySiswa         = "user_siswa"
    private val sharedPrefs= context?.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
    private val sharedPrefsEditor= sharedPrefs?.edit()

    fun updateUsername(value: String?){
        sharedPrefsEditor?.putString(keyUsername,value)
        sharedPrefsEditor?.apply()
    }
    fun updatePassword(value: String?){
        sharedPrefsEditor?.putString(keyPassword,value)
        sharedPrefsEditor?.apply()
    }
    fun updateMatpel(value: Int){
        sharedPrefsEditor?.putInt(keyMatpel,value)
        sharedPrefsEditor?.apply()
    }
    fun updateLevel(value: Int){
        sharedPrefsEditor?.putInt(keyLevel,value)
        sharedPrefsEditor?.apply()
    }
    fun updateUserID(value: Int){
        sharedPrefsEditor?.putInt(keyUserID,value)
        sharedPrefsEditor?.apply()
    }
    fun updateServerAddress(value: String?){
        sharedPrefsEditor?.putString(keyServerAddress,value)
        sharedPrefsEditor?.apply()
    }
    fun updateSiswa(value: Boolean){
        sharedPrefsEditor?.putBoolean(keySiswa,value)
        sharedPrefsEditor?.apply()
    }
    fun checkID(key: String?):Int?{
        return sharedPrefs?.getInt(key,0)
    }
    fun checkData(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkSiswa():Boolean?{
        return sharedPrefs?.getBoolean(keySiswa, false)
    }
    fun checkServerAddress(key: String?):String?{
//        return sharedPrefs?.getString(key,"192.168.1.113")
        return sharedPrefs?.getString(key,ServerAddress.localhost)
    }
    fun clearData(){
        sharedPrefsEditor?.clear()
        sharedPrefsEditor?.apply()
    }
}