package com.mestKom

import com.mestKom.data.tables.Users
import com.mestKom.data.tables.Users.username
import com.mestKom.data.user.User
import com.mestKom.requests.AuthRequest
import com.mestKom.responses.AuthResponse
import com.mestKom.security.hashing.HashingService
import com.mestKom.security.hashing.SHA256HashingService
import com.mestKom.security.hashing.SaltedHash
import com.mestKom.security.token.TokenClaim
import com.mestKom.security.token.TokenConfig
import com.mestKom.security.token.TokenService
import com.mestKom.sources.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
){
    post("signup") {
        val request = kotlin.runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run{
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length < 8

        if(areFieldsBlank || isPwTooShort){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt,
            email = "test@test.ru", // сделать запрос
            id = (0 .. 1000000).random() //last id
        )

                    val wasAcknowledged = userDataSource.insertUser(user)
                    if (wasAcknowledged == null) {
                        call.respond(HttpStatusCode.Conflict)
                        return@post

    }


        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
){
    post("signin") {
        val request = kotlin.runCatching { call.receiveNullable<AuthRequest>() }.getOrNull() ?: kotlin.run{
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUsername(request.username)
        if (user == null){
            call.respond(HttpStatusCode.Conflict, "User not found")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword){
            call.respond(HttpStatusCode.Conflict, "invalid password")
            return@post
        }
        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "UserId",
                value = user.id.toString()
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token
            )
        )
    }
}

fun Route.authenticate(){
    authenticate {
        get("authenticate"){
            call.respond(HttpStatusCode.OK)
        }
    }
}