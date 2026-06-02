/**
 * Copyright 2023 Shreyas Patil
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
package ui.screen.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.ErrorContent
import ui.component.PostGraphicImage
import ui.component.RatingBadge
import ui.theme.PeptoOrange
import ui.theme.RatingGreen
import ui.theme.SaffronYellow
import ui.util.FoodMeta
import ui.util.foodMetaFor
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer
import kotlin.math.max
import kotlin.math.min

@Composable
fun PostDetailScreen(viewModel: PostDetailViewModel, onNavigateUp: () -> Unit) {
    val state by viewModel.state.collectAsState()

    PostDetailContent(
        isLoading = state.isLoading,
        post = state.post,
        errorMessage = state.errorMessage,
        onBackClick = onNavigateUp,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailContent(
    isLoading: Boolean,
    post: PostDetailState.Post?,
    errorMessage: String?,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val meta = post?.let { foodMetaFor(it.id) }

    Scaffold(
        topBar = {
            TopBarContent(
                headerImageUrl = post?.imageUrl,
                title = post?.title ?: "",
                scrollState = scrollState,
                onBackClick = onBackClick,
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
            )
        },
        bottomBar = {
            if (!isLoading && errorMessage == null && meta != null) {
                AddToCartBar(price = meta.priceForTwo)
            }
        },
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val bodyMinHeight = remember(maxHeight) { maxHeight + 250.dp }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(it),
            ) {
                if (errorMessage != null) {
                    ErrorContent(errorMessage)
                } else {
                    PostDetailBody(
                        isLoading = isLoading,
                        title = post?.title ?: "",
                        author = post?.author ?: "",
                        content = post?.content ?: "",
                        meta = meta,
                        modifier = Modifier.padding(16.dp).heightIn(min = bodyMinHeight),
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBarContent(
    headerImageUrl: String?,
    title: String,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    modifier: Modifier,
) {
    val imageHeight by animateSizePerScrollState(280.dp, scrollState)
    val alphaPerScroll by animateAlphaPerScrollState(scrollState)

    Box(modifier.fillMaxWidth()) {
        PostGraphicImage(
            url = headerImageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                },
        )

        TopAppBar(
            title = {
                if (imageHeight < 68.dp) {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.alpha(alphaPerScroll),
                        color = Color.White,
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = alphaPerScroll),
            elevation = 0.dp,
            navigationIcon = {
                CircleIconButton(Icons.Default.ArrowBackIosNew, "Navigate back", onBackClick)
            },
            actions = {
                CircleIconButton(Icons.Default.FavoriteBorder, "Add to favourites") {}
            },
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun CircleIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Box(
            Modifier.size(36.dp).clip(RoundedCornerShape(50)).background(Color.Black.copy(alpha = 0.35f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription, tint = Color.White, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun PostDetailBody(
    isLoading: Boolean,
    title: String,
    author: String,
    content: String,
    meta: FoodMeta?,
    modifier: Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth().placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )

        if (meta != null) {
            Text(
                text = meta.cuisine,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            )

            // Rating summary strip
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                RatingBadge(meta.rating)
                Text(
                    "${meta.ratingCount} ratings",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                )
                if (meta.isPureVeg) {
                    Text(
                        "PURE VEG",
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = RatingGreen,
                    )
                }
            }

            InfoStrip(meta)

            meta.offer?.let { OfferBanner(it) }
        }

        Text(
            text = "About",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = "~ by $author",
            style = MaterialTheme.typography.caption,
            color = PeptoOrange,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.85f),
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )
    }
}

@Composable
private fun InfoStrip(meta: FoodMeta) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.08f)),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            InfoStripItem(Icons.Filled.Schedule, meta.deliveryTime, "Delivery")
            Divider()
            InfoStripItem(Icons.Filled.Place, meta.distance, "Distance")
            Divider()
            InfoStripItem(Icons.Filled.Payments, meta.priceForTwo.removePrefix("₹").substringBefore(" "), "₹ for two")
        }
    }
}

@Composable
private fun Divider() {
    Box(Modifier.width(1.dp).height(34.dp).background(MaterialTheme.colors.onSurface.copy(alpha = 0.10f)))
}

@Composable
private fun InfoStripItem(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = PeptoOrange, modifier = Modifier.size(20.dp))
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.subtitle2)
        Text(
            label,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        )
    }
}

@Composable
private fun OfferBanner(offer: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = SaffronYellow.copy(alpha = 0.15f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(Icons.Filled.LocalOffer, contentDescription = null, tint = SaffronYellow, modifier = Modifier.size(20.dp))
            Column {
                Text(offer, style = MaterialTheme.typography.subtitle2, fontWeight = FontWeight.Bold)
                Text(
                    "Apply at checkout",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                )
            }
        }
    }
}

@Composable
private fun AddToCartBar(price: String) {
    Surface(elevation = 16.dp, color = MaterialTheme.colors.surface) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(Modifier.weight(1f)) {
                Text("Total", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
                Text(price.removeSuffix(" for two"), style = MaterialTheme.typography.h6)
            }
            Button(
                onClick = { },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 28.dp, vertical = 12.dp),
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Add to cart", color = Color.White)
            }
        }
    }
}

@Composable
fun animateSizePerScrollState(
    initialSize: Dp,
    scrollState: ScrollState,
): State<Dp> = produceState(initialSize, key1 = scrollState.value) {
    value = Dp(max(0f, initialSize.value - (scrollState.value / 2f)))
}

@Composable
fun animateAlphaPerScrollState(
    scrollState: ScrollState,
): State<Float> = produceState(0f, key1 = scrollState.value) {
    value = min(1f, (scrollState.value / 600f))
}
