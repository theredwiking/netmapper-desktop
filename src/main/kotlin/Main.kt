import window.app
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val windowTitle: String by remember { mutableStateOf("Network mapper") };
    Window(title = windowTitle, onCloseRequest = ::exitApplication) {
        app();
    };
}
