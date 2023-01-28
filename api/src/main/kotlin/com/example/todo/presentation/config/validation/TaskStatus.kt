package com.example.todo.presentation.config.validation

import com.example.todo.domain.task.vo.Status
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [TaskStatusValidator::class])
annotation class TaskStatus(
    val message: String = "Supported task status are OPEN, WIP, CLOSE",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class TaskStatusValidator : ConstraintValidator<TaskStatus, String> {
    companion object {
        private val statusMap: Map<String, Status> = Status.values().associateBy { it.name }
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (statusMap[value] == null) {
            return false
        }
        return true
    }
}