package com.example.repocardandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.repocardandroid.utils.SessionManagement
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val session = SessionManagement(this)
        val userLevel = session.checkID(session.keyLevel)
        when(session.checkSiswa()){
            true -> {
                card_teacher.visibility = View.GONE
                card_score.visibility = View.VISIBLE
            }
            false -> when (userLevel) {
                1 -> {
                    card_teacher.visibility = View.GONE
                    card_score.visibility = View.VISIBLE
                }
                2 -> {
                    card_teacher.visibility = View.VISIBLE
                    card_score.visibility = View.GONE
                }
            }
        }

    }
}