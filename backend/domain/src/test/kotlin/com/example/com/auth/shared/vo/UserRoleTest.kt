package com.example.com.auth.shared.vo

import com.example.todo.auth.vo.UserRole
import org.amshove.kluent.shouldContainSame
import org.junit.jupiter.api.Test

class UserRoleTest {

    @Test
    fun `Verify role mapping being exactly`() {
        UserRole.roleMapping.shouldContainSame(
            mapOf(
                "ADMIN" to UserRole.ADMIN,
                "USER" to UserRole.USER
            )
        )
    }
}
