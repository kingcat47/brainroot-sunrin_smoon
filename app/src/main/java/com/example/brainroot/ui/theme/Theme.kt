package com.example.brainroot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background            = Dark_Background,
    surface               = Dark_Surface,
    surfaceVariant        = Dark_SurfaceVariant,
    primary               = Dark_Primary,
    onPrimary             = Dark_Background,
    primaryContainer      = Dark_PrimaryContainer,
    onPrimaryContainer    = Dark_OnBright,
    secondary             = Dark_Secondary,
    onSecondary           = Dark_Background,
    secondaryContainer    = Dark_SecondaryContainer,
    onSecondaryContainer  = Dark_OnBright,
    tertiary              = Dark_Tertiary,
    onTertiary            = Dark_Background,
    tertiaryContainer     = Dark_TertiaryContainer,
    onTertiaryContainer   = Dark_OnBright,
    onBackground          = Dark_OnBright,
    onSurface             = Dark_OnBright,
    onSurfaceVariant      = Dark_OnMuted,
    outline               = Dark_Outline,
)

private val LightColorScheme = lightColorScheme(
    background            = Light_Background,
    surface               = Light_Surface,
    surfaceVariant        = Light_SurfaceVariant,
    primary               = Light_Primary,
    onPrimary             = Light_Surface,
    primaryContainer      = Light_PrimaryContainer,
    onPrimaryContainer    = Light_OnBright,
    secondary             = Light_Secondary,
    onSecondary           = Light_Surface,
    secondaryContainer    = Light_SecondaryContainer,
    onSecondaryContainer  = Light_OnBright,
    tertiary              = Light_Tertiary,
    onTertiary            = Light_Surface,
    tertiaryContainer     = Light_TertiaryContainer,
    onTertiaryContainer   = Light_OnBright,
    onBackground          = Light_OnBright,
    onSurface             = Light_OnBright,
    onSurfaceVariant      = Light_OnMuted,
    outline               = Light_Outline,
)

@Composable
fun BrainrootTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}