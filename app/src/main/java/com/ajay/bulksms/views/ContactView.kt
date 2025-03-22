package com.ajay.bulksms.views

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ajay.bulksms.AppViewModelProvider
import com.ajay.bulksms.R
import com.ajay.bulksms.components.DisposableEffectWithLifecycle
import com.ajay.bulksms.components.Screen
import com.ajay.bulksms.components.customScrollbar
import com.ajay.bulksms.viewModel.ContactsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@Composable
fun ContactViewHeader(
    navController: NavHostController,
    viewModel: ContactsViewModel
) {
    val context = LocalContext.current

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
                        navController.popBackStack(Screen.SelectContactView.route, inclusive = true)
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

                Image(
                    painter = painterResource(R.drawable.icon_sync),
                    "Contact refresh",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.toggleCircularProgressIndicator()
                            viewModel.getAllDeviceContacts(context)
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
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "SelectedUsers",
                            viewModel.selectedContacts
                        )
                        navController.popBackStack(Screen.SelectContactView.route, inclusive = true)
                    }
                )
            }
        }
    }
}

@Composable
fun ContactView(
    id: Int,
    initials: String,
    name: String,
    mobileNumber: String,
    isSelected: MutableState<Boolean>,
    viewModel: ContactsViewModel
) {
    isSelected.value = viewModel.selectedContacts.contains(id)
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isSelected.value = !isSelected.value
                if (isSelected.value) {
                    viewModel.addContactToSelectedList(id)
                } else {
                    viewModel.removeContactFromSelectedList(id)
                }
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
        if (isSelected.value) {
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

@OptIn(ExperimentalPermissionsApi::class, FlowPreview::class)
@Composable
fun ContactListScreen(
    navController: NavHostController,
    viewModel: ContactsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
//    val contactsList = viewModel.allContacts.collectAsStateWithLifecycle(emptyList())
    val filteredContacts = viewModel.filterContact
    val readContactsPermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_CONTACTS
    )

    val contactSearchString = viewModel.contactSearchString
    val prefs by lazy {
        context.getSharedPreferences("prefs", MODE_PRIVATE)
    }
    val scrollPosition = prefs.getInt("scroll_position",0)
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )

    DisposableEffectWithLifecycle(
        onCreate = {
            if (!readContactsPermissionState.status.isGranted) {
                readContactsPermissionState.launchPermissionRequest()
                Toast.makeText(context, "Permission not provided !", Toast.LENGTH_LONG).show()
            }
        }
    )

    Column {
        ContactViewHeader(navController, viewModel)
        SearchView(
            contactSearchString,
            { viewModel.search() },
            { viewModel.changeContactSearchString(it) }
        )

        if (readContactsPermissionState.status.isGranted) {
            if (filteredContacts.isEmpty() && !isRefreshing) {
                Text(
                    text = "Please refresh to fetch contacts !",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                if(isRefreshing) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(size = 28.dp),
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(text = "Loading...")
                    }
                } else {
                    LaunchedEffect(lazyListState) {
                        snapshotFlow {
                            lazyListState.firstVisibleItemIndex
                        }
                            .debounce(1000)
                            .collectLatest { index ->
                                prefs.edit() {
                                    putInt("scroll_position", index)
                                }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.customScrollbar(lazyListState, false),
                        state = lazyListState
                    ) {
                        items(filteredContacts) { contact ->
                            ContactView(
                                id = contact.id,
                                initials = contact.initials,
                                name = contact.displayName,
                                mobileNumber = contact.phoneNumber,
                                isSelected = remember {
                                    mutableStateOf(false)
                                },
                                viewModel
                            )

                        }

                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(contactSearchString: TextFieldValue, onSearchClicked: () -> Unit, onMessageChanged: (TextFieldValue) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = contactSearchString,
        onValueChange = { value ->
            onMessageChanged(value)
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
                    .clickable {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onSearchClicked()
                    }
            )
        },
        trailingIcon = {
            if (contactSearchString != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        onMessageChanged(TextFieldValue("")) // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSearchClicked()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = colorResource(id = R.color.teal_200),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

/*

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BulkSMSTheme {
        ContactListScreen()
    }
}


//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),


            onGo = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSearchClicked()
            },
            onSearch = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSearchClicked()
            }


 */
