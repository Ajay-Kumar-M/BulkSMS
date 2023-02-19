package com.ajay.bulksms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class temp {
    /*Row(
    modifier = Modifier
    .fillMaxWidth(1.0f)
    .drawBehind {
        val strokeWidth = 2 * density
        val y = size.height - strokeWidth / 2

        drawLine(
            Color.LightGray,
            Offset(0f, y),
            Offset(size.width, y),
            strokeWidth
        )
    }
    )


LazyVerticalGrid(
                                modifier = Modifier.weight(1.0f)
                                    .background(Color.Yellow)
                                    .fillMaxWidth(),
                                columns = GridCells.Adaptive(50.dp),

                                // content padding
//                                contentPadding = PaddingValues(
//                                    start = 12.dp,
//                                    top = 16.dp,
//                                    end = 12.dp,
//                                    bottom = 16.dp
//                                ),
                                content = {
                                    items(2, span = { GridItemSpan(1) })
                                    {
//                                        ContactEdit(
//                                            initials = "82",
//                                            name = null,
//                                            mobileNumber = "+91 76253888982"
//                                        )
                                        Text(
                                            text = "Testsddsdsd",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Red
                                        )
                                    }

                                }
                            )
    */



//    Column(
//        modifier = Modifier
//            .background(Color.White)
//            .height(200.dp)
//
//    ) {
//
////            ContactEdit(
////                initials = "82",
////                name = "ajay",
////                mobileNumber = "+91 76253888982"
////            )
//        ChooseContactsList(
//            Modifier
//                .verticalScroll(rememberScrollState())
//                .padding(8.dp)
//                .fillMaxHeight()
//        ) {
//            ContactEdit(
//                initials = "82",
//                name = "ajay",
//                mobileNumber = "+91 76253888982"
//            )
//            repeat(100) {
//                Text(text = "Hello $it", color = Color.Red)
//            }
//        }
//        ContactEdit(
//            initials = "82",
//            name = "ajay",
//            mobileNumber = "+91 76253888982"
//        )
//        var modifier: Modifier = Modifier
//        ChooseContactsList(
//            Modifier
//                .verticalScroll(rememberScrollState())
//                .wrapContentSize()
//                .padding(8.dp)
//        ) {
//            ContactEdit(
//                initials = "82",
//                name = null,
//                mobileNumber = "+91 23"
//            )
//            ContactEdit(
//                initials = "82",
//                name = "Ajay",
//                mobileNumber = "+91 3445"
//            )
//        }

    //}


//    @Composable
//    fun OutlinedTextFieldBackground(
//        color: Color,
//        content: @Composable () -> Unit
//    ) {
//        // This box just wraps the background and the OutlinedTextField
//        Box {
//            // This box works as background
//            Box(
//                modifier = Modifier
//                    .matchParentSize()
//                    .padding(top = 8.dp) // adding some space to the label
//                    .background(
//                        color,
//                        // rounded corner to match with the OutlinedTextField
//                        shape = RoundedCornerShape(4.dp)
//                    )
//            )
//            // OutlineTextField will be the content...
//            content()
//        }
//    }




    // Inner content including an icon and a text label
//                                Icon(
//                                    Icons.Filled.Favorite,
//                                    contentDescription = "Favorite",
//                                    modifier = Modifier.size(ButtonDefaults.IconSize)
//                                )
}