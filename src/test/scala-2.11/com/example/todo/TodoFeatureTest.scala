package com.example.todo

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class TodoFeatureTest extends FeatureTest {
  override val server = new EmbeddedHttpServer(new TodoServer)

  "Server" should {
    "respond with '200 OK' for GET '/'" in {
      server.httpGet(path = "/", andExpect = Status.Ok)
    }

    "respond with empty list for GET '/', when nothing has been added" in {
      server.httpGet(path = "/", andExpect = Status.Ok, withJsonBody = "[]")
    }
  }
}
