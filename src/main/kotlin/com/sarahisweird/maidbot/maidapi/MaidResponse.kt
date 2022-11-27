package com.sarahisweird.maidbot.maidapi

import kotlinx.serialization.Serializable

@Serializable
data class MaidResponse(
    val availableFiles: Int,
    val files: @Serializable List<String>,
)
