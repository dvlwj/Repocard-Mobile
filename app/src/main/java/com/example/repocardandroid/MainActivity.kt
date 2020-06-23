package com.example.repocardandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repocardandroid.utils.showToast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_button.setOnClickListener {
            val loginUsername = login_username.text
            val loginPassword = login_password.text
//            val toast = Toast.makeText(applicationContext,"$loginUsername : $loginPassword", Toast.LENGTH_LONG)
            val dataToSend = JSONObject()
            dataToSend.put("username",loginUsername)
            dataToSend.put("password",loginPassword)
//            toast.show()
            Fuel.post("http://192.168.5.123:8081/login")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body(dataToSend.toString())
                .response { request, response, result ->
                    val responseData = response.data
                    if (String(responseData).isNotEmpty()){
                        val jsonData: JSONObject = JSONObject(String(responseData))
                        val jsonDataDetail = jsonData.getJSONObject("data")
                        val dataAdmin = jsonDataDetail.getString("username")
                        val dataPassword = jsonDataDetail.getString("password")
                        val dataLevel = jsonDataDetail.getString("level")
                        val dataMatpel = jsonDataDetail.getString("matpel")
                        println("Name : $dataAdmin, Password : $dataPassword, Level : $dataLevel, Matpel : $dataMatpel")
//                        val toast = showToast(applicationContext, "Login Sukses")
                        val toast = Toast.makeText(baseContext, "LOGIN SUKSES", Toast.LENGTH_LONG)
                        toast.show()
//                        val toast = Toast.makeText(baseContext, "Login Sukses", Toast.LENGTH_SHORT)
//                        toast.show()
                    } else {
                        val toast = Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT)
                        toast.show()
                    }
//                    println(result)
//                    Toast.makeText(applicationContext, String(response.data), Toast.LENGTH_LONG).show()
                }
        }
    }
}