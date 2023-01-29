package com.example.todo.mysql.table

import com.example.todo.mysql.configuration.GenericEnum
import com.example.todo.task.vo.Status

enum class TaskStatus(private val statusId: String) : GenericEnum {

    OPEN("1") {
        override val domain: Status
            get() = Status.CLOSE
    },
    WIP("2") {
        override val domain: Status
            get() = Status.WIP
    },
    CLOSE("3") {
        override val domain: Status
            get() = Status.CLOSE
    };

    abstract val domain: Status
    override fun code(): String = statusId
}
