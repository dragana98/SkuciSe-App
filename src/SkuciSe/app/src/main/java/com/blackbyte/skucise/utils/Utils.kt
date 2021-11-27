package com.blackbyte.skucise.utils

import android.R.attr
import android.util.Base64
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.R.attr.password
import java.util.Base64.getEncoder


enum class RequestType{
    REQUEST_POST,
    REQUEST_GET
}

class Utils {
    companion object Requests {
        fun GET (apiURL: String, params: List<Pair<String,String>> = listOf(), includeAuthParams : Boolean = false) : String {
            var reqParam = ""
            for (param in params) {
                reqParam += "&${URLEncoder.encode(param.first, "UTF-8")}=${URLEncoder.encode(param.second, "UTF-8")}"
            }
            // Remove leading '&'
            reqParam = reqParam.substring(startIndex = 1)

            val mURL = URL("$apiURL?$reqParam")

            with(mURL.openConnection() as HttpURLConnection) {
                // optional default is GET
                this.requestMethod = "GET"

                if(includeAuthParams) {
                    // TODO: replace with shared preferences
                    val userCredentials = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJrdmVsZmVsQGdtYWlsLmNvbSIsImlhdCI6MTYzODAyODgyNSwiZXhwIjoxNjM4MTE1MjI1fQ.7Zij1F-0J7tFbkFx9Ngq3RuZaCO8MJBcX5wcicsHLQU"

                    val basicAuth = Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT)
                    this.setRequestProperty("Authorization", "Basic $basicAuth")
                }
                println("URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    return response.toString()
                }
            }
        }
        fun getUserData(username: String) : String {
            return GET(apiURL = "http://${Config.Settings.SERVER_ADDRESS}:${Config.Settings.PORT}/api/users/$username")
        }
    }
}