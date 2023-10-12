package window

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import file.*
import network.Machine
import network.ServerInfo
import network.Vlan
import network.deviceInfo


@Composable
fun saveAction(networks: List<Vlan>, servers: List<ServerInfo>) {
    val device: Machine = deviceInfo();
    val homeDir = "/home/${System.getProperty("user.name")}";
    var path: String by remember { mutableStateOf("$homeDir/") }
    var expanded: Boolean by remember  { mutableStateOf(false) };
    var selected: Int by remember { mutableStateOf(0) };
    val fileName = "${device.ip.replace(".", "-")}.nmf"
    val data = SaveData(networks, servers)
    Column {
        Box {
            Button(onClick = {
                expanded = true;
            }) {
                Text("Save data as file");
            }
            DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
                DropdownMenuItem(onClick = {
                    selected = 1
                    expanded = false;
                }) {
                    Text("Default directory");
                }
                DropdownMenuItem(onClick = {
                    selected = 2;
                    expanded = false;
                    path = dirPicker();
                }) {
                    Text("Select directory")
                }
            }
        }
        when (selected) {
            1 -> {
                if (write("$homeDir/Documents/$fileName", data)) {
                    selected = 0;
                } else {
                    selected = 3;
                }
            }
            2 -> {
                Button(onClick = {
                    if(write("$path/$fileName", data)) {
                        selected = 0;
                    } else {
                        selected = 3;
                    }
                }) {
                    Text("Save");
                }
            }
            3 -> Text("Didn't save data");
        }
    }
}

@Composable
fun vlanReadActions(onLoaded: (SaveData) -> Unit): String {
    var status = "";
    Spacer(modifier = Modifier.width(10.dp))
    if(!read(filePicker(), onData = { onLoaded(it); })) {
        status = "Failed to read file"
    }
    return status;
}

