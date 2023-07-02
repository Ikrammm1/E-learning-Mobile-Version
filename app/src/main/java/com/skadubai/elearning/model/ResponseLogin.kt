package com.skadubai.elearning.model

data class ResponseLogin (
    val status: String,
    var response : Boolean,
    val payload : PayloadLogin
)