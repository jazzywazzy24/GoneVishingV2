package ui.screens

import android.R.attr.rotation
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.GoneVishingTheme
import com.example.gonevishing.ui.theme.GvBackground
import com.example.gonevishing.ui.theme.GvOnSurface
import com.example.gonevishing.ui.theme.GvPrimary
import com.example.gonevishing.ui.theme.GvTertiary
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment

@Composable
fun AgentManagementScreen(
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    onPersonalitySelected: (Int) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(initialSelectedIndex) }
    var expandedIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GvBackground, GvTertiary, GvPrimary)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 18.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

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
                        text = "Agent Management",
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

            ExpandablePersonalityCard(
                index = 0,
                expanded = expandedIndex == 0,
                selected = selectedIndex == 0,
                logoRes = R.drawable.logo,
                title = "Agent Susan Calico",
                description = "An elderly woman with 5 grandchildren and many cats. She enjoys talking about her family often," +
                        " which of course includes her cats. Everytime you talk to her, there is somehow another cat?!",
                onClick = {
                    expandedIndex = 0
                    selectedIndex = 0
                    onPersonalitySelected(0)
                }
            )

            ExpandablePersonalityCard(
                index = 1,
                expanded = expandedIndex == 1,
                selected = selectedIndex == 1,
                logoRes = R.drawable.logo,
                title = "Agent Simon Roundtree",
                description = "A confused, but persistent elderly man who asks lots of questions and repeats details. " +
                        "Often talks over other people due to his enthusiasm. It almost feels like running around a tree over and over...",
                onClick = {
                    expandedIndex = 1
                    selectedIndex = 1
                    onPersonalitySelected(1)
                }
            )

            ExpandablePersonalityCard(
                index = 2,
                expanded = expandedIndex == 2,
                selected = selectedIndex == 2,
                logoRes = R.drawable.logo,
                title = "Agent John Farmer",
                description = "The owner of a farm who loves to talk about it. He relates everything to something around " +
                        "the farm and attempts to make jokes wherever he can.",
                onClick = {
                    expandedIndex = 2
                    selectedIndex = 2
                    onPersonalitySelected(2)
                }
            )
        }
    }
}

@Composable
private fun ExpandablePersonalityCard(
    index: Int,
    expanded: Boolean,
    selected: Boolean,
    logoRes: Int,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF0B2A7A) else Color.Transparent
    val backgroundColor = if (selected) Color(0xFFEAF1FF) else Color(0xFFF2F2F2)

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor,
        border = BorderStroke(3.dp, borderColor),
        tonalElevation = if (selected) 6.dp else 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp)
            .animateContentSize()
    ) {
        //different layouts depending on if expanded or not
        if (!expanded) {
            CollapsedCardContent(
                logoRes = logoRes,
                title = title
            )
        } else {
            ExpandedCardContent(
                logoRes = logoRes,
                title = title,
                description = description,
                selected = selected
            )
        }
    }
}

@Composable
private fun CollapsedCardContent(
    logoRes: Int,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(logoRes),
            contentDescription = "fish logo",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Left,
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                )

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = Color(0xFF0B2A7A),
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun ExpandedCardContent(
    logoRes: Int,
    title: String,
    description: String,
    selected: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(1f),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy( alpha = 0.9f),
        )
        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(logoRes),
            contentDescription = "fish logo",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.fillMaxWidth(1f),
            textAlign = TextAlign.Center,
            text = description,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy( alpha = 0.9f),
            maxLines = 10,
        )
        if (selected) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Your current agent.",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0B2A7A),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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
private fun AgentManagementScreenPreview() {
    GoneVishingTheme {
        AgentManagementScreen(initialSelectedIndex = 0)
    }
}