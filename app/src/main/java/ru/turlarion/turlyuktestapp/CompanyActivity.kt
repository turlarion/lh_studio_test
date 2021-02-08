package ru.turlarion.turlyuktestapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_company.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompanyActivity : AppCompatActivity() {

    val baseUrl = "https://lifehack.studio/test_task/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        fragment_article_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        fragment_article_toolbar.setNavigationOnClickListener { this.finish() }

        getCompanyInfo()

    }

    private fun getCompanyInfo() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CompanyService::class.java)
        val call = service.getCompany(intent.getIntExtra("id", 100))

        call.enqueue(object : Callback<List<CompanyData>>{
            override fun onResponse(call: Call<List<CompanyData>>, response: Response<List<CompanyData>>) {
                if (response.body() != null) {
                    updateInfo(response.body()!![0])
                } else {
                    Toast.makeText(applicationContext, "Ошибка получения данных с сервера - " + response.code(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<CompanyData>>, t: Throwable) {
                Toast.makeText(applicationContext, "Ошибка получения данных с сервера - " + t.localizedMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun updateInfo(info: CompanyData){
        fragment_article_toolbar_text.text = info.name
        www.text = if (info.site.isNotEmpty()) "Сайт организации: " + info.site else "Сайт организации: Не указано"
        phone.text = if (info.phone.isNotEmpty()) "Телефон организации: " + info.phone else  "Телефон организации: Не указано"
        coords.text ="Координаты: " + info.lon + " " + info.lat
        desc.text = info.desc
        Picasso.get()
            .load(baseUrl + info.imgAddr)
            .error(R.mipmap.img_404)
            .into(imageViewCompany)
    }

}