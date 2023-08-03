package com.ajay.bulksms.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajay.bulksms.MainViewModel
import com.ajay.bulksms.R
import com.ajay.bulksms.ui.theme.BulkSMSTheme
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
        composable(Screen.detailView.route) {
            ContactListScreen(navController)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val smsMessage = viewModel.smsMessage
    val startRange = viewModel.startRange
    val endRange = viewModel.endRange
    var resultMessage: String
    val context = LocalContext.current

    val sendSMSPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.SEND_SMS
    )

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
                            color = Color.Red,
                            modifier = Modifier.clickable {
                                navController.navigate("detailView")
                            }
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
                                repeat(viewModel.contactsList.size) {
                                    val contact = viewModel.contactsList[it]
                                    key(contact.id) { //ref: https://developer.android.com/jetpack/compose/lifecycle
                                        ContactEdit(
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
                        Column {
                            PhotoPickerDemoScreen()
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
                                    startRange,
                                    onValueChanged = { viewModel.changeStartRange(it) }
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
                                    endRange,
                                    onValueChanged = { viewModel.changeEndRange(it) }
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(10.dp, 40.dp, 10.dp, 10.dp)
                        ) {
                            OutlinedTextFieldUI(
                                smsMessage,
                                onMessageChanged = { viewModel.changeSmsMessage(it) }
                            )
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
                                onClick = {
                                    if (sendSMSPermissionState.status.isGranted) {
                                        resultMessage = viewModel.sendSMSTemp()
                                        Toast.makeText(context, resultMessage, Toast.LENGTH_SHORT).show()
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoPickerDemoScreen() {
    val context = LocalContext.current

    //The URI of the photo that the user has picked
    //var photoUri: Uri? by remember { mutableStateOf(null) }
    //val uri = remember { mutableStateOf<Uri?>(null) }
    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        //When the user has selected a photo, its URI is returned here
        extractCSVFile(uri, context = context)
    }

    if (Build.VERSION.SDK_INT >= 30) {
        val readExternalStoragePermissionState = rememberPermissionState(
            permission = android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
        )
        if (readExternalStoragePermissionState.status.isGranted) {
            Toast.makeText(context, "permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "permission not granted", Toast.LENGTH_SHORT).show()
            //readExternalStoragePermissionState.launchPermissionRequest()
        }
    }

    Column {
        Button(
            onClick = {
                launcher.launch(arrayOf("*/*"))
            }
        ) {
            Text(text = "Open Document")
        }
    }
}

private fun extractCSVFile(uri: Uri?, context: Context) {
    uri?.let {
        try {
            val inputStream = context.contentResolver.openInputStream(it)
            inputStream?.bufferedReader()?.use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val data = line.split(",")
                    println("CSV line data: $data")
                    line = reader.readLine()
                }
            }
            inputStream?.close()
        } catch (e: SecurityException) {
            // Handle any errors that may occur during file reading
            println("Error reading CSV file: ${e.message}")
            println("Error reading CSV file: ${e.stackTrace}")
            if (e.message?.contains("com.android.externalstorage has no access to content") == true){
                if (Build.VERSION.SDK_INT >= 30) {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:com.android.externalstorage")
                    startActivity(context, intent, null)
                } else {
                    Toast.makeText(context, "Security issue, please check storage permissions.", Toast.LENGTH_LONG).show()

                }
            } else {
                Toast.makeText(context, "Security issue, please check storage permissions.", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception) {
            println("Error reading CSV file: ${e.message}")
            println("Error reading CSV file: ${e.stackTrace}")
        }
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

 */