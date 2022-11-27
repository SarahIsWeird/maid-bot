@file:Suppress("MemberVisibilityCanBePrivate")

package com.sarahisweird.maidbot.maidapi

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

private const val API_URL = "https://maid.ws/api"

object MaidService {
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getMaidImage(maidType: MaidType) =
        getMaidImages(maidType, 1)

    suspend fun getMaidImages(maidType: MaidType, amount: Int): MaidResponse? {
        val response: HttpResponse = client.post("$API_URL/getMaid") {
            contentType(ContentType.Application.Json)
            setBody(MaidRequest(maidType, amount))
        }

        if (response.status != HttpStatusCode.OK) {
            return null
        }

        val maidResponse: MaidResponse = try {
            response.body()
        } catch (e: Exception) {
            return null
        }

        if (maidResponse.files.isEmpty()) {
            return null
        }

        return maidResponse
    }

    suspend fun getMaidCount(): MaidCount? {
        val response: HttpResponse = client.get("$API_URL/stats")

        if (response.status != HttpStatusCode.OK) {
            return null
        }

        return try {
            response.body()
        } catch (e: Exception) {
            null
        }
    }
}
