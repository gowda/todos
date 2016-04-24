package com.example.todo

import com.example.todo.modules.JedisModule
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, ExceptionMappingFilter}
import com.twitter.finatra.http.routing.HttpRouter

class TodoServer extends HttpServer {
  override def modules = Seq(JedisModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[TodoController]
  }
}
