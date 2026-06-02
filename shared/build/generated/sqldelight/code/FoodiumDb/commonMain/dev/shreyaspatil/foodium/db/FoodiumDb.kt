package dev.shreyaspatil.foodium.db

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.shreyaspatil.foodium.db.shared.newInstance
import dev.shreyaspatil.foodium.db.shared.schema
import kotlin.Unit

public interface FoodiumDb : Transacter {
  public val postsQueries: PostsQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = FoodiumDb::class.schema

    public operator fun invoke(driver: SqlDriver): FoodiumDb = FoodiumDb::class.newInstance(driver)
  }
}
