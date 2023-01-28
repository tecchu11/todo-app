package com.example.todo.presentation.config.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal object TimeZone {
    val jst: ZoneId = ZoneId.of("Asia/Tokyo")
}

/**
 * Instant to LocalDateTime
 *
 * You can specify zone id(Default jst)
 */
fun Instant.toLocalDateTime(
    zoneId: ZoneId = TimeZone.jst
): LocalDateTime = LocalDateTime.ofInstant(this, zoneId)
