package window.servers

import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import network.Ping
import network.ServerInfo
import network.scanForServers

@Composable
fun serverDisplay(networks: List<Ping>, onServers: (List<ServerInfo>) -> Unit) {
    var servers: List<ServerInfo> by rememberSaveable { mutableStateOf(mutableListOf()) }
    var complete: Boolean by remember { mutableStateOf( false) };
    scanForServers(networks) {servers = it; complete = true;};
    if(!complete) {
        LinearProgressIndicator(modifier = Modifier.padding(2.dp))
    };
    if(complete) {
        if(servers.isNotEmpty()) {
            onServers(servers);
            serverList(servers);
        } else {
            Text("Couldn't find any servers");
        };
    };
}