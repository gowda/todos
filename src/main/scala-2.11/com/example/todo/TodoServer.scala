package com.example.todo

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

class TodoServer extends HttpServer {
  override def configureHttp(router: HttpRouter): Unit = {
    router.add[TodoController]
  }
}
