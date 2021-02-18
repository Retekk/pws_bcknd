package com.r.model

import kotlinx.serialization.Serializable
import java.security.Principal


@Serializable
data class User(val userName: String, val pw: String, val email: String) : Principal {

    override fun getName(): String {
        return userName
    }
}