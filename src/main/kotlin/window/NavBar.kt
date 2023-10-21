package window

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import network.Machine
import network.deviceInfo
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import file.SaveData

private var scan: Boolean = false;

@Composable
fun navBar(onStartChange: (Boolean) -> Unit, onData: (SaveData) -> Unit) {
    var read: Boolean by remember { mutableStateOf(false) };
    var error: String by remember { mutableStateOf("") };
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(modifier = Modifier.padding(2.dp), onClick = {
            if (scan) {
                scan = false;
                onStartChange(false);
            } else {
                scan = true;
                onStartChange(true);
            }
        }) {
            Image(
                painter = painterResource("search.svg"),
                contentDescription = "Scan subnets",
                modifier = Modifier.width(20.dp).height(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = {
            read = true;
        }) {
            Image(
                painter = painterResource("open-file.svg"),
                contentDescription = "Open file",
                modifier = Modifier.width(20.dp).height(20.dp)
            )
        }
        if (read) {
            error = vlanReadActions { onData(it); read = false; }
        }
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.width(10.dp));
            Text(error);
        }
    }
}