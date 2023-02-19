package com.ajay.bulksms.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajay.bulksms.ui.theme.BulkSMSTheme

@SuppressLint("SuspiciousIndentation")
@Composable
fun ContactView(
    initials: String,
    name: String,
    mobileNumber: String,
    isSelected: MutableState<Boolean>
) {
    val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isSelected.value = !isSelected.value
                    Toast
                        .makeText(context, " Clickable Row Example", Toast.LENGTH_SHORT)
                        .show()
                }
                .drawBehind {
                    val borderSize = 0.5.dp.toPx()
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                }
        ) {
            if(isSelected.value) {
                Surface(
                    modifier = Modifier.size(60.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(
                        100
                    ),
                    color = Color(0xFF0085FF)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "",
                        tint = Color.White
                    )

                }
            }
            else
            {
                Surface(
                    modifier = Modifier.size(60.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(
                        100
                    ),
                    color = Color(0xFF4CAF50)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(1.0f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = initials,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1.0f)
                        )
                    }

                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = mobileNumber)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BulkSMSTheme {
        LazyColumn {
           items(
               count = 10,
               key = null
           ) {
               ContactView(
                   initials = "AK",
                   name = "Ajay Kumar M",
                   mobileNumber = "+91 76253888982",
                   isSelected = remember {
                       mutableStateOf(false)
                   }
               )
           }
        }
    }
}