package com.blackbyte.skucise.utils

import android.util.Base64
import android.util.Log
import com.blackbyte.skucise.MainActivity
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import com.squareup.okhttp.RequestBody


enum class RequestType {
    REQUEST_POST,
    REQUEST_GET
}

class Utils {
    companion object Requests {
        fun GET(
            apiURL: String,
            params: List<Pair<String, String>> = listOf(),
            includeAuthParams: Boolean = false,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            thread {
                val client = OkHttpClient()

                var reqParam = ""
                for (param in params) {
                    reqParam += "&${URLEncoder.encode(param.first, "UTF-8")}=${
                        URLEncoder.encode(
                            param.second,
                            "UTF-8"
                        )
                    }"
                }
                // Remove leading '&'
                if (params.lastIndex != -1) {
                    reqParam = reqParam.substring(startIndex = 1)
                }

                val request =
                    if (includeAuthParams) {
                        Request.Builder()
                            .url("$apiURL?$reqParam")
                            .get()
                            .addHeader(
                                "Authorization",
                                MainActivity.prefs?.authToken
                            )
                            .build()
                    } else {
                        Request.Builder()
                            .url("$apiURL?$reqParam")
                            .get()
                            .build()
                    }
                try {
                    val response = client.newCall(request).execute()
                    onFinish(response.body().string(), response.code())
                } catch (e:Exception) {
                    onFinish("", 12163)
                }
            }
        }

        fun POST(
            apiURL: String,
            params: List<Pair<String, String>> = listOf(),
            includeAuthParams: Boolean = false,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            thread {
                val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")
                val client = OkHttpClient()

                var jsonParam = ""
                for (param in params) {
                    if(param.second is String) {
                        jsonParam += ",\"${param.first}\":\"${param.second}\""
                    } else {
                        jsonParam += ",\"${param.first}\":${param.second}"
                    }
                }
                // Remove leading ','
                if (params.lastIndex != -1) {
                    jsonParam = "{${jsonParam.substring(startIndex = 1)}}"
                }

                val body: RequestBody = RequestBody.create(JSON, jsonParam)
                val request = if (includeAuthParams) {
                    Request.Builder()
                        .url(apiURL)
                        .post(body)
                        .addHeader(
                            "Authorization",
                            MainActivity.prefs?.authToken
                        )
                        .build()
                } else {
                    Request.Builder()
                        .url(apiURL)
                        .post(body)
                        .build()
                }
                try {
                    val response = client.newCall(request).execute()
                    onFinish(response.body().string(), response.code())
                } catch (e:Exception) {
                    onFinish("", 12163)
                }
            }
        }

        fun getUserData(username: String, onFinish: (body: String, responseCode: Int) -> Unit) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.Settings.SERVER_ADDRESS}:${Config.Settings.PORT}/api/users/$username",
                onFinish = onFinish
            )
        }

        fun register(
            username: String,
            password: String,
            name: String,
            surname: String,
            date_of_birth: String,
            phone_number: String,
            document_type: String,
            document_number: String,
            avatar_url: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("username", username),
                    Pair("password", password),
                    Pair("name", name),
                    Pair("surname", surname),
                    Pair("date_of_birth", date_of_birth),
                    Pair("phone_number", phone_number),
                    Pair("document_type", document_type),
                    Pair("document_number", document_number),
                    Pair("avatar_url", avatar_url)
                ),
                apiURL = "http://${Config.Settings.SERVER_ADDRESS}:${Config.Settings.PORT}/api/auth/register",
                onFinish = onFinish
            )
        }
        fun login(
            username: String,
            password: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("username", username),
                    Pair("password", password)
                ),
                apiURL = "http://${Config.Settings.SERVER_ADDRESS}:${Config.Settings.PORT}/api/auth/login",
                onFinish = onFinish
            )
        }
    }
}