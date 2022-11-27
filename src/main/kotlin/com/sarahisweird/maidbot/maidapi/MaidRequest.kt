package com.sarahisweird.maidbot.maidapi

import kotlinx.serialization.Serializable

@Serializable
data class MaidRequest(
    val type: MaidType,
    val amount: Int,
)
