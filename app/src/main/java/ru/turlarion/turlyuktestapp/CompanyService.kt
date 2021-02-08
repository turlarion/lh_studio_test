package ru.turlarion.turlyuktestapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyService {
    @GET("test.php")
    fun getCompaniesList(): Call<List<CompanyPojo>>

    @GET("test.php")
    fun getCompany(@Query("id") id: Int): Call<List<CompanyData>>

}