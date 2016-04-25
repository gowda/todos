package com.example.todo

import org.joda.time.DateTime

case class Todo(title: String,
                description: String,
                start: DateTime,
                end: DateTime)