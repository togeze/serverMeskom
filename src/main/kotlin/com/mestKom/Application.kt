package com.mestKom

import com.mestKom.data.user.User
import com.mestKom.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.mestKom.plugins.*
import com.mestKom.security.hashing.SHA256HashingService
import com.mestKom.security.token.JwtTokenService
import com.mestKom.security.token.TokenConfig
import com.mestKom.sources.DatabaseDataSource
import com.mestKom.sources.UserDataSource
import kotlinx.coroutines.runBlocking

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    DatabaseFactory.init()
    val userDataSource = DatabaseDataSource()
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = "http://0.0.0.0:8080",
        audience = "Users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()
//    val source: UserDataSource = DatabaseDataSource()
//    source.run {
//        runBlocking {
//            insertUser(User(username = "test", password = "test", email = "test@test.tt", salt = "222", id = (0 .. 1000000).random()))
//        }
//    }

    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(userDataSource, hashingService, tokenService, tokenConfig)
}
