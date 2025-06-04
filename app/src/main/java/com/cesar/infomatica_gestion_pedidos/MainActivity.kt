package com.cesar.infomatica_gestion_pedidos

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cesar.infomatica_gestion_pedidos.ui.navigation.SetupNavGraph
import com.cesar.infomatica_gestion_pedidos.ui.theme.Infomatica_gestion_pedidosTheme
import com.cesar.infomatica_gestion_pedidos.util.NetworkChangeWorker
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var constraints =  Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();
        val onetimeJob =  OneTimeWorkRequest.Builder(NetworkChangeWorker::class)
            .setConstraints(constraints).build(); // or PeriodicWorkRequest
        WorkManager.getInstance(this@MainActivity).enqueue(onetimeJob);

        setContent {
            Infomatica_gestion_pedidosTheme {
               val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }

        validatePermissionNotification()
    }

    fun validatePermissionNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) { } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {
            val settingsIntent: Intent = Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(settingsIntent)
        }
    }

}