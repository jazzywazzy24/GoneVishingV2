package com.example.gonevishing.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.Fredoka
import com.example.gonevishing.ui.theme.GoneVishingTheme
import com.example.gonevishing.ui.theme.GvTertiary
import com.example.gonevishing.ui.theme.GvDeepBlue

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val outlinedColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.White,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        cursorColor = Color.White,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GvDeepBlue,
                        GvTertiary,
                        GvDeepBlue
                    )
                )
            )
    ) {

        // 🔹 CENTER CONTENT
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Gone Vishing logo",
                modifier = Modifier.size(180.dp)
            )

            Text(
                text = "Gone Vishing",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = Fredoka
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Let's go vishing!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(Modifier.height(25.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector =
                                if (passwordVisible) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedColors
            )

            Spacer(modifier = Modifier.height(20.dp))

            val canLogin = username.isNotBlank() && password.isNotBlank()

            Button(
                onClick = { onLoginSuccess() },
                enabled = canLogin,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF041c4e),
                    disabledContainerColor = Color.White.copy(alpha = 0.4f),
                    disabledContentColor = Color(0xFF041c4e).copy(alpha = 0.7f)
                )
            ) {
                Text("Log in")
            }

            TextButton(onClick = { onForgotPassword() }) {
                Text("Forgot password?", color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { onForgotPassword() }) {
                    Text("Use biometrics", color = Color.White)
                }

                TextButton(onClick = { onForgotPassword() }) {
                    Text("Customer support", color = Color.White)
                }

                TextButton(onClick = { onForgotPassword() }) {
                    Text("Don't have an account?", color = Color.White)
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
private fun LoginScreenPreview() {
    GoneVishingTheme {
        LoginScreen(
            onLoginSuccess = { },
            onForgotPassword = { }
        )
    }
}