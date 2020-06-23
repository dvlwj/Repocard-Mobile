package com.example.repocardandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.repocardandroid.utils.FeedbackManagement
import com.example.repocardandroid.utils.ServerAddress
import com.example.repocardandroid.utils.SessionManagement
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_button.setOnClickListener { validateCredentials() }
    }
    private fun validateCredentials() {
        val usernameText = login_username?.text.toString()
        val passwordText = login_password?.text.toString()
        val fbM = FeedbackManagement(this)
        when {
            usernameText.isEmpty() -> {
                fbM.showToastShort("Username tidak boleh kosong !")
            }
            passwordText.isEmpty() -> {
                fbM.showToastShort("Password tidak boleh kosong !")
            }
            !usernameText.isEmpty() && !passwordText.isEmpty() -> {
                connectServer(usernameText, passwordText)
            }
        }
    }
    private fun connectServer(username: String, password: String) {
        val dataToSend = JSONObject()
        dataToSend.put("username",username)
        dataToSend.put("password",password)
        val session = SessionManagement(this)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.Login}"
        val fbM = FeedbackManagement(this)
        Fuel.post(address)
        .header(Headers.CONTENT_TYPE, "application/json")
        .body(dataToSend.toString())
        .response { request, response, result ->
            result.success {
                val responseData = response.data
                val jsonData: JSONObject = JSONObject(String(responseData))
                val jsonDataDetail = jsonData.getJSONObject("data")
                val dataAdmin = jsonDataDetail.getString("username")
                val dataPassword = jsonDataDetail.getString("password")
                val dataLevel = jsonDataDetail.getInt("level")
                var dataMatpel: Int = 0
                if (dataLevel == 2){
                    dataMatpel = jsonDataDetail.getInt("matpel")
                }
                session.run {
                    updateUsername(dataAdmin)
                    updateLevel(dataLevel)
                    updatePassword(dataPassword)
                    updateMatpel(dataMatpel)
                    updateSiswa(false)
                }
                println("Name : $dataAdmin, Password : $dataPassword, Level : $dataLevel, Matpel : $dataMatpel")
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            result.failure {
                connectServerAsStudent(username, password)
//                fbM.showToastShort("Login Gagal")
//                println("Login gagal")
            }
        }
    }
    private fun connectServerAsStudent(username: String, password: String) {
        val dataToSend = JSONObject()
        dataToSend.put("username",username)
        dataToSend.put("password",password)
        val session = SessionManagement(this)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.LoginStudent}"
        Fuel.post(address)
        .header(Headers.CONTENT_TYPE, "application/json")
        .body(dataToSend.toString())
        .response { request, response, result ->
            result.success {
                val responseData = response.data
                val jsonData: JSONObject = JSONObject(String(responseData))
                val jsonDataDetail = jsonData.getJSONObject("data")
                val dataAdmin = jsonDataDetail.getString("username")
                val dataPassword = jsonDataDetail.getString("password")
                session.run {
                    updateUsername(dataAdmin)
                    updatePassword(dataPassword)
                    updateSiswa(true)
                }
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            result.failure {
                val fbM = FeedbackManagement(this.baseContext)
                fbM.showToastShort("Login Gagal")
                println("Login gagal")
            }
        }
    }
}