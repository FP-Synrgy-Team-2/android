package com.example.domain.repository

import com.example.domain.model.Auth
import com.example.domain.model.BankAccount
import com.example.domain.model.Login
import com.example.domain.model.PinValidation

interface AuthRepository {
    suspend fun login(auth : Auth) : Login

    suspend fun pinValidation(pin : String) : BankAccount

    suspend fun logout()

    suspend fun getLoginStatus() : Boolean

    suspend fun forgotPassword(email : String) : String

    suspend fun validateOTP(otp : String) : String

    suspend fun resetPassword(email : String, otp: String, newPassword : String) : String

}