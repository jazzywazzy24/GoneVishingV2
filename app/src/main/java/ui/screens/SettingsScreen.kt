package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.*

/*
Edits to make:
- make "account" and "app settings" a dropdown button
- remove notifications?
 */



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onEditProfilePicture: () -> Unit = {},

    onSave: (
        username: String,
        email: String,
        phone: String,
        agent: Boolean,
        //scamOverlay: Boolean,
        notifications: Boolean,
        vibration: Boolean
    ) -> Unit = { _, _, _, _, _, _ -> },

    initialUsername: String = "",
    initialEmail: String = "",
    initialPhone: String = "",

    initialAgentEnabled: Boolean = true,
    //initialScamOverlayEnabled: Boolean = true,
    initialNotificationsEnabled: Boolean = true,
    initialVibrationEnabled: Boolean = false
) {
    // Account
    var username by remember { mutableStateOf(initialUsername) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }

    // General settings
    var agentEnabled by remember { mutableStateOf(initialAgentEnabled) }
    //var scamOverlayEnabled by remember { mutableStateOf(initialScamOverlayEnabled) }
    var notificationsEnabled by remember { mutableStateOf(initialNotificationsEnabled) }
    var vibrationEnabled by remember { mutableStateOf(initialVibrationEnabled) }

    // This shows what the user is currently editing
    var editTarget by remember { mutableStateOf<EditTarget?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        SettingsSectionCard(title = "Profile") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.account_icon),
                    contentDescription = "Account Icon",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(12.dp))

                GVButton(onClick = onEditProfilePicture) {
                    Text("Edit Profile Picture")
                }
            }
        }

        SettingsSectionCard(title = "Account") {
            SettingsNavRow(
                title = "Username",
                subtitle = username.ifBlank { "Not set" },
                onClick = { editTarget = EditTarget.Username(username) }
            )
            Divider(
                color = GvPrimary,
            )

            SettingsNavRow(
                title = "Email",
                subtitle = email.ifBlank { "Not set" },
                onClick = { editTarget = EditTarget.Email(email) }
            )
            Divider(
                color = GvPrimary,
            )
            SettingsNavRow(
                title = "Phone",
                subtitle = phone.ifBlank { "Not set" },
                onClick = { editTarget = EditTarget.Phone(phone) }
            )
            Divider(
                color = GvPrimary,
            )
            SettingsNavRow(
                title = "Password",
                subtitle = "Change password",
                onClick = { editTarget = EditTarget.Password }
            )
        }

        SettingsSectionCard(title = "App Settings") {

            ToggleRow(
                title = "AI Agent",
                description = "Receive overlay prompt when a scam call is flagged.",
                checked = agentEnabled,
                onCheckedChange = { agentEnabled = it }
            )

            /*
            Divider(
                    color = GvPrimary,
            )
            ToggleRow(
                title = "Scam call overlay",
                description = "Show an overlay when a call is flagged.",
                checked = scamOverlayEnabled,
                onCheckedChange = { scamOverlayEnabled = it }
            )

             */

            Divider(
                color = GvPrimary,
            )
            ToggleRow(
                title = "Notifications",
                description = "Get alerts when scams are detected.",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            Divider(
                color = GvPrimary,
            )
            ToggleRow(
                title = "Vibration",
                description = "Vibrate when a call is flagged.",
                checked = vibrationEnabled,
                onCheckedChange = { vibrationEnabled = it }
            )
        }

        GVButton(
            onClick = {
                onSave(
                    username,
                    email,
                    phone,
                    //scamOverlayEnabled,
                    agentEnabled,
                    notificationsEnabled,
                    vibrationEnabled
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }

        Spacer(Modifier.height(32.dp))
    }

    when (val target = editTarget) {
        is EditTarget.Username -> {
            EditTextDialog(
                title = "Edit Username",
                label = "Username",
                initialValue = target.current,
                onDismiss = { editTarget = null },
                onSave = { newValue ->
                    username = newValue.trim()
                    editTarget = null
                }
            )
        }

        is EditTarget.Email -> {
            EditTextDialog(
                title = "Edit Email",
                label = "Email",
                initialValue = target.current,
                onDismiss = { editTarget = null },
                onSave = { newValue ->
                    email = newValue.trim()
                    editTarget = null
                }
            )
        }

        is EditTarget.Phone -> {
            EditTextDialog(
                title = "Edit Phone",
                label = "Phone",
                initialValue = target.current,
                onDismiss = { editTarget = null },
                onSave = { newValue ->
                    phone = newValue.trim()
                    editTarget = null
                }
            )
        }

        is EditTarget.Password -> {
            ChangePasswordDialog(
                onDismiss = { editTarget = null },
                onSave = { currentPassword, newPassword ->
                    editTarget = null
                }
            )
        }

        null -> Unit
    }
}

private sealed class EditTarget {
    data class Username(val current: String) : EditTarget()
    data class Email(val current: String) : EditTarget()
    data class Phone(val current: String) : EditTarget()
    data object Password : EditTarget()
}

@Composable
private fun SettingsSectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            //Spacer(Modifier.height(3.dp))
            content()
        }
    }
}

@Composable
private fun SettingsNavRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Open",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = GvTertiary,
                checkedTrackColor = GvTertiary.copy(alpha = 0.5f),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
private fun EditTextDialog(
    title: String,
    label: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var value by remember(initialValue) { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                singleLine = true,
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(value) }
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onSave: (currentPassword: String, newPassword: String) -> Unit
) {
    var current by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var showCurrent by remember { mutableStateOf(false) }
    var showNew by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = current,
                    onValueChange = { current = it },
                    label = { Text("Current password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (showCurrent) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showCurrent = !showCurrent }) {
                            Icon(
                                imageVector = if (showCurrent) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle visibility"
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = newPass,
                    onValueChange = { newPass = it },
                    label = { Text("New password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (showNew) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showNew = !showNew }) {
                            Icon(
                                imageVector = if (showNew) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle visibility"
                            )
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(current, newPass) }
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Preview(
    name = "Pixel 6",
    device = Devices.PIXEL_6,
    showBackground = true
)
@Composable
private fun AllSettingsPreview() {
    GoneVishingTheme {
        SettingsScreen(
            initialUsername = "jazzywazzy",
            initialEmail = "jasmine@gmail.com",
            initialPhone = "(818)-***-**67"
        )
    }
}