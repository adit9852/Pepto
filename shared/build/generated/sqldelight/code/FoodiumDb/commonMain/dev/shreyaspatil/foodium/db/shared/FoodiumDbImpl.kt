package dev.shreyaspatil.foodium.db.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.shreyaspatil.foodium.db.FoodiumDb
import dev.shreyaspatil.foodium.db.PostsQueries
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<FoodiumDb>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = FoodiumDbImpl.Schema

internal fun KClass<FoodiumDb>.newInstance(driver: SqlDriver): FoodiumDb = FoodiumDbImpl(driver)

private class FoodiumDbImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), FoodiumDb {
  override val postsQueries: PostsQueries = PostsQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE posts (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  title TEXT NOT NULL,
          |  author TEXT NOT NULL,
          |  body TEXT NOT NULL,
          |  imageUrl TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
