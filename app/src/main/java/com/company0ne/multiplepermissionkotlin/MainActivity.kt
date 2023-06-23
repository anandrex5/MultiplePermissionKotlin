package com.company0ne.multiplepermissionkotlin

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isCameraPermissionGranted = false
    private var isAccessNetworkPermissionGranted = false
    private var isCallLogReadPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Request Multiple Permissions
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

                isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: isReadPermissionGranted
                isCameraPermissionGranted =
                    permissions[Manifest.permission.CAMERA] ?: isCameraPermissionGranted
                isAccessNetworkPermissionGranted =
                    permissions[Manifest.permission.ACCESS_NETWORK_STATE]
                        ?: isAccessNetworkPermissionGranted
                isCallLogReadPermissionGranted =
                    permissions[Manifest.permission.READ_CALL_LOG] ?: isCallLogReadPermissionGranted


            }
        requestPermission()
    }

    private fun requestPermission() {
        //read storage permission from Manifest file
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        //read camera permission from Manifest file
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        //read network permission from Manifest file
        isAccessNetworkPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED

        //read callLogs permission from Manifest file
        isCallLogReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

        //if request is not null then it will ask for permissions
        val permissionRequest: MutableList<String> = ArrayList()

        if (!isReadPermissionGranted) {

            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        if (!isCameraPermissionGranted) {

            permissionRequest.add(Manifest.permission.CAMERA)

        }

        if (!isAccessNetworkPermissionGranted) {

            permissionRequest.add(Manifest.permission.ACCESS_NETWORK_STATE)

        }

        if (!isCallLogReadPermissionGranted) {

            permissionRequest.add(Manifest.permission.READ_CALL_LOG)

        }
        if (permissionRequest.isNotEmpty()) {

            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}


/*
Another simpler working approach 
class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_CALL_LOG
    )
    private val permissionResults = mutableMapOf<String, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissionResults.clear()
                permissions.forEach { (permission, isGranted) ->
                    permissionResults[permission] = isGranted
                }
                handlePermissionResults()
            }

        requestPermissions()
    }

    private fun requestPermissions() {
        val permissionRequest = permissions.filterNot { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest)
        } else {
            handlePermissionResults()
        }
    }

    private fun handlePermissionResults() {
        // Handle the permission results here
        // Check the granted status using permissionResults[permission]
    }
}
 */










