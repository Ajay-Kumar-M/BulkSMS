package com.ajay.bulksms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajay.bulksms.components.ContactView
import com.ajay.bulksms.ui.theme.BulkSMSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectContactsView() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column {
            Row {
                Card(
                    elevation = 10.dp,
                    //modifier = Modifier.padding(1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                //text = ""
                            },
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.weight(1.0f))

                        Text(
                            text = "DONE",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50) //Color.Green
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp,0.dp,0.dp,0.dp)
            ){
                LazyColumn {
                    items(
                        count = 15,
                        key = null
                    ) {
//                        ContactView(
//                            id = 11111111,
//                            initials = "AK",
//                            name = "Ajay Kumar M",
//                            mobileNumber = "+91 76253888982",
//                            isSelected = remember { mutableStateOf(false) },
//                            viewModel = TODO()
//                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectContacts() {
    BulkSMSTheme {
        SelectContactsView()
    }
}
