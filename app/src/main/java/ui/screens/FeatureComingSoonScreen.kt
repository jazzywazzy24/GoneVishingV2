package com.example.gonevishing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gonevishing.ui.theme.GVButton
import com.example.gonevishing.ui.theme.GoneVishingTheme

@Composable
fun FeatureComingSoonScreen(
    onBack: () -> Unit      //defined onBack in AppRouter, and Unit is basically like void
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

    horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This feature is coming soon.")

        Spacer(Modifier.height(16.dp))
        GVButton(onClick = onBack) {
            Text("Back to login")
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun FeatureComingSoonPreview() {
    GoneVishingTheme {
        FeatureComingSoonScreen(onBack = {})
    }
}