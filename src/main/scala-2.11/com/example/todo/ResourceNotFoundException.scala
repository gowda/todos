package com.example.todo

case class ResourceNotFoundException(message: String) extends Exception(message)
