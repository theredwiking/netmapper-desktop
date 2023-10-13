package window

import androidx.compose.foundation.layout.*
import network.Machine
import network.deviceInfo
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import file.SaveData

private var scan: Boolean = false;

@Composable
fun navBar(onMachineChange: (String) -> Unit, onStartChange: (Boolean) -> Unit, onData: (SaveData) -> Unit) {
    var read: Boolean by remember { mutableStateOf(false) };
    var error: String by remember { mutableStateOf("") };
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(modifier = Modifier.width(200.dp).padding(2.dp), onClick =  {
            val info: Machine = deviceInfo();
            onMachineChange( "Os: ${info.os}\nIp: ${info.ip}\nName: ${info.name}");
        }) {
            Text("Load machine info");
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            if(scan) {
                scan = false;
                onStartChange(false);
            } else {
                scan = true;
                onStartChange(true);
            }
        }) {
            Text("Scan for subnets");
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            read = true;
        }) {
            Text("Read file");
        }
        if(read) {
            error = vlanReadActions {onData(it); read = false; }
        }
        if(error.isNotEmpty()) {
            Spacer(modifier = Modifier.width(10.dp));
            Text(error);
        }
    }
}