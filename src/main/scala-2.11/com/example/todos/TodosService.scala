package com.example.todos

import javax.inject.Inject

import com.example.todos.exceptions.ResourceNotFoundException
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import redis.clients.jedis.Jedis
import com.example.todos.models.TodosIdCounter.KeyPrefix
import com.example.todos.models.{Todo, TodosIdCounter}

class TodosService @Inject()(client: Jedis,
                             counter: TodosIdCounter,
                             mapper: FinatraObjectMapper)
  extends Logging {
  def getAll(): List[Todo] = {
    val keys = client.keys(s"$KeyPrefix*").toArray
    var todos: List[Todo] = List[Todo]()
    for (key <- keys) {
      val todoJson = client.get(key.toString)
      info(s"Key: $key")
      info(s"JSON read from redis: $todoJson")
      val todo: Todo = mapper.parse[Todo](todoJson)
      todos = todo :: todos
    }

    todos.sortWith(_.start isBefore _.start)
  }

  def add(todo: Todo): Todo = {
    val todoId = counter.next
    client.set(todoId, mapper.writeValueAsString(todo))

    todo.copy(id = Some(todoId.replace(KeyPrefix, "").toLong))
  }

  def delete(id: Long): Unit = {
    info(s"Deleting TODO with id: $id")
    val deletedKeysCount = client.del(s"$KeyPrefix$id")
    if (deletedKeysCount != 1) {
      info(s"Failed to delete $id. Possibly key not found")
      throw new ResourceNotFoundException(s"Key not present: $id")
    } else {
      info(s"Deleted TODO with id: $id")
    }
  }
}
