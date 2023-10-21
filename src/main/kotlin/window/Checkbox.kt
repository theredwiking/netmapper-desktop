package window

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun checkbox(check: Boolean, label: String, onChange: (Boolean) -> Unit) {
    Row(Modifier.height(56.dp).toggleable(value = check, onValueChange = {onChange(it)}, role = Role.Checkbox).padding(16.dp)) {
        Checkbox(checked = check, onCheckedChange = null);
        Text(modifier = Modifier.padding(3.dp), color = Color.White, text = label)
    }
}