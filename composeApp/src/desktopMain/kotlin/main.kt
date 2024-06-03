import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import goryachkovskiy.danil.mtuci.kmm.App
import goryachkovskiy.danil.mtuci.kmm.di.initKoin

fun main() = application {
    initKoin {}
    Window(
        title = "Rick N Morty KMM",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}