package com.example.todo

import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class TodoStartupTest extends FeatureTest {
  override val server = new EmbeddedHttpServer(new TodoServer)

  "Todo Server" in {
    server.assertHealthy()
  }
}
