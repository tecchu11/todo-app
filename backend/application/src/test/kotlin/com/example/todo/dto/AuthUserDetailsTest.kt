package com.example.todo.dto

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.AuthorityUtils

class AuthUserDetailsTest {

    companion object {
        private const val USER_ID = "1"
        private const val PASSWORD = "test-password"
        private const val ROLE = "USER"
        private const val EXPECTED_USERNAME = "1"
        private const val EXPECTED_PASSWORD = "test-password"
        private val EXPECTED_AUTHORITIES = AuthorityUtils.createAuthorityList("USER")
    }

    @Test
    fun `Verify userdetails mapping is exactly`() {
        val actual = AuthUserDetails(
            id = USER_ID,
            password = PASSWORD,
            role = ROLE,
        )

        actual.username `should be equal to` EXPECTED_USERNAME
        actual.password `should be equal to` EXPECTED_PASSWORD
        actual.authorities `should be equal to` EXPECTED_AUTHORITIES
        actual.isAccountNonExpired.shouldBeTrue()
        actual.isAccountNonLocked.shouldBeTrue()
        actual.isCredentialsNonExpired.shouldBeTrue()
        actual.isEnabled.shouldBeTrue()
    }
}
