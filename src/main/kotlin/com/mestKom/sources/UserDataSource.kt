package com.mestKom.sources

import com.mestKom.data.user.User

interface UserDataSource {
    suspend fun allUsers() : List<User>
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User) : User?
    suspend fun editUser(email:String, password:String, username:String): Boolean
    suspend fun deleteUser(username: String): Boolean
}