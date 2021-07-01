package com.pushpa.vehicletracking.api

import com.pushpa.vehicletracking.model.User
import com.pushpa.vehicletracking.response.LoginResponse
import com.pushpa.vehicletracking.response.RegisterResponse
import com.pushpa.vehicletracking.response.UserDataResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    //register user
    @POST("/user/add")
    suspend fun registerUser(
        @Body user: User
    ): Response<RegisterResponse>
    //Login user
    @POST("/user/login")
    suspend fun checkUser(
        @Body user: User
    ): Response<LoginResponse>

    @GET("/user/account")
    suspend fun getUser(
        @Header("Authorization") token : String
    ):Response<UserDataResponse>


}