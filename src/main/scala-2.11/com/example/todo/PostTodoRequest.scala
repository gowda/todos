package com.example.todo

import javax.inject.Inject

import com.twitter.finagle.http.Request
import org.joda.time.DateTime

case class PostTodoRequest(@Inject request: Request,
                           title: String,
                           description: String,
                           start: DateTime,
                           end: DateTime)
