package com.ajay.bulksms.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue


@Composable
fun LabelAndPlaceHolderTextField(
    text: String,
    placeHolder: String,
    modifier: Modifier,
    textContent: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit
) {
    val text1 = remember { mutableStateOf(textContent) }
    androidx.compose.material.TextField(
        value = textContent,
        onValueChange = {
            onValueChanged(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(text = text) },
        placeholder = { Text(text = placeHolder) },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        modifier = modifier
    )
}