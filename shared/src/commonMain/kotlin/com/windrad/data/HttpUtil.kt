package com.windrad.data

expect object HttpUtil {
    suspend fun get(urlString: String): String?
}

expect fun urlEncode(value: String): String
