package goryachkovskiy.danil.mtuci.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth.AuthScreen
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Navigator(AuthScreen())
}

