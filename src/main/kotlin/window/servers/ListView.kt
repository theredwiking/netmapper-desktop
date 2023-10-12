package window.servers

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import network.ServerInfo
import window.Pagination
import window.paginationNav

@Composable
fun serverList(servers: List<ServerInfo>) {
    var page: Pagination by rememberSaveable { mutableStateOf(Pagination(0, 40, 1)) };
    val pages = servers.size / 40;
    Spacer(modifier = Modifier.padding(1.dp));
    Column(modifier = Modifier.border(width = Dp.Hairline, shape = RoundedCornerShape(1.dp), color = Color.Black).fillMaxHeight()) {
        paginationNav(page) {page = it};
        Divider(modifier = Modifier.widthIn(100.dp, 180.dp), color = Color.Black)
        Text(" Page ${page.page} of $pages")
        Divider(modifier = Modifier.widthIn(100.dp, 180.dp), color = Color.Black)
        listServers(servers, page);
    }
}

@Composable
private fun listServers(servers: List<ServerInfo>, page: Pagination) {
    Column(modifier = Modifier.verticalScroll(state = ScrollState(1), enabled = true)) {
        for (i in page.start..page.end) {
            val testInteract = remember { MutableInteractionSource() };
            val isHover by testInteract.collectIsHoveredAsState();
            Text(modifier = Modifier.padding(6.dp).hoverable(testInteract, true), text = " Ip: ${
                try {
                    servers[i].ip
                } catch (e: Exception) {
                    continue;
                }
            }");
            if(isHover) {
                Text(color = Color.Black, fontFamily = FontFamily.Monospace, text = " Ports: ${servers[i].ports}")
            }
        }
    }
}