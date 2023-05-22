package com.ajay.bulksms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajay.bulksms.components.MainView
import com.ajay.bulksms.components.OutlinedTextFieldUI
import com.ajay.bulksms.ui.theme.BulkSMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestView() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column() {
            Row() {
                Card(
                    elevation = 10.dp,
                    modifier = Modifier.padding(1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Bulk SMS",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    val brush =
                                        Brush.horizontalGradient(listOf(Color.Red, Color.Yellow))
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                                    }
                                }

                        )

                        Spacer(modifier = Modifier.weight(1.0f))

                        Text(
                            text = "Close",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp,20.dp,10.dp,10.dp)
            ){
                var singlePhoneNumber = remember { mutableStateOf(TextFieldValue("")) }
                val trailingIconView = @Composable {
                    IconButton(
                        onClick = {
                            //text = ""
                        },
                    ) {
                        Icon(
                            Icons.Default.Contacts,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
                TextField(
                    value = singlePhoneNumber.value,
                    onValueChange = {
                        singlePhoneNumber.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Enter - Phone number") },
                    placeholder = { Text(text = "Enter single number to test the message") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    trailingIcon = trailingIconView,
                    shape = RectangleShape
                )

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(0.dp, 40.dp, 0.dp, 0.dp)

                ){
                    //OutlinedTextFieldUI("")
                }
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp, 30.dp, 10.dp, 10.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ){
                    Button(
                        onClick = { /* ... */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        // Uses ButtonDefaults.ContentPadding by default
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 10.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        )
                    ) {
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = "Send Message",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    BulkSMSTheme {
        TestView()
    }
}
