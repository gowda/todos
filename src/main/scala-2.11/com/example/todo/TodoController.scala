package com.example.todo

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class TodoController @Inject()(todoService: TodoService) extends Controller {
  get("/") { request: Request =>
    todoService.getAll
  }
}
