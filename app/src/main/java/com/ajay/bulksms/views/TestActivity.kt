package com.ajay.bulksms.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ajay.bulksms.components.OutlinedTextFieldUI
import com.ajay.bulksms.components.Screen
import com.ajay.bulksms.ui.theme.BulkSMSTheme
import com.ajay.bulksms.viewModel.TestActivityViewModel

@Composable
fun TestView(
    navController: NavController,
    viewModel: TestActivityViewModel = viewModel()
) {
    val context = LocalContext.current
    val testSmsMessage = viewModel.testSmsMessage
    val phoneNumber = viewModel.phoneNumber

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row {
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
                            color = Color.Red,
                            modifier = Modifier
                                .clickable {
                                    navController.popBackStack(
                                        Screen.TestView.route,
                                        inclusive = true
                                    )
                                }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 20.dp, 10.dp, 10.dp)
            ) {
                //val singlePhoneNumber = remember { mutableStateOf(TextFieldValue("")) }
                val trailingIconView = @Composable {
                    IconButton(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Enter phone number manually.",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    ) {
                        Icon(
                            Icons.Default.ContactPhone,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        viewModel.changePhoneNumber(it)
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

                ) {
                    OutlinedTextFieldUI(
                        testSmsMessage,
                        onMessageChanged = { viewModel.changeTestSmsMessage(it) }
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp, 30.dp, 10.dp, 10.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight =1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "Result Message :",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(15.dp, 15.dp, 15.dp, 5.dp)
                    )
                    Text(
                        text = viewModel.messageStatus,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 20.dp)
                            .fillMaxWidth()
                    )
                }
                Button(
                    onClick = {
                        viewModel.sendTestSMS()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
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

@Preview(showBackground = true)
@Composable
fun TestPreview() {
    BulkSMSTheme {
        TestView(
            navController = NavHostController(LocalContext.current),
            viewModel = TestActivityViewModel()
        )
    }
}
