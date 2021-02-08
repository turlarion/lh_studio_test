package ru.turlarion.turlyuktestapp

import com.google.gson.annotations.SerializedName
import java.net.URL

data class CompanyData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val imgAddr: String,
    @SerializedName("description")
    val desc: String,
    @SerializedName("lat")
    val lat: Float,
    @SerializedName("lon")
    val lon: Float,
    @SerializedName("www")
    val site: String,
    @SerializedName("phone")
    val phone: String
    )
