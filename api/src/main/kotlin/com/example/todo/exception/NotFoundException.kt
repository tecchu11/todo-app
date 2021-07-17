package com.example.todo.exception

import java.lang.RuntimeException

class NotFoundException (message: String): RuntimeException(message){
}