package com.example.todo.models

import org.joda.time.DateTime

case class Todo(title: String,
                description: String,
                start: DateTime,
                end: DateTime,
                id: Option[Long] = None)
