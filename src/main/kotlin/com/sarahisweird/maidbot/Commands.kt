package com.sarahisweird.maidbot

import dev.kord.common.Color
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import me.jakejmattson.discordkt.commands.commands

@Suppress("unused")
fun maidCommands() = commands("Maids") {
    slash("maid", "Get a maid!") {
        execute {
            val client = HttpClient()

            val response: HttpResponse = client.head("https://maid.ws/maids")
            if (response.status != HttpStatusCode.OK) {
                respond("Couldn't fetch an image (status code ${response.status})")
                return@execute
            }

            val redirectLocation = response.call.request.url.toString()

            respondPublic {
                title = "Maid Bot"
                color = Color(0) // black

                image = redirectLocation

                footer {
                    text = "Current maid count: 1576 | https://maid.ws/"
                }
            }
        }
    }
}
