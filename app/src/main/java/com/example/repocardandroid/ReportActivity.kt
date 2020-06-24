package com.example.repocardandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.repocardandroid.utils.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_score_list.*
import org.json.JSONObject

class ReportActivity() : AppCompatActivity(){
    private var requestArrayList: ArrayList<NilaiModel>? = ArrayList()

    private var mAdapter: NilaiAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        recyclerView = list_rapor_siswa
        val session = SessionManagement(this)
        list_student_name.text = "Nama: ${session.checkData(session.keyUsername)}"
        var kelas = session.checkData(session.keyKelas)
        when (kelas) {
            "1" -> kelas = "7A"
            "2" -> kelas = "7B"
            "3" -> kelas = "7C"
            "4" -> kelas = "8A"
            "5" -> kelas = "8B"
            "6" -> kelas = "8C"
            "7" -> kelas = "9A"
            "8" -> kelas = "9B"
            "9" -> kelas = "9C"
        }
        list_student_class.text = "Kelas: $kelas"
        recyclerView?.adapter = mAdapter
//        val level = session.checkID(session.keyLevel)
//        if(level != 1) {
//            search_student.visibility = View.GONE
//        }
    }

    override fun onStart() {
        super.onStart()
        loadStudentData()
    }

    fun loadStudentData() {
//        progressbar?.visibility = View.VISIBLE
//        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
//        anim.duration = 1000
//        progressbar.startAnimation(anim)
        val session = SessionManagement(this)
        val dataToSend = JSONObject()
        val idSiswa = session.checkID(session.keyUserID)
        dataToSend.put("idSiswa", idSiswa)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.ReadScoreByStudent}"
        Fuel.post(address)
            .header(Headers.CONTENT_TYPE, "application/json")
            .body(dataToSend.toString())
            .response { request, response, result ->
                result.success {
                    val respond = String(response.data)
                    val stringBuilder = StringBuilder(respond)
                    val parser = Parser.default()
                    val respondParser = parser.parse(stringBuilder) as JsonObject
                    val data = respondParser.array<JsonObject>("data")
                    when{
                        data?.isNotEmpty()!! -> {
                            val arrayList = data?.map {it1->
                                NilaiModel(
                                    it1.int("id_mata_pelajaran"),
                                    it1.int("nilai1")!!.toInt(),
                                    it1.int("nilai2")!!.toInt(),
                                    it1.int("nilai3")!!.toInt(),
                                    it1.int("nilai4")!!.toInt(),
                                    it1.int("nilai5")!!.toInt(),
                                    it1.int("nilai6")!!.toInt(),
                                    it1.int("nilai7")!!.toInt(),
                                    it1.int("nilai8")!!.toInt(),
                                    it1.int("nilai9")!!.toInt(),
                                    it1.int("nilai10")!!.toInt()
                                )
                            }
                            requestArrayList = ArrayList(arrayList)
                            renderRV()
                        }
                    }
                }
                result.failure {
                    val fbM = FeedbackManagement(this.baseContext)
                    fbM.showToastShort("Gagal dapat data")
                    println("Gagal dapat data")
                }
            }
    }

    fun renderRV(){
//        val recyclerView = list_rapor_siswa
        mAdapter = NilaiAdapter(requestArrayList)
        recyclerView?.layoutManager = LinearLayoutManager(this)
//        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = mAdapter
//        recyclerView.visibility = View.VISIBLE
    }
}