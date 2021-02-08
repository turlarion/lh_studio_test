package ru.turlarion.turlyuktestapp

import com.google.gson.annotations.SerializedName

data class CompanyPojo (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("img")
    val imgAddr: String
    )
