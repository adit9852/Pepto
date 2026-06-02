/**
 * Copyright 2023 Aditya Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screen.home.HomeState
import ui.theme.PeptoOrange
import ui.util.foodMetaFor
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer

/**
 * Displays a modern food/restaurant card.
 *
 * @param isLoading If true, shimmer placeholders are shown in place of the card's contents
 * @param post Item to be displayed
 */
@Composable
fun PostCard(isLoading: Boolean, post: HomeState.Post, modifier: Modifier = Modifier) {
    val meta = foodMetaFor(post.id)

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
    ) {
        Column {
            Box {
                PostGraphicImage(
                    url = post.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )

                if (!isLoading) {
                    // Bottom gradient so the offer text stays legible over any image
                    Box(
                        Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    0.55f to Color.Transparent,
                                    1f to Color.Black.copy(alpha = 0.55f),
                                ),
                            ),
                    )

                    meta.offer?.let { offer ->
                        Text(
                            text = offer,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp),
                        )
                    }

                    Box(
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(34.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.92f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Add to favourites",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }

            Column(Modifier.fillMaxWidth().padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                    )
                    if (!isLoading) {
                        Spacer(Modifier.size(8.dp))
                        RatingBadge(meta.rating)
                    }
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = meta.cuisine,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )

                Spacer(Modifier.height(10.dp))

                if (!isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        InfoChip(icon = { tint ->
                            Icon(Icons.Filled.Schedule, null, tint = tint, modifier = Modifier.size(14.dp))
                        }, text = meta.deliveryTime)

                        InfoChip(icon = { tint ->
                            Icon(Icons.Filled.Place, null, tint = tint, modifier = Modifier.size(14.dp))
                        }, text = meta.distance)

                        Text(
                            text = "• ${meta.priceForTwo}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(icon: @Composable (Color) -> Unit, text: String) {
    val tint = PeptoOrange
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        icon(tint)
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
            fontWeight = FontWeight.SemiBold,
        )
    }
}
