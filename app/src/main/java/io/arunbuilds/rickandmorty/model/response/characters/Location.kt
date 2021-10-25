package io.arunbuilds.rickandmorty.model.response.characters


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)