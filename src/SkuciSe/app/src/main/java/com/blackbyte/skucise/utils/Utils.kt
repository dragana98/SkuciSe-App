package com.blackbyte.skucise.utils

import android.os.Looper
import android.util.Log
import com.blackbyte.skucise.MainActivity
import com.squareup.okhttp.*
import java.net.URLEncoder
import kotlin.concurrent.thread
import com.squareup.okhttp.Response
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.OkHttpClient

enum class RequestType {
    REQUEST_POST,
    REQUEST_GET
}

class Utils {
    companion object Requests {
        private fun stringifySimpleObject(
            params: List<Pair<String, Any?>>
        ): String {
            var jsonParam = ""
            for (param in params) {
                if (param.second == null) {
                    jsonParam += ",\"${param.first}\":null"
                } else if (param.second is List<*>) {
                    if ((param.second as List<*>).lastIndex != -1) {
                        if ((param.second as List<*>)[0] is Pair<*, *>) {
                            jsonParam += ",\"${param.first}\":["
                            var jtmp: String = ""
                            @Suppress("UNCHECKED_CAST")
                            (param.second as List<*>).forEach {
                                jtmp += ", ${
                                    stringifySimpleObject(
                                        listOf(((it as Pair<String, Any?>)))
                                    )
                                }"
                            }
                            jtmp = jtmp.substring(startIndex = 1)
                            jsonParam += "$jtmp]"
                        } else {
                            var jtmp: String = ""
                            (param.second as List<*>).forEach { jtmp += ", ${if (it is String) "\"$it\"" else it}" }
                            jtmp = "[${jtmp.substring(startIndex = 1)}]"
                            jsonParam += ",\"${param.first}\":$jtmp"
                        }
                    }
                } else if (param.second is String) {
                    jsonParam += ",\"${param.first}\":\"${param.second}\""
                } else {
                    jsonParam += ",\"${param.first}\":${param.second}"
                }
            }
            // Remove leading ','
            if (params.lastIndex != -1) {
                jsonParam = "{${jsonParam.substring(startIndex = 1)}}"
            }

            return jsonParam
        }

