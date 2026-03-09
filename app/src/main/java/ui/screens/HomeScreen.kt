package ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.GoneVishingTheme
import com.example.gonevishing.ui.theme.GvBackground
import com.example.gonevishing.ui.theme.GvPrimary
import com.example.gonevishing.ui.theme.GvTertiary
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.example.gonevishing.ui.theme.GvDeepBlue
import com.example.gonevishing.ui.theme.GvOnSurface
import com.example.gonevishing.ui.theme.GvSecondary
import com.example.gonevishing.ui.theme.GvSurface
import com.example.gonevishing.ui.theme.GvTurquoise

/* edits to make
- drop view stats button and screen
- need automation management?
 */

@Composable
fun HomeScreen(
    timeSpent: Int = 0, //needs real value when available
    currentAgentName: String = "Susan Calico",
    onTimeWastedClick: () -> Unit = {},
    onViewStatsClick: () -> Unit = {},
    onAutomationManagementClick: () -> Unit = {},
    onRecentActivityClick: () -> Unit = {},
    onAgentManagementClick: () -> Unit = {},
) {

    //background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GvBackground,
                        GvTertiary,
                        GvPrimary
                    )
                )
            ),
    ) {



        //fish and boat column
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(Modifier.height(100.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Box(
                    modifier = Modifier
                        .size(width = 250.dp, height = 140.dp)
                ) {

                    // Fish behind boat
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Current agent fish image",
                        modifier = Modifier
                            .size(600.dp)
                            .offset(x = 90.dp, y = (-80).dp)
                            .align(Alignment.TopCenter)
                            .zIndex(1f)
                            .clickable { onAgentManagementClick() },
                        contentScale = ContentScale.Fit
                    )

                    // Boat in front
                    Image(
                        painter = painterResource(R.drawable.fishing_boat_resimplified),
                        contentDescription = "boat",
                        modifier = Modifier
                            .matchParentSize()
                            .zIndex(1f),
                        contentScale = ContentScale.Fit
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset( x = 5.dp, y = (-40).dp )
                            .padding(start = 12.dp, bottom = 8.dp)
                            .zIndex(2f)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            )
                            .background(
                                color = GvSurface,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable { onAgentManagementClick() }
                    ) {

                        Column {
                            Text(
                                text = "Current Agent",
                                style = MaterialTheme.typography.labelSmall,
                                color = GvOnSurface
                            )

                            Text(
                                text = currentAgentName,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = GvOnSurface
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Change agent",
                            tint = GvDeepBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
            }

            //Home buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TimeWastedCard(
                    timeSpent = timeSpent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                )

                Spacer(Modifier.height(13.dp))

                HomeMenuButton(
                    text = "Recent Activity",
                    onClick = { onRecentActivityClick() }
                )

                Spacer(Modifier.height(13.dp))

                HomeMenuButton(
                    text = "View Statistics",
                    onClick = onViewStatsClick
                )

                Spacer(Modifier.height(13.dp))

                HomeMenuButton(
                    text = "Automation Management",
                    onClick = onAutomationManagementClick
                )

            }
            Spacer(Modifier.weight(1f))

        }
    }
}

@Composable
private fun TimeWastedCard(
    timeSpent: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = GvBackground,
        border = BorderStroke(6.dp, GvDeepBlue),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You have wasted $timeSpent minutes of scammer's time this week!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun HomeMenuButton(
    text: String,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(320.dp)
            .height(96.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = GvOnSurface
            )
        }
    }
}


@Preview(
    name = "Pixel 6",
    device = Devices.PIXEL_6,
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    GoneVishingTheme {
        //sample timeSpent value
        HomeScreen(timeSpent = 127)
    }
}