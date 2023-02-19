package com.mestKom.plugins

import com.mestKom.authenticate
import com.mestKom.security.hashing.HashingService
import com.mestKom.security.token.TokenConfig
import com.mestKom.security.token.TokenService
import com.mestKom.signIn
import com.mestKom.signUp
import com.mestKom.sources.UserDataSource
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
    }
}
