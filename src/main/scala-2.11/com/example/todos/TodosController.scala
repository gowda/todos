package com.example.todos

import javax.inject.Inject

import com.example.todos.models.Todo
import com.example.todos.models.http.{DeleteTodoRequest, PostTodoRequest}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.example.todos.DB.db

class TodosController @Inject()(todoService: TodosService) extends Controller {
  get("/") { request: Request =>
    todoService.getAll
  }

  post("/") { request: PostTodoRequest =>
    val todo = todoService.add(
      Todo(None, request.title, request.description, request.start, request.end))

    response.created(todo)
  }

  delete("/:id") { request: DeleteTodoRequest =>
    info(s"Processing DELETE for ${request.id}")
    todoService.delete(request.id)
    response.ok
  }
}
