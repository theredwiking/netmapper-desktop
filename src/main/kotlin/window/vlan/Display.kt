package window.vlan

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import file.SaveData
import network.Vlan
import network.runScan

@Composable
private fun subnetInfo(vlans: List<Vlan>) {
    var subnets: List<Vlan> by remember{ mutableStateOf(mutableListOf()) };
    var complete: Boolean by rememberSaveable { mutableStateOf(false) }
    if(vlans.isEmpty()  && !complete) {
        runScan { subnets = it; complete = true; };
    } else {
        if(subnets.isEmpty()) {
            println(subnets)
            subnets = vlans
        }
        complete = true;
    }
    Column {
        if(!complete) {
            LinearProgressIndicator(modifier = Modifier.padding(2.dp))
        }
        if(complete) {
            if(subnets.isNotEmpty()) {
                showList(subnets);
            } else {
                Text(color = Color.White, text = "Couldn't find any networks");
            }
        }
    };
}

@Composable
fun subnetDisplay(display: Boolean, data: SaveData) {
    if (display) {
        subnetInfo(data.networks);
    } else {
        Text(modifier = Modifier.padding(2.dp), color = Color.White, text = "No info to display");
    }
}