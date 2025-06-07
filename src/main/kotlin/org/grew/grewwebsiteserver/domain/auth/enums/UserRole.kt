package org.grew.grewwebsiteserver.domain.auth.enums

enum class UserRole {
    USER, ADMIN;

    companion object {
        fun from(value: String): UserRole {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid role: $value. Must be 'USER' or 'ADMIN'")
            }
        }
    }
}