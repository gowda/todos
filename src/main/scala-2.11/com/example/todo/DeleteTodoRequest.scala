package com.example.todo

import javax.inject.Inject

import com.twitter.finagle.http.Request
import com.twitter.finatra.request.RouteParam

case class DeleteTodoRequest(@Inject request: Request, @RouteParam id: Long)
