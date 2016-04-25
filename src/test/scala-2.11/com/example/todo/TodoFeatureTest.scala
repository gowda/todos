package com.example.todo

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.server.FeatureTest
import org.joda.time.DateTime
import org.mockito.Mockito
import redis.clients.jedis.Jedis

class TodoFeatureTest extends FeatureTest {

  override protected def beforeAll() = {
    super.beforeAll()
    val client: Jedis = injector.instance[Jedis]
    client.flushAll()
  }

  override val server = new EmbeddedHttpServer(new TodoServer)
  val invalidJson = s"""{}"""
  val todoJson1 = s"""
                 |{
                 |   "title": "Test task",
                 |   "description": "Write tests for creating tasks",
                 |   "start": "${DateTime.now().plusDays(5).toString}",
                 |   "end": "${DateTime.now().plusDays(6).toString}"
                 |}
          """.stripMargin
  val todoJsons = List(
    s"""
       |{
       |   "title": "Test task - 1",
       |   "description": "Write tests for creating tasks - 1",
       |   "start": "${DateTime.now().plusDays(1).toString}",
       |   "end": "${DateTime.now().plusDays(2).toString}"
       |}
          """.stripMargin,
    s"""
       |{
       |   "title": "Test task - 2",
       |   "description": "Write tests for creating tasks - 2",
       |   "start": "${DateTime.now().plusDays(3).toString}",
       |   "end": "${DateTime.now().plusDays(4).toString}"
       |}
          """.stripMargin,
    s"""
       |{
       |   "title": "Test task - 3",
       |   "description": "Write tests for creating tasks - 3",
       |   "start": "${DateTime.now().plusDays(5).toString}",
       |   "end": "${DateTime.now().plusDays(6).toString}"
       |}
          """.stripMargin,
    s"""
       |{
       |   "title": "Test task - 4",
       |   "description": "Write tests for creating tasks - 4",
       |   "start": "${DateTime.now().plusDays(7).toString}",
       |   "end": "${DateTime.now().plusDays(8).toString}"
       |}
          """.stripMargin
  )

  "Server" should {
    "respond with '200 OK' for GET '/'" in {
      server.httpGet(path = "/", andExpect = Status.Ok)
    }

    "respond with empty list for GET '/', when nothing has been added" in {
      server.httpGet(path = "/", andExpect = Status.Ok, withJsonBody = "[]")
    }

    "respond with '201 Created' for POST '/'" in {
      server.httpPost(
        path = "/",
        postBody = todoJson1,
        andExpect = Status.Created)
    }

    "respond with created TODO for 'POST /'" in {
      val mockId = 123
      val client: Jedis = injector.instance[Jedis]
      Mockito.when(client.get(TodoIdCounter.CounterKey)).thenReturn(s"$mockId")

      val mapper = injector.instance[FinatraObjectMapper]
      val todo = mapper.parse[Todo](todoJson1)

      server.httpPost(
        path = "/",
        postBody = todoJson1,
        andExpect = Status.Created,
        withJsonBody = mapper.writeValueAsString(todo.copy(id = Some(mockId))))
    }

    "respond with '400 Bad Request' for POST '/', when json in invalid" in {
      server.httpPost(
        path = "/",
        postBody = invalidJson,
        andExpect = Status.BadRequest)
    }

    "respond with all todos for 'GET /'" in {
      for (todo <- todoJsons) {
        server.httpPost(
          path = "/",
          postBody = todo,
          andExpect = Status.Created)
      }

      server.httpGet(
        path = "/",
        andExpect = Status.Ok,
        withJsonBody = s"[$todoJson1, ${todoJsons.mkString(",")}]")
    }

  }
}
