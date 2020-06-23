package com.example.repocardandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.repocardandroid.utils.FeedbackManagement
import com.example.repocardandroid.utils.ServerAddress
import com.example.repocardandroid.utils.SessionManagement
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.change_password_button
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class ChangePasswordActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        val session = SessionManagement(this)
        change_password_button.setOnClickListener { changePassword() }
    }

    private fun changePassword() {
        val password1 = password1.text.toString()
        val password2 = password2.text.toString()
        val fbM = FeedbackManagement(this)
        when {
            password1.isEmpty() -> {
                fbM.showToastShort("Password tidak boleh kosong !")
            }
            password2.isEmpty() -> {
                fbM.showToastShort("Konfirmasi Password tidak boleh kosong !")
            }
            password1 != password2 -> {
                fbM.showToastShort("Password tidak sama")
            }
            password1 == password2 -> {
                connectServer(password1)
            }
            else -> fbM.showToastShort("Ada kesalahan sistem.")
        }
    }

    private fun connectServer(password: String) {
        val session = SessionManagement(this)
        val username = session.checkData(session.keyUsername)
        var address = ""
        val dataToSend = JSONObject()
        dataToSend.put("username",username)
        dataToSend.put("password",password)
        when(session.checkSiswa()){
            true -> address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.ChangePasswordSiswa}"
            false -> address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.ChangePassword}"
        }
        Fuel.post(address)
            .header(Headers.CONTENT_TYPE, "application/json")
            .body(dataToSend.toString())
            .response { request, response, result ->
                result.success {
                    SessionManagement(this).clearData()
                    finishAffinity()
                }
                result.failure {
                    val fbM = FeedbackManagement(this.baseContext)
                    fbM.showToastShort("Proses Gagal")
                    println("Proses gagal")
                }
            }
    }
}