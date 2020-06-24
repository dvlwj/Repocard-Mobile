package com.example.repocardandroid

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.repocardandroid.utils.FeedbackManagement
import com.example.repocardandroid.utils.SessionManagement
import kotlinx.android.synthetic.main.activity_server_config.*

class ServerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_config)
        val session = SessionManagement(this)
        val serverAddress = session.checkServerAddress(session.keyServerAddress)
        server.setText(serverAddress)
        button_confirm?.setOnClickListener { validateServer() }
    }

    private fun validateServer() {
        val serverText = server?.text.toString()
        val session = SessionManagement(this)
        val serverAddress = session.checkServerAddress(session.keyServerAddress)
        when {
            serverText == serverAddress -> {
                server?.requestFocus()
            }
            serverText.isEmpty() -> {
                server?.requestFocus()
            }
            !serverText.isEmpty() -> {
                try {
                    session.updateServerAddress(serverText)
                    Handler().postDelayed({finish()},500)
                } catch (e: Exception) {
                }
            }
        }
    }
}