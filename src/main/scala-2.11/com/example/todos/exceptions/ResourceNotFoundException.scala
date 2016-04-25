package com.example.todos.exceptions

case class ResourceNotFoundException(message: String) extends Exception(message)
