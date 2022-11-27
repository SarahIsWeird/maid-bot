package com.sarahisweird.maidbot

import com.sarahisweird.maidbot.maidapi.MaidResponse
import com.sarahisweird.maidbot.maidapi.MaidService
import com.sarahisweird.maidbot.maidapi.MaidType
import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import me.jakejmattson.discordkt.commands.commands

@Suppress("unused")
fun maidCommands() = commands("Maids") {
    slash("maid", "Get a maid!") {
        execute {
            val maidResponse = MaidService.getMaidImage(MaidType.SAFE_FOR_WORK)

            if (maidResponse == null) {
                respond("Sorry, something went wrong.")
                return@execute
            }

            respondPublic {
                makeMaidResponse(MaidType.SAFE_FOR_WORK, maidResponse)
            }
        }
    }

    slash("lingerie", "Get a maid in lingerie!") {
        execute {
            val maidResponse = MaidService.getMaidImage(MaidType.LINGERIE)

            if (maidResponse == null) {
                respond("Sorry, something went wrong.")
                return@execute
            }

            respondPublic {
                makeMaidResponse(MaidType.LINGERIE, maidResponse)
            }
        }
    }
}

private fun EmbedBuilder.makeMaidResponse(maidType: MaidType, maidResponse: MaidResponse) {
    title = "Maid Bot"
    color = Color(0) // black

    image = maidResponse.files.first()

    footer {
        text = "Current ${maidType.displayName} maid count: ${maidResponse.availableFiles} | https://maid.ws/"
    }
}
