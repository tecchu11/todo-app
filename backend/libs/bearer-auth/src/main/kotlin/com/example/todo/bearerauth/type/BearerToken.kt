package com.example.todo.bearerauth.type

@JvmInline
value class BearerToken private constructor(
    val value: String,
) {
    companion object {
        internal const val PREFIX = "Bearer "
        private const val INDEX = PREFIX.length

        internal fun create(jws: String) = BearerToken("$PREFIX$jws")

        /**
         * Create from raw bearer token.
         *
         * When passed illegal bearer token, this method throw [IllegalArgumentException]
         */
        fun from(token: String) = BearerToken(token)
    }

    init {
        require(value.startsWith(PREFIX)) { "This is not bearer format." }
    }

    /**
     * Return jws(jwt token).
     *
     * You must verify jws with [com.example.todo.bearerauth.service.BearerTokenService].
     */
    internal val jws: String
        get() = value.substring(INDEX)
}
