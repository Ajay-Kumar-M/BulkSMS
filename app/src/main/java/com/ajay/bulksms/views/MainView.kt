package com.ajay.bulksms.views

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajay.bulksms.AppViewModelProvider
import com.ajay.bulksms.R
import com.ajay.bulksms.components.CustomLayoutContact
import com.ajay.bulksms.components.OutlinedTextFieldUI
import com.ajay.bulksms.components.Screen
import com.ajay.bulksms.ui.theme.BulkSMSTheme
import com.ajay.bulksms.viewModel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun MainView() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainView.route) {
        composable(Screen.MainView.route) {
            HomeScreen(navController)
        }
        composable(Screen.SelectContactView.route) {
            ContactListScreen(navController)
        }
        composable(Screen.TestView.route) {
            TestView(navController)
        }
        composable(Screen.CSVView.route) {
            CSVView(navController)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val smsMessage = viewModel.smsMessage
    val sendSMSPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.SEND_SMS
    )
    val isSendSMSButtonEnable by viewModel.isSendSMSButtonEnable.collectAsStateWithLifecycle()

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<Int>>("SelectedUsers")?.observe(
        LocalLifecycleOwner.current){
        Log.d("MainInfo","$it")
        viewModel.addSelectedContactsToMainScreen(it)
    }
    navController.currentBackStackEntry?.savedStateHandle?.remove<MutableList<Int>>("SelectedUsers")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
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
                            color = Color.Red,
                            modifier = Modifier.clickable {
                                if (sendSMSPermissionState.status.isGranted) {
                                    navController.navigate("testView")
                                } else {
                                    sendSMSPermissionState.launchPermissionRequest()
                                }
                            }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
                    .weight(weight = 1f, fill = false)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(0xFFE0E0E0))
                        .border(BorderStroke(2.dp, Color(0xFFEEEEEE)))
                        .clickable {
                            navController.navigate("selectContactView")
                        }
                ) {
                    Text(
                        text = " Choose contacts",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(8.dp, 0.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    IconButton(
                        onClick = {
                            navController.navigate("selectContactView")
                        },
                    ) {
                        Icon(
                            Icons.Default.Contacts,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
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
                        repeat(viewModel.contactsList.size) {
                            val contact = viewModel.contactsList[it]
                            key(contact.id) { //ref: https://developer.android.com/jetpack/compose/lifecycle
                                CustomLayoutContact(
                                    initials = contact.initials,
                                    name = contact.name,
                                    mobileNumber = contact.mobileNumber
                                ) {
                                    viewModel.deleteUser(contact)
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(0.dp, 15.dp, 0.dp, 0.dp)
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
                        .padding(10.dp, 20.dp, 10.dp, 15.dp)
                        .border(3.dp, Color.LightGray, RoundedCornerShape(3))
                        .fillMaxWidth()
                        .clickable {
                            if (sendSMSPermissionState.status.isGranted) {
                                navController.navigate("CSVView")
                            } else {
                                sendSMSPermissionState.launchPermissionRequest()
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 10.dp)
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

                Divider(color = Color.Black, thickness = 1.dp)

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp, 20.dp, 10.dp, 20.dp)
                ) {
                    OutlinedTextFieldUI(
                        smsMessage,
                        onMessageChanged = { viewModel.changeSmsMessage(it) }
                    )
                }

                Divider(color = Color.Black, thickness = 1.dp)

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp, 15.dp, 10.dp, 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text="Result Message :",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(15.dp, 15.dp, 15.dp, 5.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text=viewModel.messageStatus,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 20.dp)
                            .fillMaxWidth()
                    )
                }
            }

            Button(
                enabled = isSendSMSButtonEnable,
                onClick = {
                    if (sendSMSPermissionState.status.isGranted) {
                        viewModel.sendSMS()
                    } else {
                        sendSMSPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.align(CenterHorizontally),
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 15.dp,
                    top = 10.dp,
                    end = 15.dp,
                    bottom = 10.dp
                )
            ) {
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


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BulkSMSTheme {
        HomeScreen(
            navController = NavHostController(LocalContext.current)
        )
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

        var totalHeight = 120
        val layoutWidth = constraints.maxWidth

        var xPositionCalc = 0
        placeables.forEach { placeable ->

            val placeableWidth = placeable.width

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

                val placeableWidth = placeable.width

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

/*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    permissionState: PermissionState,
) {

//    PermissionRequired(
//        permissionState = permissionState,
//        permissionNotGrantedContent = { /* ... */ },
//        permissionNotAvailableContent = { /* ... */ }
//    ) {
//        // Open Camera
//    }
}


/**
 * Composable helper for permission checking
 *
 * onDenied contains lambda for request permission
 *
 * @param permission permission for request
 * @param onGranted composable for [PackageManager.PERMISSION_GRANTED]
 * @param onDenied composable for [PackageManager.PERMISSION_DENIED]
 */
@Composable
fun ComposablePermission(
    permission: String,
    onDenied: @Composable (requester: () -> Unit) -> Unit,
    onGranted: @Composable () -> Unit
) {
    val ctx = LocalContext.current

    // check initial state of permission, it may be already granted
    var grantState by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                ctx,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    if (grantState) {
        onGranted()
    } else {
        val launcher: ManagedActivityResultLauncher<String, Boolean> =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
                grantState = it
            }
        onDenied { launcher.launch(permission) }
    }
}

//                    val csvFile = context.contentResolver.openInputStream(photoUri.value!!)
//                    val isr = InputStreamReader(csvFile, "UTF-8")
//                    println(BufferedReader(isr).readLines())


//                            Image(
//                                painter = painterResource(Icons.Default.Contacts), //R.drawable.keyboard_arrow_down),
//                                "Contact dropdown",
//                                modifier = Modifier
//                                    .size(40.dp)
//                                    .align(Alignment.CenterVertically)
//                                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
//                                    .clickable {
//                                        navController.navigate("selectContactView")
//                                    }
//                            )

 */