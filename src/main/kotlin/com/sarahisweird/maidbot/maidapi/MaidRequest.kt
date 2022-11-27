package com.sarahisweird.maidbot.maidapi

import kotlinx.serialization.Serializable

@Serializable
data class MaidRequest(
    val category: MaidType,
    val amount: Int,
)
