package com.pushpa.vehicletracking.response

import com.pushpa.vehicletracking.model.User

data class UserDataResponse (
    val success:Boolean? = null,
    val data: User? = null
)