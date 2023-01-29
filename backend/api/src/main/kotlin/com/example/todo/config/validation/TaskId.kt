package com.example.todo.config.validation

import com.github.guepardoapps.kulid.ULID
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [TaskIdValidator::class])
annotation class TaskID(
    val message: String = "Task is is must be ulid format",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class TaskIdValidator : ConstraintValidator<TaskID, String> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?
    ): Boolean = ULID.isValid(value)
}
