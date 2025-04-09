package eu.playersnba.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.playersnba.utils.Dimens

@Composable
fun AttributeRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(modifier = Modifier.padding(vertical = Dimens.SmallSpacing)) {
            Text(
                text = "$label: ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = value,
                fontSize = 16.sp
            )
        }
    }
}