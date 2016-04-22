package com.example.todo

class TodoService {
  var todos: List[Todo] = List()

  def getAll(): List[Todo] = {
    todos
  }

  def add(request: PostTodoRequest): Todo = {
    val todo = Todo(request.title, request.description, request.start, request.end)
    todos ++= List(todo)
    todo
  }
}
