package com.pushpa.vehicletracking.model

data class User(
        var _id: String? = null,
        var username:String?=null,
        var email:String?=null,
        var password:String?=null,
        var profile:String? = null
)