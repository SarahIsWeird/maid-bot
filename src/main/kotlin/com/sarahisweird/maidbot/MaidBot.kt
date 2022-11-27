package com.sarahisweird.maidbot

import dev.kord.common.annotation.KordPreview
import me.jakejmattson.discordkt.dsl.bot

@OptIn(KordPreview::class)
fun main() {
    val token = System.getenv("MAID_BOT_TOKEN") ?: error("Please set the MAID_BOT_TOKEN environment variable.")

    bot(token) {
        configure {
            dualRegistry = false
        }
    }
}
