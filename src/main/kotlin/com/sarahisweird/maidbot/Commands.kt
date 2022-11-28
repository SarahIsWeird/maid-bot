package com.sarahisweird.maidbot

import com.sarahisweird.maidbot.maidapi.MaidCount
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
import me.jakejmattson.discordkt.NoArgs
import me.jakejmattson.discordkt.commands.GuildSlashCommandEvent
import me.jakejmattson.discordkt.commands.commands

@Suppress("unused")
fun maidCommands() = commands("Maids") {
    slash("maid", "Get a maid!") {
        execute {
            sendMaid(MaidType.SAFE_FOR_WORK, checkNsfw = false)
        }
    }

    slash("lingerie", "Get a maid in lingerie!") {
        execute {
            sendMaid(MaidType.LINGERIE)
        }
    }

    slash("swimsuit", "Get a maid in a swimsuit!") {
        execute {
            sendMaid(MaidType.SWIMSUIT)
        }
    }

    slash("nsfw", "Get a NSFW maid!") {
        execute {
            sendMaid(MaidType.NOT_SAFE_FOR_WORK)
        }
    }

    slash("bondage", "Get a roped up maid!") {
        execute {
            sendMaid(MaidType.BONDAGE)
        }
    }
}

private suspend fun GuildSlashCommandEvent<NoArgs>.sendMaid(maidType: MaidType, checkNsfw: Boolean = true) {
    if (checkNsfw && !isInNsfwChannel()) {
        respond("Sorry, but you can only use this command in an age-restricted channel.")
        return
    }

    val maidResponse = MaidService.getMaidImage(maidType)
    val maidCount = MaidService.getMaidCount()

    if (maidResponse == null || maidCount == null) {
        respond("Sorry, something went wrong.")
        return
    }

    respondPublic {
        makeMaidResponse(maidType, maidResponse, maidCount)
    }
}

private fun GuildSlashCommandEvent<NoArgs>.isInNsfwChannel() =
    channel.data.nsfw.orElse(false)

private fun EmbedBuilder.makeMaidResponse(maidType: MaidType, maidResponse: MaidResponse, maidCount: MaidCount) {
    title = "Maid Bot"
    color = Color(0) // black

    image = maidResponse.files.first()

    footer {
        text = "${maidType.displayName} maids: ${maidResponse.availableFiles}" +
                " | Total maids: ${maidCount.all} | https://maid.ws/"
    }
}
