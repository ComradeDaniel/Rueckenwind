@file:OptIn(kotlinx.cinterop.BetaInteropApi::class)
package com.windrad.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import platform.Foundation.*

actual object HttpUtil {
    actual suspend fun get(urlString: String): String? = withContext(Dispatchers.Default) {
        suspendCoroutine { continuation ->
            val url = NSURL.URLWithString(urlString)
            if (url == null) {
                continuation.resume(null)
                return@suspendCoroutine
            }
            val request = NSMutableURLRequest.requestWithURL(url)
            request.setValue("WindradApp/1.0 (contact: support@windrad.com)", forHTTPHeaderField = "User-Agent")
            request.setTimeoutInterval(5.0)

            val session = NSURLSession.sharedSession
            val task = session.dataTaskWithRequest(request) { data, response, error ->
                if (error != null || data == null) {
                    continuation.resume(null)
                } else {
                    val httpResponse = response as? NSHTTPURLResponse
                    if (httpResponse?.statusCode == 200L) {
                        val nsString = NSString.create(data = data, encoding = NSUTF8StringEncoding)
                        continuation.resume(nsString?.toString())
                    } else {
                        continuation.resume(null)
                    }
                }
            }
            task.resume()
        }
    }
}

@Suppress("CAST_NEVER_SUCCEEDS")
actual fun urlEncode(value: String): String {
    val nsString = value as NSString
    return nsString.stringByAddingPercentEncodingWithAllowedCharacters(NSCharacterSet.URLQueryAllowedCharacterSet) ?: value
}
