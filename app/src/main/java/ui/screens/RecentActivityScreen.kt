package ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gonevishing.ui.theme.GVButton
import com.example.gonevishing.ui.theme.GVOutlinedButton
import com.example.gonevishing.ui.theme.GoneVishingTheme
import com.example.gonevishing.ui.theme.GvBackground
import com.example.gonevishing.ui.theme.GvDeepBlue
import com.example.gonevishing.ui.theme.GvPrimary
import com.example.gonevishing.ui.theme.GvSecondary
import com.example.gonevishing.ui.theme.GvTertiary
import com.example.gonevishing.ui.theme.GvTurquoise
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Immutable
data class CallActivity(
    val id: String,
    val number: String,
    val receivedAt: LocalDateTime,
    val duration: Duration,
    val wasSuccessful: Boolean
)

@Composable
fun RecentActivityScreen(
    calls: List<CallActivity>,
    onBack: () -> Unit,
    onViewTranscript: (CallActivity) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GvBackground,
                        GvTertiary,
                        GvPrimary
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Column {
                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Your last 5 calls",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            CallsList(
                calls = calls.take(5),
                onViewTranscript = onViewTranscript
            )
        }
    }
}

@Composable
private fun CallsList(
    calls: List<CallActivity>,
    onViewTranscript: (CallActivity) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
        border = BorderStroke(2.dp, GvDeepBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (calls.isEmpty()) {
                Text(
                    text = "No calls yet.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                return@Column
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(calls, key = { it.id }) { call ->
                    CallRow(
                        call = call,
                        onViewTranscript = { onViewTranscript(call) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CallRow(
    call: CallActivity,
    onViewTranscript: () -> Unit
) {
    val receivedFmt = rememberDateTimeFormatter()
    val receivedText = call.receivedAt.format(receivedFmt)
    val durationText = formatDuration(call.duration)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(2.dp, GvDeepBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = call.number,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (call.wasSuccessful) "Status: Successful" else "Status: Failed",
                style = MaterialTheme.typography.titleMedium,
                color = if (call.wasSuccessful)
                    GvSecondary
                else
                    MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Received",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = receivedText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Duration",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = durationText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            GVButton(
                onClick = onViewTranscript,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View transcript")
            }
        }
    }
}

@Composable
private fun rememberDateTimeFormatter(): DateTimeFormatter {
    return DateTimeFormatter.ofPattern("MMM d, h:mm a")
}

private fun formatDuration(d: Duration): String {
    val totalSeconds = d.seconds.coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes}m ${seconds}s"
}

@Preview(showBackground = true, widthDp = 420, heightDp = 900)
@Composable
private fun RecentActivityScreenPreview() {
    val now = LocalDateTime.now()
    val sample = listOf(
        CallActivity("1", "+1 (415) 555-0123", now.minusHours(1), Duration.ofSeconds(184),true ),
        CallActivity("2", "+1 (650) 555-0199", now.minusDays(1).minusMinutes(12), Duration.ofSeconds(57), false),
        CallActivity("3", "Unknown", now.minusDays(2).minusHours(3), Duration.ofSeconds(420), true),
        CallActivity("4", "+44 20 7946 0958", now.minusDays(3), Duration.ofSeconds(133), true),
        CallActivity("5", "+1 (212) 555-0177", now.minusDays(6).minusHours(2), Duration.ofSeconds(305), false),
    )

    GoneVishingTheme {
        RecentActivityScreen(
            calls = sample,
            onBack = {},
            onViewTranscript = {}
        )
    }
}