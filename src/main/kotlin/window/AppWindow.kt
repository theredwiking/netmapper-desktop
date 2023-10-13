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
    var machine: String by rememberSaveable { mutableStateOf("No information") };
    var scan: Boolean by rememberSaveable { mutableStateOf(false) };
    var data: SaveData by rememberSaveable { mutableStateOf(SaveData(emptyList(), emptyList())) };
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = Color(0xFF1D1E2C)
                ) {
                    navBar(onMachineChange = { machine = it }, onStartChange = { scan = it }, onData = { data = it });
                }
            },
            backgroundColor = Color(0xFF7E8287)
        ){
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(modifier = Modifier.width(200.dp).padding(2.dp), text = machine)
                    Text(modifier = Modifier.width(200.dp).padding(2.dp), text = "Vlans: ${data.networks.size}");
                }
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.Black)
                subnetDisplay(scan, data);
            }
        }
    };
}