        fun GET(
            apiURL: String,
            params: List<Pair<String, String>> = listOf(),
            includeAuthParams: Boolean = false,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            val task = fun() {
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
                } catch (e: Exception) {
                    onFinish(e.stackTraceToString(), 12163)
                }
            }
            if (Looper.getMainLooper().isCurrentThread) {
                thread {
                    task()
                }
            } else {
                task()
            }
        }

        fun POST(
            apiURL: String,
            params: List<Pair<String, Any?>> = listOf(),
            includeAuthParams: Boolean = false,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            val task = fun() {
                val JSON: MediaType = MediaType.parse("application/json; charset=utf-8")
                val client = OkHttpClient()

                var jsonParam = stringifySimpleObject(params)

                Log.d("ENCODED JSON", jsonParam);

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
                } catch (e: Exception) {
                    onFinish("", 12163)
                }
            }
            if (Looper.getMainLooper().isCurrentThread) {
                thread {
                    task()
                }
            } else {
                task()
            }
        }

        /*
        fun uploadFile(serverURL: String?, file: File): Boolean? {
            thread {
                val body = MultipartBuilder()
                    .addFormDataPart(
                        "file",
                        file.name,
                        RequestBody.create(MediaType.parse("media/type"), File(file.name))
                    )
                    .type(MultipartBuilder.FORM)
                    .build()

                val request: Request = Request.Builder()
                    .url("/path/to/your/upload")
                    .post(body)
                    .build()
            }

        }*/
        fun getUserData(onFinish: (body: String, responseCode: Int) -> Unit) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/users",
                onFinish = onFinish
            )
        }

        fun getBasicUserData(user_id: Int, onFinish: (body: String, responseCode: Int) -> Unit) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/users/basic/$user_id",
                onFinish = onFinish
            )
        }

        fun updateUserData(
            username: String?,
            password: String?,
            phone_number: String?,
            avatar_url: String?,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("username", username),
                    Pair("password", password),
                    Pair("phone_number", phone_number),
                    Pair("avatar_url", avatar_url)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/users/update",
                onFinish = onFinish
            )
        }

        fun addNewRealtor(
            natural_person: Int,
            name: String,
            corporate_address: String,
            website_url: String,
            avatar_url: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("natural_person", natural_person),
                    Pair("name", name),
                    Pair("corporate_address", corporate_address),
                    Pair("website_url", website_url),
                    Pair("avatar_url", avatar_url),
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/users/update",
                onFinish = onFinish
            )
        }

        fun getRealtorData(realtor_id: Int, onFinish: (body: String, responseCode: Int) -> Unit) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/realtors/$realtor_id",
                onFinish = onFinish
            )
        }

        fun getRealtorLegalEntityData(
            realtor_id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/realtors/legalEntity/$realtor_id",
                onFinish = onFinish
            )
        }

        fun addRemoveFavorite(
            property_ad_id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("property_ad_id", property_ad_id)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/users/update",
                onFinish = onFinish
            )
        }

        fun getAllFavorites(onFinish: (body: String, responseCode: Int) -> Unit) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/favorites",
                onFinish = onFinish
            )
        }

        fun addNewPropertyAd(
            name: String,
            realtor_id: Int,
            description: String,
            city: String,
            postal_code: String,
            street_address: String,
            leasable: Int,
            unified: Int,
            images: List<String>,
            amenities: List<String>,
            price: Int?,
            deposit: Int?,
            property_ad_realties: List<Pair<String, Any>>,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("name", name),
                    Pair("realtor_id", realtor_id),
                    Pair("description", description),
                    Pair("city", city),
                    Pair("postal_code", postal_code),
                    Pair("street_address", street_address),
                    Pair("leasable", leasable),
                    Pair("unified", unified),
                    Pair("images", images),
                    Pair("amenities", amenities),
                    Pair("price", price),
                    Pair("deposit", deposit),
                    Pair("property_ad_realties", property_ad_realties)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/listings",
                onFinish = onFinish
            )
        }

        fun getPropertyById(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/listings/$id",
                onFinish = onFinish
            )
        }

        fun getAllPropertyAds(
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/listings/all",
                onFinish = onFinish
            )
        }

        fun getAllPropertyAdsByRealtorId(
            realtor_id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/listings/all/$realtor_id",
                onFinish = onFinish
            )
        }

        fun getAllMessages(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/messages/$id",
                onFinish = onFinish
            )
        }

        fun getAllConversationPreviews(
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/messages/preview",
                onFinish = onFinish
            )
        }

        fun sendMessage(
            urcv: Int,
            contents: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("urcv", urcv),
                    Pair("contents", contents)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/messages/push",
                onFinish = onFinish
            )
        }

        fun markConversationAsRead(
            usnd: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("usnd", usnd)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/messages/markAsRead",
                onFinish = onFinish
            )
        }

        fun getAllReviewsRealtorToUser(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/reviews/user/$id",
                onFinish = onFinish
            )
        }

        fun getAllReviewsUsersToRealty(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/reviews/realty/$id",
                onFinish = onFinish
            )
        }

        fun addReviewRealtorToUser(
            user_id: Int,
            recommends: Int,
            contents: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("user_id", user_id),
                    Pair("recommends", recommends),
                    Pair("contents", contents)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/reviews/realtorToUser",
                onFinish = onFinish
            )
        }

        fun addReviewUserToRealty(
            property_ad_id: Int,
            stars: Int,
            contents: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("property_ad_id", property_ad_id),
                    Pair("stars", stars),
                    Pair("contents", contents)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/reviews/userToRealty",
                onFinish = onFinish
            )
        }

        fun addRealtorsResponse(
            id: Int,
            response: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("id", id),
                    Pair("response", response)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/reviews/realtorsResponse",
                onFinish = onFinish
            )
        }

        fun getAllAvailableTourDates(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/tourDates/all/$id",
                onFinish = onFinish
            )
        }

        fun getTourDatesFromNowOnward(
            id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            GET(
                includeAuthParams = true,
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/tourDates/$id",
                onFinish = onFinish
            )
        }

        fun scheduleATour(
            tour_id: Int,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("tour_id", tour_id)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/tourDates/reserve",
                onFinish = onFinish
            )
        }

        fun addAvailableTerm(
            property_ad_id: Int,
            date: String,
            onFinish: (body: String, responseCode: Int) -> Unit
        ) {
            POST(
                params = listOf(
                    Pair("property_ad_id", property_ad_id),
                    Pair("date", date)
                ),
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/tourDates",
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
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/auth/register",
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
                apiURL = "http://${Config.SERVER_ADDRESS}:${Config.PORT}/api/auth/login",
                onFinish = onFinish
            )
        }
    }
}