package com.example.repocardandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repocardandroid.R
import kotlinx.android.synthetic.main.report_card.view.*
import kotlinx.android.synthetic.main.score_card.view.*


class NilaiAdapter(private val dataList: ArrayList<NilaiModel>?) : RecyclerView.Adapter<NilaiAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.report_card_revamp, parent, false)
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
        var textMatpel = view.text_matpel
        var textMatpelScore = view.text_matpel_score
//        var textNilai1 = view.nilai1
//        var textNilai2 = view.nilai2
//        var textNilai3 = view.nilai3
//        var textNilai4 = view.nilai4
//        var textNilai5 = view.nilai5
//        var textNilai6 = view.nilai6
//        var textNilai7 = view.nilai7
//        var textNilai8 = view.nilai8
//        var textNilai9 = view.nilai9
//        var textNilai10 = view.nilai10
//        var textNilaiPS = view.nilaiPS
//        var textNilaiPR = view.nilaiPR
//        var textNilaiUH = view.nilaiUH
        fun bind(model: NilaiModel){
            var matpel = "Mata Pelajaran"
            when(model.id_mata_pelajaran){
                1 -> matpel = "1. Agama"
                2 -> matpel = "2. Bahasa Indonesia"
                3 -> matpel = "3. PKN"
                4 -> matpel = "4. Bahasa Inggris"
                5 -> matpel = "5. IPA"
                6 -> matpel = "6. IPS"
                7 -> matpel = "7. Matematika"
                8 -> matpel = "8. TIK"
                9 -> matpel = "9. Penjaskes"
                10 -> matpel = "10. Seni Budaya"
                11 -> matpel = "11. Bahasa Mandarin"
            }
            val meanPS = ((model.nilai1 + model.nilai2 + model.nilai3) / 3)
            val meanPR = ((model.nilai4 + model.nilai5 + model.nilai6) / 3)
            val meanUH = ((model.nilai7 + model.nilai8) / 2)
            val meanPSPRPUH = ((meanPS + meanPR + meanUH) / 3 )
            val meanMatpel = ((meanPSPRPUH + model.nilai9 + model.nilai10) / 3)
            textMatpel.text = matpel
//            textNilai1.text = model.nilai1.toString()
//            textNilai2.text = model.nilai2.toString()
//            textNilai3.text = model.nilai3.toString()
//            textNilai4.text = model.nilai4.toString()
//            textNilai5.text = model.nilai5.toString()
//            textNilai6.text = model.nilai6.toString()
//            textNilai7.text = model.nilai7.toString()
//            textNilai8.text = model.nilai8.toString()
//            textNilai9.text = "UTS : ${model.nilai9}"
//            textNilai10.text = "UAS : ${model.nilai10}"
//            textNilaiPS.text = meanPS.toString()
//            textNilaiPR.text = meanPR.toString()
//            textNilaiUH.text = meanUH.toString()
            textMatpelScore.text = meanMatpel.toString()
        }
    }
}