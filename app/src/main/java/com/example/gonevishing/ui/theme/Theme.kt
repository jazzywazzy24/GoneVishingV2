package com.example.gonevishing.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

//we dont want a light AND dark mode and will just force one theme

private val GvColorScheme = lightColorScheme(
    primary = GvPrimary,
    secondary = GvSecondary,
    tertiary = GvTertiary,

    background = GvBackground,
    onBackground = GvOnSurface,
    surface = GvSurface,
    onSurface = GvOnSurface
)

@Composable
fun GoneVishingTheme( content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GvColorScheme,
        typography = Typography,
        content = content
    )
}
