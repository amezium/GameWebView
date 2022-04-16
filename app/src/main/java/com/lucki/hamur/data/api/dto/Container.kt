package com.lucki.hamur.data.api.dto

import com.google.gson.annotations.SerializedName

data class Container(
    @SerializedName("users" ) var users : ArrayList<Users> = arrayListOf()
)
