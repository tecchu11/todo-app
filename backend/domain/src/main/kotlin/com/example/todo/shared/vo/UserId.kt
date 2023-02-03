package com.example.todo.shared.vo

/**
 * Indicate user id.
 */
@JvmInline
value class UserId private constructor(
    val value: Int
) {
    init {
        require(value > 0)
    }

    companion object {

        /**
         * Create from raw value.
         */
        fun from(raw: Int): UserId = UserId(raw)
    }

    /**
     * Returning literal user id.
     */
    val literal: String
        get() = value.toString()
}
