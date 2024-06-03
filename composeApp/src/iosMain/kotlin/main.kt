import androidx.compose.ui.window.ComposeUIViewController
import goryachkovskiy.danil.mtuci.kmm.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
