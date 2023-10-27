package window

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import file.SaveData
import window.vlan.subnetDisplay

@Composable
@Preview
fun app() {
    var scan: Boolean by rememberSaveable { mutableStateOf(false) };
    var data: SaveData by rememberSaveable { mutableStateOf(SaveData(emptyList(), emptyList())) };
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = Color(0xFF1D1E2C)
                ) {
                    navBar(onStartChange = { scan = it }, onData = { data = it; scan = true });
                }
            },
            backgroundColor = Color(0xFF1D1E2C)
        ){
            Row(modifier = Modifier.fillMaxWidth()) {
                subnetDisplay(scan, data);
            }
        }
    };
}