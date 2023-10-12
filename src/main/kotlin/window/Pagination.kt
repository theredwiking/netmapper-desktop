package window

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Pagination(val start: Int, val end: Int, val page: Int);

@Composable
fun paginationNav(page: Pagination, onChange: (Pagination) -> Unit) {
    Row {
        Button(onClick = {
            if(page.start != 0) {
                val first = page.start - 40;
                val last = page.end - 40;
                val previous = page.page - 1;
                onChange(Pagination(first, last, previous));
            }
        }) {
            Text("<-")
        }
        Spacer(modifier = Modifier.padding(10.dp));
        Button(onClick = {
            val first = page.end;
            val last = page.end + 40;
            val next = page.page + 1;
            onChange(Pagination(first, last, next));
        }) {
            Text("->");
        }
    }
}