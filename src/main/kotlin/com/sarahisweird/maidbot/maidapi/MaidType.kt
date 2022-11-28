package com.sarahisweird.maidbot.maidapi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = MaidTypeSerializer::class)
enum class MaidType(val apiName: String, val displayName: String) {
    ALL("all", "Total"),
    SAFE_FOR_WORK("sfw", "SFW"),
    LINGERIE("lingerie", "Lingerie"),
    SWIMSUIT("swimsuit", "Swimsuit"),
    NOT_SAFE_FOR_WORK("nsfw", "NSFW"),
    BONDAGE("bondage", "Bondage"),
    TENTACLE("tentacle", "Tentacle");

    companion object {
        fun getMaidTypeFromApiName(apiName: String) =
            when (apiName) {
                "all" -> ALL
                "sfw" -> SAFE_FOR_WORK
                "lingerie" -> LINGERIE
                "swimsuit" -> SWIMSUIT
                "bondage" -> BONDAGE
                "tentacle" -> TENTACLE
                else -> throw IllegalArgumentException("Invalid maid type")
            }
    }
}

object MaidTypeSerializer : KSerializer<MaidType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MaidType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: MaidType) {
        encoder.encodeString(value.apiName)
    }

    override fun deserialize(decoder: Decoder) =
        MaidType.getMaidTypeFromApiName(decoder.decodeString())
}
