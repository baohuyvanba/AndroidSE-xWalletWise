package com.android.tyzen.xwalletwise

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

interface WalletWiseDestination {
    val icon : ImageVector
    val route: String
}

/**
 * AUTHENTICATION ==================================================================================
 */
//WELCOME SCREEN -----------------------------------------------------------------------------------
object welcomeScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "welcomeScreen"
}
//PROFILE SETUP SCREEN -----------------------------------------------------------------------------
object profileSetupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "profileSetupScreen"
}
//PIN SETUP SCREEN ---------------------------------------------------------------------------------
object pinSetupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.CheckCircle
    override val route = "pinSetupScreen"
}
//BIOMETRICS SETUP SCREEN --------------------------------------------------------------------------
object biometricsSetupScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.CheckCircle
    override val route = "biometricsSetupScreen"
}
//PIN VERIFICATION SCREEN --------------------------------------------------------------------------
object pinVerificationScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.CheckCircle
    override val route = "pinVerificationScreen"
}

/**
 * MAIN TABS NAVIGATION ============================================================================
 */
//HOME SCREEN --------------------------------------------------------------------------------------
object homeScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "homeScreen"
}

//SETTINGS SCREEN ----------------------------------------------------------------------------------
object settingScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "settingScreen"
}

/**
 * TRANSACTION SCREENS =============================================================================
 */
//TRANSACTION LIST SCREEN --------------------------------------------------------------------------
object transactionListScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "transactionListScreen"
}
//TRANSACTION DETAIL SCREEN ------------------------------------------------------------------------
object transactionDetailScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Menu
    override val route = "transactionDetailScreen"
}

/**
 * CATEGORY SCREENS ================================================================================
 */
//CATEGORIES LIST SCREEN ---------------------------------------------------------------------------
object categoryListScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "categoryListScreen"
}
//CATEGORY DETAIL SCREEN ---------------------------------------------------------------------------
object categoryDetailScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Menu
    override val route = "categoryDetailScreen"
}

/**
 * CHATBOT SCREENS =================================================================================
 */
object chatbotScreen : WalletWiseDestination {
    override val icon: ImageVector = Icons.Default.Home
    override val route = "chatbotScreen"
}

/**
 * APPLICATION BARS SETTINGS =======================================================================
 */
val authenticationScreens = mutableListOf(
    welcomeScreen.route,
    profileSetupScreen.route,
    pinSetupScreen.route,
    pinVerificationScreen.route
) //No Top or Bottom bars or FAB

val tabNavigationScreens = mutableListOf(
    homeScreen.route,
    transactionListScreen.route,
    categoryListScreen.route,
    settingScreen.route,
) //Top (main) and Bottom (navigation) bars and FAB

val homepageScreens = mutableListOf(
    homeScreen.route,
) //Show FAB on Homepage only, or not :>