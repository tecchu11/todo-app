package com.example.todo.enumration

import kotlin.IllegalArgumentException

enum class TaskStatus(val statusId: String?) {

    BACKLOG("1"), OPEN("2"), DONE("3");

    companion object {
        fun of(result: String?): TaskStatus = values().find { it.statusId == result }
            ?: throw IllegalArgumentException("statusId = $result is not defined")
    }
}
