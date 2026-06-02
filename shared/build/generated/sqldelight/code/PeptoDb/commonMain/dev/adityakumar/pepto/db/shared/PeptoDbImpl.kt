package dev.adityakumar.pepto.db.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.adityakumar.pepto.db.PeptoDb
import dev.adityakumar.pepto.db.PostsQueries
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<PeptoDb>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = PeptoDbImpl.Schema

internal fun KClass<PeptoDb>.newInstance(driver: SqlDriver): PeptoDb = PeptoDbImpl(driver)

private class PeptoDbImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), PeptoDb {
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
