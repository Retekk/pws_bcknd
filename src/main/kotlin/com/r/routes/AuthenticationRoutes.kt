package com.r.routes

import com.r.model.PW
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

internal fun Routing.loginRoute() {
    route("/login") {
        authenticate("form") {
            post {
                val principal = call.principal<UserIdPrincipal>()
                call.sessions.set(principal)
                call.respondText(
                    "Success login",
                    status = HttpStatusCode.Accepted
                )
            }
        }
    }
}

internal fun Route.logoutRoute() {
    post("/logout") {
        call.sessions.clear<UserIdPrincipal>()
        call.respondText(
            "Success logout",
            status = HttpStatusCode.Accepted
        )
    }
}

internal fun Route.allPWRoute() {
    authenticate("session") {
        get("/all_pw") {
            call.respond(
                listOf(
                    PW("pornhub.com", "Izomgerenda", "locskosp"),
                    PW("xhamster.com", "HatalmasPallos", "gyulolomazeletem"),
                    PW("xvideos.com", "VaginaVadász", "plskillme"),
                    PW("efukt.com", "PussyDestroyer43", "123456"),
                    PW("rosszlanyok.hu", "PornStar666", "jelszo")
                )
            )
        }
    }
}