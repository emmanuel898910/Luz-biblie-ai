package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = DarkPrimary,
  onPrimary = Color(0xFF1C1300),
  primaryContainer = Color(0xFF423100),
  onPrimaryContainer = GoldLight,
  secondary = DarkSecondary,
  onSecondary = Color(0xFF1E1B4B),
  secondaryContainer = Color(0xFF312E81),
  onSecondaryContainer = Color(0xFFE0E7FF),
  tertiary = DarkTertiary,
  onTertiary = Color(0xFF082F49),
  background = DarkBackground,
  onBackground = DarkOnBackground,
  surface = DarkSurface,
  onSurface = DarkOnSurface,
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = DarkOnSurfaceVariant,
  outline = CardBorderDark
)

private val LightColorScheme = lightColorScheme(
  primary = LightPrimary,
  onPrimary = Color.White,
  primaryContainer = GoldGlow,
  onPrimaryContainer = Color(0xFF423100),
  secondary = LightSecondary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFEEF2FF),
  onSecondaryContainer = Color(0xFF312E81),
  tertiary = LightTertiary,
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = LightOnBackground,
  surface = LightSurface,
  onSurface = LightOnSurface,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = LightOnSurfaceVariant,
  outline = CardBorderLight
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
