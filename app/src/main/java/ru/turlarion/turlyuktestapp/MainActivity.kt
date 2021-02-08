package ru.turlarion.turlyuktestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://lifehack.studio/test_task/"

    var list: List<CompanyPojo>? = emptyList()
    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecyclerViewAdapter(list!!, applicationContext, baseUrl)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fillList()
    }

    private fun fillList() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CompanyService::class.java)
        val call = service.getCompaniesList()

        call.enqueue(object : Callback<List<CompanyPojo>> {
            override fun onResponse(
                call: Call<List<CompanyPojo>>, response:
                Response<List<CompanyPojo>>
            ) {
                if (response.code() == 200) {
                    list = response.body()
                    if (list?.isEmpty() == true) {
                        Toast.makeText(applicationContext, "Ошибка - сервер вернул пустой список", Toast.LENGTH_SHORT).show()
                    } else if (list == null) {
                        Toast.makeText(applicationContext, "Ошибка - сервер не передал список", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.updateDataSet(list!!)
                    }
                } else {
                    Log.i("no 200 code", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<CompanyPojo>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Ошибка при запросе к серверу - " + t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
                Log.e("onFail", t.stackTraceToString())
            }
        })
    }
}