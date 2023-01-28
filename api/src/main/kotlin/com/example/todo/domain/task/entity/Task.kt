package com.example.todo.domain.task.entity

import com.example.todo.domain.task.vo.Status
import com.example.todo.domain.task.vo.TaskId
import java.time.Instant

/**
Indicate user owns task
 */
data class Task(
    /** User owns this task */
    val userId: Int,
    /** Indicate unique id of this task */
    val id: TaskId,
    /** Summary of this task */
    var summary: String,
    /** Description of task. Description is not required */
    var description: String?,
    /** Indicate status of task */
    var status: Status,
    /** Issued time of this task */
    val issuedTime: Instant,
    /** Last edited time of this task */
    var lastEditedTime: Instant,
) {
    companion object {
        /** Create new task */
        fun create(
            userId: Int,
            summary: String,
            description: String?,
        ): Task = Task(
            id = TaskId.create(),
            userId = userId,
            summary = summary,
            description = description,
            status = Status.OPEN,
            issuedTime = Instant.now(),
            lastEditedTime = Instant.now(),
        )

        /**
         Create existed task
         */
        fun from(
            id: TaskId,
            userId: Int,
            summary: String,
            description: String?,
            status: Status,
            issuedTime: Instant,
            lastEditedTime: Instant,
        ): Task = Task(
            id = id,
            userId = userId,
            summary = summary,
            description = description,
            status = status,
            issuedTime = issuedTime,
            lastEditedTime = lastEditedTime,
        )
    }

    /**
     * Edit summary
     */
    fun editSummary(newSummary: String) {
        summary = newSummary
        lastEditedTime = Instant.now()
    }

    /**
     * Edit description
     *
     * *NOTE:* If you call with null argument, the description is cleared
     */
    fun editDescription(newDescription: String?) {
        description = newDescription
        lastEditedTime = Instant.now()
    }

    fun editStatus(newStatus: Status) {
        status = newStatus
        lastEditedTime = Instant.now()
    }
}
