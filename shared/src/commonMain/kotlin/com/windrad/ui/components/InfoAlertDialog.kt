package com.windrad.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windrad.ui.theme.AppColors
import com.windrad.ui.theme.WindradTheme

/**
 * Reusable info/alert dialog with a title, body message, and up to two action buttons.
 *
 * @param title       Bold title shown at the top of the dialog.
 * @param message     Explanatory body text.
 * @param confirmText Label for the primary (blue) confirm button. Defaults to "Ok".
 * @param dismissText Optional label for the secondary (outlined) dismiss button.
 *                    When null, only the confirm button is shown.
 * @param onConfirm   Called when the confirm button is pressed.
 * @param onDismissRequest Called when the user taps outside the dialog or presses back.
 * @param onDismissButtonClick Called when the secondary dismiss button is pressed.
 *                             Only relevant when [dismissText] is not null.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAlertDialog(
    title: String,
    message: String,
    confirmText: String = "Ok",
    dismissText: String? = null,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    onDismissButtonClick: (() -> Unit)? = null,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 8.dp,
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = message,
                    fontSize = 15.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                ) {
                    if (dismissText != null) {
                        OutlinedButton(
                            onClick = { onDismissButtonClick?.invoke() },
                            shape = RoundedCornerShape(1000.dp),
                        ) {
                            Text(text = dismissText, color = Color.Black)
                        }
                    }
                    androidx.compose.material3.Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(1000.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = AppColors.MapsBlue,
                        ),
                    ) {
                        Text(text = confirmText, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InfoAlertDialogPreview() {
    WindradTheme {
        InfoAlertDialog(
            title = "Hinweis zu den Zahlen",
            message = "Bei diesen Berechnungen arbeiten wir mit Näherungswerten. Weitere Infos findest Du in unserem FAQ.",
            confirmText = "Ok",
            dismissText = "Zum FAQ",
            onConfirm = {},
            onDismissRequest = {},
            onDismissButtonClick = {},
        )
    }
}
