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
package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The Pepto brand logo: a rounded badge holding a fork/spoon dining glyph, followed by the
 * "Pepto" wordmark. Drawn entirely in Compose so it renders identically on Android and iOS
 * without any image assets.
 *
 * @param showWordmark when false, only the badge mark is shown (useful for compact placements)
 * @param onBrand when true, colours are tuned for placement on top of the brand-coloured header
 *  (white badge + white text); otherwise it uses the theme's primary colour.
 */
@Composable
fun PeptoLogo(
    modifier: Modifier = Modifier,
    badgeSize: Dp = 34.dp,
    wordmarkSize: Int = 22,
    showWordmark: Boolean = true,
    onBrand: Boolean = true,
) {
    val badgeColor = if (onBrand) Color.White else androidx.compose.material.MaterialTheme.colors.primary
    val glyphColor = if (onBrand) androidx.compose.material.MaterialTheme.colors.primary else Color.White
    val textColor = if (onBrand) Color.White else androidx.compose.material.MaterialTheme.colors.primary

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(badgeSize)
                .clip(RoundedCornerShape(percent = 30))
                .background(badgeColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Restaurant,
                contentDescription = "Pepto",
                tint = glyphColor,
                modifier = Modifier.size(badgeSize * 0.55f),
            )
        }

        if (showWordmark) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Pepto",
                color = textColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = wordmarkSize.sp,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.padding(bottom = 2.dp),
            )
        }
    }
}
