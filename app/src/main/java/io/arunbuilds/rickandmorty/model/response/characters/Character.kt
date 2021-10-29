package io.arunbuilds.rickandmorty.model.response.characters


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @SerializedName("created")
    val created: String?,
    @SerializedName("episode")
    val episode: List<String>?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: Origin?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("status")
    val status: Status,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable