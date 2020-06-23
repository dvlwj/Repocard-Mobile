package com.example.repocardandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repocardandroid.R
import kotlinx.android.synthetic.main.score_card.view.*

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
        var buttonSubmit: Button = view.submit_button
        fun bind(model: SiswaModel){
            name.text = model.name
            val id = model.id
            buttonSubmit.setOnClickListener {
                println(id)
            }
        }
    }
}