package dev.adityakumar.pepto.db

import kotlin.Long
import kotlin.String

public data class Posts(
  public val id: Long,
  public val title: String,
  public val author: String,
  public val body: String,
  public val imageUrl: String,
)
