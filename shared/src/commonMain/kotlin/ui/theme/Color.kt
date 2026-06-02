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
package ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Brand palette (Pepto)
val PeptoRed = Color(0xFFE23744)
val PeptoRedDark = Color(0xFFC1232F)
val PeptoOrange = Color(0xFFFF7E5F)
val RatingGreen = Color(0xFF267E3E)
val SaffronYellow = Color(0xFFF5A623)

// Light neutrals
val LightBackground = Color(0xFFF6F6F8)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF1C1C1E)
val LightSubtle = Color(0xFF6E6E73)

// Dark neutrals
val DarkBackground = Color(0xFF0F0F11)
val DarkSurface = Color(0xFF1B1B1F)
val DarkOnSurface = Color(0xFFF2F2F5)
val DarkSubtle = Color(0xFF9A9AA2)

val lightColors = lightColors(
    primary = PeptoRed,
    primaryVariant = PeptoRedDark,
    secondary = PeptoOrange,
    secondaryVariant = PeptoOrange,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = LightOnSurface,
    onSurface = LightOnSurface,
)

val darkColors = darkColors(
    primary = PeptoRed,
    primaryVariant = PeptoRedDark,
    secondary = PeptoOrange,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
)
