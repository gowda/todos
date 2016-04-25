package com.example.todos

import com.example.todos.exceptions.ResourceNotFoundExceptionMapper
import com.example.todos.modules.JedisModule
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.internal.exceptions.ExceptionManager
import com.twitter.finatra.http.routing.HttpRouter

class TodosServer extends HttpServer {
  override def modules = Seq(JedisModule)

  override def configureHttp(router: HttpRouter): Unit = {
    val exceptionManager = injector.instance[ExceptionManager]
    exceptionManager.add[ResourceNotFoundExceptionMapper]

    router
      .filter[CommonFilters]
      .add[TodosController]
  }
}
