package com.ajay.bulksms.components

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ajay.bulksms.ContactsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun ContactViewHeader(
    navController: NavHostController,
    viewModel: ContactsViewModel
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
                    text = "Back",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        //navController.navigate("MainView")
                        navController.popBackStack(Screen.detailView.route, inclusive = true)
                    }
                )

                Spacer(modifier = Modifier.weight(1.0f))

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
                    text = "Done",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    modifier = Modifier.clickable {
                        viewModel.addContactstoUser()
                        navController.popBackStack(Screen.detailView.route, inclusive = true)
                    }
                )
            }
        }
    }
}
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
                        .makeText(context, "Clickable Row Example", Toast.LENGTH_SHORT)
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
                    modifier = Modifier
                        .size(60.dp)
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
            } else {
                Surface(
                    modifier = Modifier
                        .size(60.dp)
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactListScreen(
    navController: NavHostController,
    viewModel: ContactsViewModel = viewModel()
) {
    val context = LocalContext.current

    val readContactsPermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_CONTACTS
    )

    DisposableEffectWithLifecycle(
        onCreate = {
            println("oncreate")
            if (readContactsPermissionState.status.isGranted) {
                viewModel.getAllContacts(context)
                viewModel.deviceContactsList.forEach{contact ->
                    println(contact)
                }
                Toast.makeText(context, "contacts print done", Toast.LENGTH_SHORT).show()
            } else {
                readContactsPermissionState.launchPermissionRequest()
                Toast.makeText(context, "Permission not provided !", Toast.LENGTH_SHORT).show()

            }
        }
    )

    LazyColumn {
        items(
            count = 1,
            key = null
        ) {
            ContactViewHeader(navController, viewModel)

            if (readContactsPermissionState.status.isGranted) {
                viewModel.deviceContactsList.forEach { contact ->
                    println("lazycolumbn $contact")
                    ContactView(
                        initials = contact.initials,
                        name = contact.displayName,
                        mobileNumber = contact.phoneNumber,
                        isSelected = remember {
                            mutableStateOf(false)
                        }
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ){
                    Button(
                        onClick = {
                            readContactsPermissionState.launchPermissionRequest()
                        },
                        //modifier = Modifier.align(Alignment.CenterHorizontally),
                        // Uses ButtonDefaults.ContentPadding by default
                        contentPadding = PaddingValues(
                            start = 15.dp,
                            top = 10.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        ),
                        modifier = Modifier.align(CenterHorizontally)
                    ) {
                        Text(
                            text = "Provide permission to fetch contacts !",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DisposableEffectWithLifecycle(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onStop: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onDestroy: () -> Unit = {},
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentOnCreate by rememberUpdatedState(onCreate)
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnDestroy by rememberUpdatedState(onDestroy)

    DisposableEffect(lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> currentOnCreate()
                Lifecycle.Event.ON_START -> currentOnStart()
                Lifecycle.Event.ON_PAUSE -> currentOnPause()
                Lifecycle.Event.ON_RESUME -> currentOnResume()
                Lifecycle.Event.ON_STOP -> currentOnStop()
                Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BulkSMSTheme {
//        ContactListScreen()
//    }
//}