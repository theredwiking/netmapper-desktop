package window

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import file.*
import network.Machine
import network.ServerInfo
import network.Vlan
import network.deviceInfo


@Composable
fun saveAction(networks: List<Vlan>, servers: List<ServerInfo>) {
    val homeDir = "/home/${System.getProperty("user.name")}";
    var path: String by remember { mutableStateOf("$homeDir/") }
    var expanded: Boolean by remember  { mutableStateOf(false) };
    var selected: Int by remember { mutableStateOf(0) };
    var fileName: String by remember { mutableStateOf("") }
    val data = SaveData(networks, servers)
    Column {
        Box {
            Button(onClick = {
                expanded = true;
            }) {
                Text("Save data as file");
            }
            DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
                fileNameDialog { fileName = "$it.nmf" }
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
        if(fileName.isNotEmpty()) {
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
}

@Composable
private fun fileNameDialog(onSave: (String) -> Unit) {
    var name: String by remember { mutableStateOf("") }
    var close: Boolean by remember { mutableStateOf(true) }

    DialogWindow(
        onCloseRequest = { close = false},
        visible = close,
        title = "Filename"
    ) {
        Column {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Filename") }
            )
            Button(onClick = {
                onSave(name);
                close = false;
            }) {
                Text("Save");
            }
        }
    }
}

fun vlanReadActions(onLoaded: (SaveData) -> Unit): String {
    var status = "";
    if(!read(filePicker(), onData = { onLoaded(it); })) {
        status = "Failed to read file"
    }
    return status;
}

