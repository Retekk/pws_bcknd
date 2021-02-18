package com.r.model

import kotlinx.serialization.Serializable

@Serializable
data class PW(val webSite: String, val userName: String, val pw: String)