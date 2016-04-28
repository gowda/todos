package com.example.todos.models.http

import java.sql.Timestamp
import javax.inject.Inject

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.twitter.finagle.http.Request

@JsonIgnoreProperties(Array("request"))
case class PostTodoRequest(@Inject request: Request,
                           title: String,
                           description: String,
                           start: Timestamp,
                           end: Timestamp)
