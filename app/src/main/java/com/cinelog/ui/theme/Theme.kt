package com.cinelog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    secondary = GoldSecondary,
    tertiary = Color.White,
    background = DarkBg,
    surface = DarkSurface,
    onPrimary = DarkBg,
    onSecondary = DarkBg,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = GoldPrimary,
    surfaceVariant = DarkSelection,
    onSurfaceVariant = TextSecondary
)

@Composable
fun CineLogTheme(
    content: @Composable () -> Unit
) {
    val typography = Typography(
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp,
            color = GoldPrimary
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            color = TextSecondary,
            lineHeight = 22.sp
        )
    )

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = typography,
        content = content
    )
}
