package io.arunbuilds.rickandmorty.model.response.characters

import com.google.gson.annotations.SerializedName

enum class Status(private val status: String) {
    @SerializedName(value = "Alive", alternate = ["alive"])
    ALIVE("Alive"),

    @SerializedName(value = "Dead", alternate = ["dead"])
    DEAD("Dead"),

    @SerializedName(value = "unknown", alternate = ["Unknown"])
    UNKNOWN("Unknown");

    override fun toString() = status
}