package com.android.tyzen.xwalletwise

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseFloatingBottomBar
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseFloatingTopBar
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import dagger.hilt.android.AndroidEntryPoint

//START POINT
@AndroidEntryPoint
class WalletWiseActivity : AppCompatActivity() {
    //PERMISSION Request
    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission(), )
    { isGranted ->
        if (isGranted) {
            Log.e("Permission", "Granted")
        } else {
            Log.e("Permission", "Den ied")
        }
    }
    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.e("Permission", "Was Previously granted")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.e("Permission", "Show Permission Dialog")
            else -> requestPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    //ON CREATE APPLICATION
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        enableEdgeToEdge()

        //Application's Content
        setContent {
            requestCameraPermission()
            WalletWiseTheme {
                XWalletWise()
            }
        }
    }
}

@Composable
fun XWalletWise()
{
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    var currentDestination = currentBackStack?.destination?.route
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    /**
     * Application Bars Types ==================================================================
     */
    val barsType: Int = when {
        tabNavigationScreens.contains(currentDestination) -> 0
        else -> -1
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = currentBackStack?.destination?.route) {
        currentDestination = currentBackStack?.destination?.route
        selectedTab = when (currentDestination) {
            homeScreen.route -> 0
            transactionListScreen.route -> 1
            categoryListScreen.route -> 2
            else -> -1
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent), )
    { innerPadding ->
        Box(modifier = Modifier.fillMaxSize())
        {
            /**
             * Navigation Host =====================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), )
            {
                when (barsType) {
                    0 -> Spacer(
                        modifier = Modifier
                            .height((screenHeight * 0.05).dp)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )
                }

                WalletWiseNavHost(
                    navController = navController,
                )
            }

            /**
             * Top Floating App Bar ================================================================
             */
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top, )
            {
                when (barsType) {
                    0 -> WalletWiseFloatingTopBar(
                        showActionButton = true,
                        onActionClick = { /*TODO*/ },
                    )
                }
            }

            /**
             * Bottom Floating App Bar =============================================================
             */
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom, )
            {
                when (barsType) {
                    0 -> WalletWiseFloatingBottomBar(
                        selectedTab = selectedTab,
                        onTabSelected = { index ->
                            selectedTab = index
                            navController.navigate(
                                when (index) {
                                    0 -> homeScreen.route
                                    1 -> transactionListScreen.route
                                    2 -> categoryListScreen.route
                                    else -> homeScreen.route
                                }, )
                            {
                                popUpTo(homeScreen.route) { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        onAddTransactionPress = { transactionId ->
                            navController.navigate(route = "${transactionDetailScreen.route}/$transactionId")
                        },
                    )
                }
            }
        }
    }
}

