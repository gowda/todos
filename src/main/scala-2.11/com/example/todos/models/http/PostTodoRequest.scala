package com.example.todos.models.http

import javax.inject.Inject

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.twitter.finagle.http.Request
import org.joda.time.DateTime

@JsonIgnoreProperties(Array("request"))
case class PostTodoRequest(@Inject request: Request,
                           title: String,
                           description: String,
                           start: DateTime,
                           end: DateTime)
