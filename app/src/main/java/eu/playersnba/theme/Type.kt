package eu.playersnba.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.playersnba.R

val BebasFont = FontFamily(
	Font(R.font.bebas_neue, FontWeight.Normal)
)

val Typography = Typography(
	headlineLarge = TextStyle(
		fontFamily = BebasFont,
		fontWeight = FontWeight.Normal,
		fontSize = 30.sp,
		lineHeight = 36.sp,
		letterSpacing = 0.sp
	),
	titleLarge = TextStyle(
		fontFamily = BebasFont,
		fontWeight = FontWeight.Normal,
		fontSize = 24.sp,
		lineHeight = 30.sp
	),
	bodyLarge = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp
	)
)
