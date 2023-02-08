package com.example.todo.presentation.config.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Objects of timezone for java time.
 */
internal object JavaTime {
    val jst: ZoneId = ZoneId.of("Asia/Tokyo")
}

/**
 * Instant to LocalDateTime.
 *
 * You can specify zone id(Default jst).
 */
fun Instant.toLocalDateTime(
    zoneId: ZoneId = JavaTime.jst
): LocalDateTime = LocalDateTime.ofInstant(this, zoneId)
