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
package ui.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.ErrorContent
import ui.component.PopularCard
import ui.component.PostCard
import ui.theme.PeptoOrange
import ui.theme.PeptoRed
import ui.theme.LocalUiModePreferenceController
import ui.theme.UiMode
import ui.theme.rememberUiMode
import utils.navigation.rememberInNavStack

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    HomeContent(
        isLoading = state.isLoading,
        posts = state.posts,
        errorMessage = state.errorMessage,
        onNavigateToDetail = onNavigateToDetail,
        onRefresh = viewModel::refresh,
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    errorMessage: String?,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { PeptoBottomBar() },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            if (errorMessage != null) {
                Column(Modifier.fillMaxSize()) {
                    LocationHeader()
                    ErrorContent(errorMessage)
                }
            } else {
                Crossfade(isLoading, animationSpec = tween(500)) { loading ->
                    PostListContent(
                        isLoading = loading,
                        posts = posts,
                        onNavigateToDetail = onNavigateToDetail,
                        onRefresh = onRefresh,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PostListContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = androidx.compose.material.pullrefresh.rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh,
    )

    val listState = rememberInNavStack(
        key = "scrollState-$isLoading",
        compute = { LazyListState() },
    )

    val items = if (isLoading) loadingPostCards else posts
    val popular = items.take(6)

    Box(Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item(key = "header") { LocationHeader() }
            item(key = "search") { SearchBar() }
            item(key = "categories") { CategoryRow() }

            item(key = "popular-title") {
                SectionHeader(title = "Popular near you", subtitle = "Trending spots this week")
            }
            item(key = "popular-row") {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(popular, key = { "popular-${it.id}" }) { post ->
                        PopularCard(
                            post = post,
                            modifier = Modifier.clickable(enabled = !isLoading) {
                                onNavigateToDetail(post.id)
                            },
                        )
                    }
                }
            }

            item(key = "all-title") {
                SectionHeader(title = "All restaurants", subtitle = "${if (isLoading) "" else items.size} places delivering to you")
            }

            items(items = items, key = { it.id }) { post ->
                PostCard(
                    isLoading = isLoading,
                    post = post,
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        onNavigateToDetail(post.id)
                    },
                )
            }
        }

        androidx.compose.material.pullrefresh.PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary,
        )
    }
}

@Composable
private fun LocationHeader() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(PeptoRed, PeptoOrange))),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Deliver to", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                    Icon(Icons.Filled.KeyboardArrowDown, null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Text(
                    "Home • Koramangala, Bengaluru",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )
            }

            val controller = LocalUiModePreferenceController.current
            val uiMode by rememberUiMode()
            IconButton(onClick = controller::toggle) {
                Icon(
                    imageVector = if (uiMode == UiMode.DARK) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Toggle theme",
                    tint = Color.White,
                )
            }
            Box(
                Modifier.size(38.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White)
            }
        }
    }
}

@Composable
private fun SearchBar() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 16.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 2.dp,
        color = MaterialTheme.colors.surface,
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
            Spacer(Modifier.width(10.dp))
            Text(
                "Search for restaurants, dishes...",
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

private data class FoodCategory(val label: String, val icon: ImageVector)

private val categories = listOf(
    FoodCategory("Pizza", Icons.Filled.LocalPizza),
    FoodCategory("Burger", Icons.Filled.LunchDining),
    FoodCategory("Noodles", Icons.Filled.RamenDining),
    FoodCategory("Thali", Icons.Filled.SetMeal),
    FoodCategory("Bakery", Icons.Filled.BakeryDining),
    FoodCategory("Coffee", Icons.Filled.LocalCafe),
    FoodCategory("Dessert", Icons.Filled.Icecream),
    FoodCategory("Drinks", Icons.Filled.LocalBar),
)

@Composable
private fun CategoryRow() {
    LazyRow(
        modifier = Modifier.padding(top = 20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(categories, key = { it.label }) { category ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        category.icon,
                        contentDescription = category.label,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(28.dp),
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    category.label,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp)) {
        Text(title, style = MaterialTheme.typography.h6)
        if (subtitle.isNotBlank()) {
            Text(
                subtitle,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.55f),
            )
        }
    }
}

private data class BottomTab(val label: String, val icon: ImageVector)

private val bottomTabs = listOf(
    BottomTab("Home", Icons.Filled.Home),
    BottomTab("Search", Icons.Filled.Search),
    BottomTab("Orders", Icons.Filled.Receipt),
    BottomTab("Profile", Icons.Filled.Person),
)

@Composable
private fun PeptoBottomBar() {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.primary,
        elevation = 12.dp,
    ) {
        bottomTabs.forEachIndexed { index, tab ->
            BottomNavigationItem(
                selected = index == 0,
                onClick = { },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label, fontSize = 11.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            )
        }
    }
}

/**
 * Dummy items for showing shimmer animation while data is loading
 */
private val loadingPostCards = List(6) { HomeState.Post(it, "", "", "") }
