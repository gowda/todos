package com.example.todo

import javax.inject.Inject

import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Logging
import redis.clients.jedis.Jedis
import com.example.todo.TodoIdCounter.KeyPrefix

class TodoService @Inject()(client: Jedis,
                            counter: TodoIdCounter,
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

    todos
  }

  def add(todo: Todo): Todo = {
    val todoId = counter.next
    client.set(todoId, mapper.writeValueAsString(todo))

    todo.copy(id = Some(todoId.replace(KeyPrefix, "").toLong))
  }
}
