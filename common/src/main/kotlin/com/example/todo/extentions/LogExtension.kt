package com.example.todo.extentions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

interface LogExtension

@Suppress("unused")
inline fun <reified T : LogExtension> T.logger(): Logger =
    LoggerFactory.getLogger(getClassForLogging(T::class.java))

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> =
    javaClass.enclosingClass?.takeIf { it.kotlin.companionObject?.java == javaClass } ?: javaClass
