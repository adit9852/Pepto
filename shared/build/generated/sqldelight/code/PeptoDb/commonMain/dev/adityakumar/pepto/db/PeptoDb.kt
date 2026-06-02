package dev.adityakumar.pepto.db

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.adityakumar.pepto.db.shared.newInstance
import dev.adityakumar.pepto.db.shared.schema
import kotlin.Unit

public interface PeptoDb : Transacter {
  public val postsQueries: PostsQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = PeptoDb::class.schema

    public operator fun invoke(driver: SqlDriver): PeptoDb = PeptoDb::class.newInstance(driver)
  }
}
