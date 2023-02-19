package com.ajay.bulksms.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajay.bulksms.R
import com.ajay.bulksms.ui.theme.BulkSMSTheme

@Composable
fun MainView() {
    //var offset = remember { derivedStateOf(0) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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
                            text = "Test",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }

            Row {
                Surface {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color(0xFFE0E0E0))
                                .border(BorderStroke(2.dp, Color(0xFFEEEEEE)))
                        ) {
                            Text(
                                text = " Choose contacts",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                //fontWeight = FontWeight.Bold ,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(8.dp, 0.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.weight(1.0f))
                            Image(
                                painter = painterResource(R.drawable.keyboard_arrow_down),
                                "Contact dropdown",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .height(200.dp)
                                .border(2.dp, Color.LightGray, RoundedCornerShape(0))
                        ) {
                            ChooseContactsList(
                                Modifier
                                    .verticalScroll(rememberScrollState())
                                    .padding(8.dp)
                            ) {
                                repeat(100) {
                                    ContactEdit(
                                        initials = "82",
                                        name = null,
                                        mobileNumber = "+91 2398765432"
                                    )
                                    ContactEdit(
                                        initials = "AK",
                                        name = "Ajay",
                                        mobileNumber = "+91 3445"
                                    )
                                    ContactEdit(
                                        initials = "82",
                                        name = null,
                                        mobileNumber = "+91 2398765432"
                                    )
                                }
                            }

                        }

                        Column(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        ) {
                            Text(
                                text = "(or)",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp, 30.dp, 10.dp, 10.dp)
                                .border(3.dp, Color.LightGray, RoundedCornerShape(3))
                                .fillMaxWidth()

                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 10.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.text_snippet),
                                    "Contact dropdown",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = "Extract contact from CSV",
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(5.dp, 5.dp, 5.dp, 5.dp)
                                .fillMaxWidth()

                        ) {
                            Row(
                                modifier = Modifier.align(CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LabelAndPlaceHolderTextField(
                                    "Start Range",
                                    "Enter a number",
                                    modifier = Modifier
                                        .weight(1.0f)
                                        .border(0.5.dp, color = Color.Black),
                                )

                                Text(
                                    text = "-",
                                    fontSize = 20.sp
                                )

                                LabelAndPlaceHolderTextField(
                                    "End Range",
                                    "Enter a number",
                                    modifier = Modifier
                                        .weight(1.0f)
                                        .border(0.5.dp, color = Color.Black),
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp, 40.dp, 10.dp, 10.dp)

                        ) {
                            OutlinedTextFieldUI()
                        }

                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp, 30.dp, 10.dp, 10.dp)
                                .fillMaxWidth(),
                                //.weight(1f),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Button(
                                onClick = { /* ... */ },
                                modifier = Modifier.align(CenterHorizontally),
                                // Uses ButtonDefaults.ContentPadding by default
                                contentPadding = PaddingValues(
                                    start = 15.dp,
                                    top = 10.dp,
                                    end = 15.dp,
                                    bottom = 10.dp
                                )
                            ) {
                                //Spacer(Modifier.size(ButtonDefaults.IconSpacing))
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
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BulkSMSTheme {
        MainView()
    }
}

@Composable
fun ChooseContactsList(
    modifier: Modifier,
    contents: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = contents
    ) { measurables, constraints ->
        //Don't constrain child views further, measure them with given constraints
        // List of measured children
//        val height = 0 //placeable.height + placeableY
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        var totalHeight = 55
        var layoutWidth = constraints.maxWidth

        var xPositionCalc = 0
        placeables.forEach { placeable ->

            var placeableWidth = placeable.width

            if (layoutWidth < placeableWidth + xPositionCalc) {
                xPositionCalc = 0
                //yPositionCalc += placeable.height
                totalHeight += placeable.height
            }
            xPositionCalc += placeableWidth
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, totalHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0
            var xPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->

                var placeableWidth = placeable.width

                if (layoutWidth < placeableWidth + xPosition) {
                    xPosition = 0
                    yPosition += placeable.height
                }
                // Position item on the screen
                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += placeableWidth
            }
        }
    }
}