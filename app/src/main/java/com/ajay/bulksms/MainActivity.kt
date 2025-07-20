package com.ajay.bulksms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajay.bulksms.ui.theme.BulkSMSTheme
import com.ajay.bulksms.views.MainView


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BulkSMSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BulkSMSTheme {
        MainView()
    }
}

/*
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                // Permission is granted, proceed with your operation
//            } else {
//                // Permission is denied, inform the user
//                Toast.makeText(this, "Permission denied main", Toast.LENGTH_SHORT).show()
//            }
//        }
    private val getPreviewImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument(), { uri ->
            Toast.makeText(this, "$uri", Toast.LENGTH_SHORT).show()
        })
 */