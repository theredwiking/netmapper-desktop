package window.servers

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import network.ServerInfo
import androidx.compose.desktop.*

@Composable
fun serverWindow(server: ServerInfo) {
    var close: Boolean by remember { mutableStateOf(true) }
    Window(title = server.ip, onCloseRequest = { close = false}, visible = close) {
        MaterialTheme {
            Scaffold(
                backgroundColor = Color(0xFF1D1E2C)
            ){
                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                    Text(modifier = Modifier.padding(6.dp), color = Color.White, text = "Ip: ${server.ip}");
                    Column(modifier = Modifier.border(width = Dp.Hairline, shape = RoundedCornerShape(1.dp), color = Color.Black).verticalScroll(state = ScrollState(1), enabled = true).fillMaxHeight()) {
                        for (i in 0..server.ports.size) {
                            Text(modifier = Modifier.padding(6.dp), color = Color.White, text = " Port: ${
                                try {
                                    server.ports[i]
                                } catch (e: Exception) {
                                    continue;
                                }
                            }");
                        }
                    }
                }
            }
        };
    }
}