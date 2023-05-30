package com.mestKom.data.user

data class User (
    val username: String,
    val password: String,
    val email: String,
    val salt: String,
    val sequelId: String
    )

