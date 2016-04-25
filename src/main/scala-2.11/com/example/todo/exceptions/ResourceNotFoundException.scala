package com.example.todo.exceptions

case class ResourceNotFoundException(message: String) extends Exception(message)
