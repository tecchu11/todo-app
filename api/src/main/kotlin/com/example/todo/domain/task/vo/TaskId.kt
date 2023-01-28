package com.example.todo.domain.task.vo

import com.github.guepardoapps.kulid.ULID

@JvmInline
value class TaskId(
    private val value: String
) {
    init {
        require(ULID.isValid(value)) {
            "Illegal task_id = $value was passed"
        }
    }

    /**
     return literal task id
     */
    val literal: String
        get() = value

    companion object {
        /**
         Create new task id
         */
        fun create() = TaskId(ULID.random())

        /**
         Create task id instance from literal
         */
        fun from(literal: String): TaskId = TaskId(ULID.fromString(literal))
    }
}
