package com.github.saikcaskey.pokertracker.ui_compose.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.materialkolor.DynamicMaterialExpressiveTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.rememberDynamicMaterialThemeState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
        MaterialTheme(
            colorScheme = rememberDynamicColorScheme(
                style = PaletteStyle.Expressive,
                isDark = useDarkTheme,
                contrastLevel = 0.5,
                specVersion = ColorSpec.SpecVersion.SPEC_2025,
                primary = Color(0xFFF44336),
                secondary = Color(0xFF0D88FF),
                tertiary = Color(0xFFEFFFF7),
                error = Color(0xFFCB495F),
                neutral = Color(0xFF0D88FF),
                neutralVariant = Color(0xFFFF174E),
            ),
            content = content,
        )
    } else {
        val dynamicThemeState = rememberDynamicMaterialThemeState(
            isDark = useDarkTheme,
            style = PaletteStyle.Expressive,
            contrastLevel = 0.5,
            specVersion = ColorSpec.SpecVersion.SPEC_2025,
            primary = Color(0xFFF44336),
            secondary = Color(0xFF0D88FF),
            tertiary = Color(0xFFEFFFF7),
            error = Color(0xFFCB495F),
            neutral = Color(0xFF0D88FF),
            neutralVariant = Color(0xFFFF174E),
        )

        DynamicMaterialExpressiveTheme(
            state = dynamicThemeState,
            motionScheme = MotionScheme.expressive(),
            animate = true,
            content = content,
        )
    }
}
