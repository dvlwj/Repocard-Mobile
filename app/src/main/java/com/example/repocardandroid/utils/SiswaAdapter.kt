package com.example.repocardandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repocardandroid.R
import com.example.repocardandroid.ScoreListActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import kotlinx.android.synthetic.main.score_card.view.*
import org.json.JSONObject


class SiswaAdapter(private val dataList: ArrayList<SiswaModel>?) : RecyclerView.Adapter<SiswaAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.score_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataList?.let{
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.student_name
        var classRoom: TextView = view.student_class
        var buttonSubmit: Button = view.submit_button
        fun bind(model: SiswaModel){
            var className = "kosong"
            when(model.classRoom){
                "1" -> className = "7A"
                "2" -> className = "7B"
                "3" -> className = "7C"
                "4" -> className = "8A"
                "5" -> className = "8B"
                "6" -> className = "8C"
                "7" -> className = "9A"
                "8" -> className = "9B"
                "9" -> className = "9C"
            }
            name.text = "Nama: ${model.name}"
            classRoom.text = "Kelas: $className"
            val id = model.id
            buttonSubmit.setOnClickListener {
                if (id != null) {
                    submitData(id, buttonSubmit.context)
                }
            }
        }
    }

    fun submitData(id: Int, context: Context) {
        val session = SessionManagement(context)
        val idMatpel = session.checkID(session.keyMatpel)
        val dataToSend = JSONObject()
        dataToSend.put("idSiswa", id)
        dataToSend.put("idMataPelajaran", idMatpel)
        val address =
            "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.UpdateScoreSubmit}"
        Fuel.post(address)
            .header(Headers.CONTENT_TYPE, "application/json")
            .body(dataToSend.toString())
            .response { request, response, result ->
                result.success {
                    val intent = Intent(context, ScoreListActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
                result.failure {
                    val fbM = FeedbackManagement(context)
                    fbM.showToastShort("Submit Gagal")
                    println("Submit gagal")
                }
            }
    }
}