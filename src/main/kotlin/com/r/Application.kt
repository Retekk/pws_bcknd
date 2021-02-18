package com.r

import com.r.model.User
import com.r.routes.allPWRoute
import com.r.routes.loginRoute
import com.r.routes.logoutRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.sessions.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }

    install(Sessions) {
        configureAuthCookie()
    }

    install(Authentication) {
        configureSessionAuth()
        configureFromAuth()
    }

    routing {
        loginRoute()
        logoutRoute()
        allPWRoute()
    }

}

private fun Sessions.Configuration.configureAuthCookie() {
    cookie<UserIdPrincipal>(
        "auth",
        storage = SessionStorageMemory()
    ) {
        cookie.path = "/"
        cookie.extensions["SameSite"] = "lax"
    }
}

private fun Authentication.Configuration.configureFromAuth() {
    form("form") {
        userParamName = "username"
        passwordParamName = "password"
        challenge {
            val errors: List<AuthenticationFailedCause> = call.authentication.allErrors
            call.respondText(
                "Invalid user or password $errors",
                 status = HttpStatusCode.Unauthorized
            )
        }
        validate {
                cred: UserPasswordCredential ->
            if (cred.name == "asd" && cred.password == "dsa") UserIdPrincipal (
                cred.name
            ) else null
        }
    }
}

private fun Authentication.Configuration.configureSessionAuth() {
    session<UserIdPrincipal>("session") {
        challenge {
            call.respondText(
                "Not authenticatecd",
                status = HttpStatusCode.Unauthorized
            )
        }
        validate {
            session : UserIdPrincipal -> session
        }
    }
}