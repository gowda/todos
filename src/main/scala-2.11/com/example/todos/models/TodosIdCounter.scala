package com.example.todos.models

import javax.inject.{Inject, Singleton}

import redis.clients.jedis.Jedis

import com.example.todos.models.TodosIdCounter._

object TodosIdCounter {
  val InitialValue = 10000000L
  val CounterKey = "com.example.todo.counter"
  val KeyPrefix = "com.example.todo.ID-"
}

@Singleton
class TodosIdCounter @Inject()(client: Jedis) {
  def next: String = {
    val current: Long = Option(client.get(CounterKey)) match {
      case Some(value) => value.toLong
      case None => InitialValue
    }

    val nextValue = current + 1
    client.set(CounterKey, s"${nextValue.toString}")
    s"$KeyPrefix$current"
  }
}

