package com.pushpa.vehicletracking.repository

import com.pushpa.vehicletracking.api.ApiRequest
import com.pushpa.vehicletracking.api.ServiceBuilder
import com.pushpa.vehicletracking.api.UserApi
import com.pushpa.vehicletracking.model.User
import com.pushpa.vehicletracking.response.LoginResponse
import com.pushpa.vehicletracking.response.RegisterResponse

class UserRepository: ApiRequest() {
    val api = ServiceBuilder.buildService(UserApi::class.java)

    suspend fun loginUser(user: User): LoginResponse {
        return  apiRequest {
            api.checkUser(user)
        }
    }
    suspend fun registerUser(user: User): RegisterResponse {
        return apiRequest {
            api.registerUser(user)
        }
    }
}