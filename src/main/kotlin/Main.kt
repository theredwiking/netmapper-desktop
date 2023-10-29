import window.app
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val windowTitle: String by remember { mutableStateOf("Network mapper") };
    Window(title = windowTitle, onCloseRequest = ::exitApplication, icon = painterResource("icon.png")) {
        app();
    };
}
