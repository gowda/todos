package com.example.todos.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import org.mockito.Mockito
import redis.clients.jedis.Jedis

object JedisModule extends TwitterModule {
  @Singleton
  @Provides
  def provideJedis: Jedis = {
    Mockito.spy(new Jedis("localhost", 7295))
  }
}
