package com.ajay.bulksms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldUI(smsMessage: TextFieldValue, onMessageChanged: (TextFieldValue) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(22.dp))
                .height(120.dp),
            shape = RoundedCornerShape(22.dp),
            value = smsMessage,
            onValueChange = { onMessageChanged(it) },
            maxLines = 6,
            label = { Text("Please enter the message !") },
            textStyle = MaterialTheme.typography.caption,
            )
    }
}
