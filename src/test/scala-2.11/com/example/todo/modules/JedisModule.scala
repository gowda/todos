package com.example.todo.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import redis.clients.jedis.Jedis

object JedisModule extends TwitterModule {
  @Singleton
  @Provides
  def provideJedis: Jedis = {
    new Jedis("localhost", 7295)
  }
}
