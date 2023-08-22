package com.ajay.bulksms.views

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ajay.bulksms.viewModel.CSVActivityViewModel
import com.ajay.bulksms.R
import com.ajay.bulksms.components.LabelAndPlaceHolderTextField
import com.ajay.bulksms.components.OutlinedTextFieldUI
import com.ajay.bulksms.components.Screen
import com.ajay.bulksms.ui.theme.BulkSMSTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@Composable
fun CSVView(
    navController: NavController,
    viewModel: CSVActivityViewModel = viewModel()
) {
    val testSmsMessage = viewModel.csvSmsMessage
    val startRange = viewModel.startRange
    val endRange = viewModel.endRange

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
                                    navController.popBackStack(Screen.CSVView.route, inclusive = true)
                                }
                        )
                    }
                }
            }

            CSVPickerScreen(viewModel)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 20.dp, 10.dp, 10.dp)
            ){
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
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

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(0.dp, 40.dp, 0.dp, 0.dp)

                ){
                    OutlinedTextFieldUI(
                        testSmsMessage,
                        onMessageChanged = { viewModel.changeCSVSmsMessage(it) }
                    )
                }
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp, 30.dp, 10.dp, 10.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ){

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
                Button(
                    onClick = {
                        viewModel.sendSMS()
                    },
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CSVPickerScreen(viewModel: CSVActivityViewModel) {
    val context = LocalContext.current

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        //When the user has selected a photo, its URI is returned here
        viewModel.extractCSVFile(uri, context = context)
    }

    val readExternalStoragePermissionState = rememberPermissionState(
        permission = Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )


    Column (
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp, 30.dp, 10.dp, 10.dp)
            .border(3.dp, Color.LightGray, RoundedCornerShape(3))
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
                .clickable {
                    if (Build.VERSION.SDK_INT >= 30) {
                        launcher.launch(arrayOf("*/*"))
                    } else {
                        if (readExternalStoragePermissionState.status.isGranted) {
                            Toast
                                .makeText(
                                    context,
                                    "Permission granted for reading Storage!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            launcher.launch(arrayOf("*/*"))
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Permission not granted for reading Storage",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            readExternalStoragePermissionState.launchPermissionRequest()
                        }
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.text_snippet),
                "Contact dropdown",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Select Document from Storage",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
                Text(
                    text = "(* CSV should contain only one column with phone numbers alone.\n* Each row will be considered as a phone number.\n* Column header is also not necessary.)",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CSVViewPreview() {
    BulkSMSTheme {
        CSVView(
            navController = NavHostController(LocalContext.current),
            viewModel = CSVActivityViewModel()
        )
    }
}

/*
    //The URI of the photo that the user has picked
    //var photoUri: Uri? by remember { mutableStateOf(null) }
    //val uri = remember { mutableStateOf<Uri?>(null) }
 */