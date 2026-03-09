package com.example.gonevishing.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.GvTertiary
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.gonevishing.ui.theme.GoneVishingTheme
import androidx.compose.ui.tooling.preview.Devices

@Composable
fun SendToAgentOverlay(
    callerName: String,
    callerNumber: String,
    onDismiss: () -> Unit,
    onSendToAgent: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
    contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.fishing_bobber),
                        contentDescription = "Fishing bobber icon",
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = "Potential Scam Call!",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Column {
                    Text(
                        text = callerName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = callerNumber,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = "Gone Vishing has flagged this call as possible scam. Would you like to send it to your AI agent?",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onSendToAgent,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GvTertiary
                        )
                    ) {
                        Text(
                            text = "Send to Agent",
                            maxLines = 1
                        )
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GvTertiary
                        )
                    ) {
                        Text("Dismiss")
                    }


                }
            }
        }
    }
}

@Preview(
    name = "Pixel 6",
    device = Devices.PIXEL_6,
    showBackground = true
)
@Composable
private fun SendToAgentOverlayPreview() {
    GoneVishingTheme {
        SendToAgentOverlay(
            callerName = "Scam Caller",
            callerNumber = "+1 (818) 123-4567",
            onDismiss = {},
            onSendToAgent = {}
        )
    }
}




