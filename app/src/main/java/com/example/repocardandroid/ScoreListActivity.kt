package com.example.repocardandroid

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.repocardandroid.utils.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import kotlinx.android.synthetic.main.activity_score_list.*
import org.json.JSONObject

class ScoreListActivity: AppCompatActivity() {
    private var requestArrayList: ArrayList<SiswaModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_list)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        loadData()
        return super.onCreateView(name, context, attrs)
    }

    private fun loadData() {
        val session = SessionManagement(this)
        val dataToSend = JSONObject()
        val idMatpel = session.checkID(session.keyMatpel)
        dataToSend.put("idMatpel",idMatpel)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.ReadScoreBySubject}"
        Fuel.post(address)
            .header(Headers.CONTENT_TYPE, "application/json")
            .body(dataToSend.toString())
            .response { request, response, result ->
                result.success {
                    val respond = String(response.data)
                    val stringBuilder = StringBuilder(respond)
                    val parser = Parser.default()
                    val respondParser = parser.parse(stringBuilder) as JsonObject
                    println("AAAA ${respondParser.toJsonString()}")
                    val data = respondParser.array<JsonObject>("data")
                    println("bbbb ${data}")
                    when{
                        data?.isNotEmpty()!! -> {
                            val arrayList = data?.map {it1->
                                SiswaModel(
                                    it1.int("id"),
                                    it1.string("nama")
                                )
                            }
                            println("bbbb ${data}")
                            requestArrayList = ArrayList(arrayList)
                            val recyclerView = score_recycle_view
                            val mAdapter = SiswaAdapter(requestArrayList)
                            val layoutManager = LinearLayoutManager(this)
                            recyclerView?.setHasFixedSize(true)
                            recyclerView?.addItemDecoration(
                                DividerItemDecoration(
                                    this,
                                    LinearLayoutManager.VERTICAL
                                )
                            )
                            recyclerView?.layoutManager = layoutManager
                            recyclerView?.itemAnimator = DefaultItemAnimator()
                            recyclerView?.adapter = mAdapter
                        }
                    }
                }
                result.failure {
                    val fbM = FeedbackManagement(this.baseContext)
                    fbM.showToastShort("Gagal dapat data")
                    println("Gagal dapat data")
                }
            }
//        println("scoreList : $scoreList and studentList : $studentList")
    }
}