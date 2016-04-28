package com.example.todos.models

import java.sql.Timestamp

case class Todo(id: Option[Long] = None,
                title: String,
                description: String,
                start: Timestamp,
                end: Timestamp)
