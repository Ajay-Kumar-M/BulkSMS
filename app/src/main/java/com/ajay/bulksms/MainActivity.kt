package com.ajay.bulksms

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ajay.bulksms.ui.theme.BulkSMSTheme
import com.ajay.bulksms.views.MainView


class MainActivity : ComponentActivity() {
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

            @RequiresApi(Build.VERSION_CODES.R)
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

                    // Check for permission
                    val permissionCheck = ContextCompat.checkSelfPermission(
                        this,
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    )

// Request permission if not granted
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission denied main", Toast.LENGTH_SHORT).show()
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf<String>(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION),501
                        )
                    }

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