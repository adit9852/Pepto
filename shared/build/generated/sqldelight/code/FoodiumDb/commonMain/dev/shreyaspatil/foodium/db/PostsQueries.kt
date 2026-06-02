package dev.shreyaspatil.foodium.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class PostsQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    title: String,
    author: String,
    body: String,
    imageUrl: String,
  ) -> T): Query<T> = Query(1_639_709_507, arrayOf("posts"), driver, "Posts.sq", "selectAll",
      "SELECT * FROM posts") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  public fun selectAll(): Query<Posts> = selectAll { id, title, author, body, imageUrl ->
    Posts(
      id,
      title,
      author,
      body,
      imageUrl
    )
  }

  public fun <T : Any> findById(id: Long, mapper: (
    id: Long,
    title: String,
    author: String,
    body: String,
    imageUrl: String,
  ) -> T): Query<T> = FindByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  public fun findById(id: Long): Query<Posts> = findById(id) { id_, title, author, body, imageUrl ->
    Posts(
      id_,
      title,
      author,
      body,
      imageUrl
    )
  }

  public fun addPost(
    id: Long?,
    title: String,
    author: String,
    body: String,
    imageUrl: String,
  ) {
    driver.execute(1_911_776_415,
        """INSERT INTO posts (id, title, author, body, imageUrl) VALUES (?, ?, ?, ?, ?)""", 5) {
          bindLong(0, id)
          bindString(1, title)
          bindString(2, author)
          bindString(3, body)
          bindString(4, imageUrl)
        }
    notifyQueries(1_911_776_415) { emit ->
      emit("posts")
    }
  }

  public fun deleteAll() {
    driver.execute(-1_297_991_628, """DELETE FROM posts""", 0)
    notifyQueries(-1_297_991_628) { emit ->
      emit("posts")
    }
  }

  private inner class FindByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("posts", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("posts", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-294_379_219, """SELECT * FROM posts WHERE id = ?""", mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "Posts.sq:findById"
  }
}
