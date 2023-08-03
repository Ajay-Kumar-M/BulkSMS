package com.ajay.bulksms.components

class Temp {


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

    /*

    @Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return  lifecycleEvent
}

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if ((lifecycleEvent == Lifecycle.Event.ON_CREATE)||(lifecycleEvent == Lifecycle.Event.ON_RESUME)) {


        }
    }


@Composable
fun <viewModel: LifecycleObserver> viewModel.observeLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycleEvents)
        }
    }
}




//            // Open a specific media item using ParcelFileDescriptor.
//            val resolver = context.contentResolver
//
////            try {
//            val inputStream: InputStream? =
//                LocalContext.current.contentResolver.openInputStream(photoUri.value!!)
////            } catch (e: IOException) {
////                null
////            }
//
//            inputStream?.bufferedReader()?.use { reader ->
//                val content = StringBuilder()
//                reader.forEachLine { line ->
//                    // Process each line of the file here
//                    content.append(line).append('\n')
//                }
//                // Use the content string as needed
//                println("File content: $content")
//            }

//                        val csvFile = context.contentResolver.openInputStream(photoUri.value!!)
//                        val isr = InputStreamReader(csvFile, "UTF-8")
//                        println("csv output "+BufferedReader(isr).readLines())
//                        isr.close()




navigation nested graph
    NavHost(navController = navController, startDestination = Screen.MainView.route) {
        composable(Screen.MainView.route) {
            HomeScreen(navController)
        }
        composable(Screen.detailView.route) {
            ContactListScreen(navController)
        }


//        navigation(route = "",  startDestination = Screen.MainView.route) {
//            composable(Screen.MainView.route) {
//                HomeScreen(navController)
//            }
//            composable(Screen.detailView.route) {
//                ContactListScreen(navController)
//            }
//        }
    }



     */
}