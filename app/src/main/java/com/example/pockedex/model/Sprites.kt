package com.example.pockedex.model

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val photoUrl : String? = ""
)
