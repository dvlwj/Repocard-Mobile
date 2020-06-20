package com.example.repocardandroid

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_button.setOnClickListener {
            val loginUsername = login_username.text
            val loginPassword = login_password.text
            val toast = Toast.makeText(applicationContext,
                "$loginUsername : $loginPassword", Toast.LENGTH_LONG)
            toast.show()
        }
    }
}