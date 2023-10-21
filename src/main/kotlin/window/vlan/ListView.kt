package window.vlan

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import network.Ping
import network.ServerInfo
import network.Vlan
import network.totalList
import window.Pagination
import window.checkbox
import window.paginationNav
import window.saveAction
import window.servers.serverDisplay

@Composable
fun showList(subnets: List<Vlan>) {
    var list: Boolean by remember  { mutableStateOf(false) };
    var servers: List<ServerInfo> by rememberSaveable{ mutableStateOf(mutableListOf()) }
    val networks: List<Ping> = totalList(subnets);
    var serverScan: Boolean by remember { mutableStateOf(false) };
    var page: Pagination by rememberSaveable { mutableStateOf(Pagination(0, 40, 1)) };
    val pages = networks.size / 40;
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(modifier = Modifier.padding(2.dp), color = Color.White, text = "Completed scan")
            Text(modifier = Modifier.padding(2.dp), color = Color.White, text = "Found: ${subnets.size}")
            checkbox(list, "Show networks") {list = it}
            Button(onClick = {
                serverScan = true;
            }) {
                Text("Scan for servers")
            }
            saveAction(subnets, servers);
        }
        Spacer(modifier = Modifier.padding(10.dp));
        if(list) {
            Column(modifier = Modifier.border(width = Dp.Hairline, shape = RoundedCornerShape(1.dp), color = Color.Black).fillMaxHeight()) {
                paginationNav(page) {page = it};
                Divider(modifier = Modifier.widthIn(100.dp, 180.dp), color = Color.Black)
                Text(color = Color.White, text = " Page ${page.page} of $pages")
                Divider(modifier = Modifier.widthIn(100.dp, 180.dp), color = Color.Black)
                listSubnets(networks, page);
            }
        }
        if(serverScan) {
            serverDisplay(networks) {servers = it};
        }
    }
}

@Composable
private fun listSubnets(subnets: List<Ping>, page: Pagination) {
    Column(modifier = Modifier.verticalScroll(state = ScrollState(1), enabled = true)) {
        for (i in page.start..page.end) {
            Text(modifier = Modifier.padding(6.dp) , color = Color.White, text = " Vlan: ${
                try {
                    subnets[i].ip
                } catch (e: Exception) {
                    continue;
                }
            }");
        }
    }
}