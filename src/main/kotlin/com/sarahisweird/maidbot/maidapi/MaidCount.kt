package com.sarahisweird.maidbot.maidapi

import kotlinx.serialization.Serializable

@Serializable
data class MaidCount(
    val all: Int,
    val sfw: Int,
    val lingerie: Int,
    val swimsuit: Int,
    val nsfw: Int,
)
