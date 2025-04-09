package eu.playersnba.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.playersnba.utils.Constants
import eu.playersnba.utils.Dimens

@Composable
fun ErrorWithRetry(
	message: String,
	onRetry: () -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(Dimens.SectionSpacing),
		contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				text = message,
				color = MaterialTheme.colorScheme.error
			)
			Spacer(modifier = Modifier.height(Dimens.ScreenPadding))
			Button(onClick = onRetry) {
				Text(Constants.RETRY)
			}
		}
	}
}
