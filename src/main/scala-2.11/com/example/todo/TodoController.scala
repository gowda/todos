package com.example.todo

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class TodoController @Inject()(todoService: TodoService) extends Controller {
  get("/") { request: Request =>
    todoService.getAll
  }

  post("/") { request: PostTodoRequest =>
    val todo = todoService.add(
      Todo(request.title, request.description, request.start, request.end))

    response.created(todo)
  }

  delete("/:id") { request: DeleteTodoRequest =>
    info(s"Processing DELETE for ${request.id}")
    todoService.delete(request.id)
    response.ok
  }
}
