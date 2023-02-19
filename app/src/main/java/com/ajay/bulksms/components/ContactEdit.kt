package com.ajay.bulksms.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajay.bulksms.R
import com.ajay.bulksms.ui.theme.BulkSMSTheme

@Composable
fun ContactEdit(
    initials: String = "AK",
    name: String? = "Ajay M",
    mobileNumber: String = "1234567890"
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(60))
            .shadow(3.dp, shape = RoundedCornerShape(18.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                //.shadow(3.dp)
        ) {
            Surface(
                modifier = Modifier.size(25.dp),
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
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (name != null) Text(text = name) else Text(text = mobileNumber)
            }
            Surface(
                modifier = Modifier.size(20.dp),
                shape = RoundedCornerShape(
                    100
                ),
                color = Color(0xFF6B6B6B)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(1.0f)
                        .clickable { println("close action triggered") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.close),
                        "Contact dropdown",
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ContactEditPreview() {
    BulkSMSTheme {
        ContactEdit(
            initials = "82",
            name = null,
            mobileNumber = "+91 76253888982"
        )
    }
}