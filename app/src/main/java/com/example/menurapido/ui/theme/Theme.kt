// ui/theme/Theme.kt
package com.example.menurapido.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de colores personalizada coherente
private val LightColors = lightColorScheme(
    primary = Color(0xFF1E88E5),       // Azul principal
    onPrimary = Color.White,           // Texto sobre botones primarios
    secondary = Color(0xFFBBDEFB),     // Azul claro secundario
    onSecondary = Color.Black,         // Texto sobre botones secundarios
    background = Color(0xFFFFFFFF),    // Fondo general claro
    onBackground = Color(0xFF212121),  // Texto sobre fondo
    surface = Color.White,             // Color de superficies (cards, botones)
    onSurface = Color(0xFF212121)      // Texto sobre superficies
)

@Composable
fun MenuRapidoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(), // Puedes personalizarla si quieres
        content = content
    )
}
