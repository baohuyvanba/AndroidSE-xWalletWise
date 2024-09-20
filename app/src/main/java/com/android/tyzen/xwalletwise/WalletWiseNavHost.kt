package com.android.tyzen.xwalletwise

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.compiler.plugins.kotlin.ComposeCallableIds.remember
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.model.user.isFirstTimeLaunch

//UI
import com.android.tyzen.xwalletwise.ui.activity.HomeScreen
import com.android.tyzen.xwalletwise.ui.activity.category.CategoriesListScreen
import com.android.tyzen.xwalletwise.ui.activity.category.CategoryDetailScreen
import com.finance.android.walletwise.ui.activity.chatbot.ChatBotScreen
import com.android.tyzen.xwalletwise.ui.activity.transaction.TransactionDetailScreen
import com.android.tyzen.xwalletwise.ui.activity.transaction.TransactionsListScreen
import com.android.tyzen.xwalletwise.ui.activity.sercurity.VerifyUserScreen
import com.android.tyzen.xwalletwise.ui.activity.sercurity.SetupBiometricsScreen
import com.android.tyzen.xwalletwise.ui.activity.user.ProfileSetupScreen
import com.android.tyzen.xwalletwise.ui.activity.sercurity.SetupPinScreen
import com.android.tyzen.xwalletwise.ui.activity.user.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//Control application navigation with NavHost
@Composable
fun WalletWiseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier, )
{
    //Setup Start Screen
    val context = LocalContext.current
    val startDestination by rememberSaveable { mutableStateOf(
        if (isFirstTimeLaunch(context)) {
            welcomeScreen.route
        }
        else {
            pinVerificationScreen.route
        }
    ) }

    /**
     * Navigation Host
     */
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.background(Color.Transparent), )
    {
        /**
         * WELCOME =================================================================================
         */
        composable(
            route = welcomeScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))  //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            }, )
        {
            WelcomeScreen(
                onStartPress = {
                    navController.navigateSingleTopTo(profileSetupScreen.route) }
            )
        }

        /**
         * SETUP PROFILE ===========================================================================
         */
        //SetupProfileScreen
        composable(
            route = profileSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, )
        {
            ProfileSetupScreen(
                navigateToPINSetup = {
                    navController.navigateSingleTopTo(pinSetupScreen.route)
                },
            )
        }
        //SetupPinScreen
        composable(
            route = pinSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, )
        {
            SetupPinScreen(
                onNextClickToBiometric = {
                    navController.navigateSingleTopTo(biometricsSetupScreen.route)
                },
                onNextClickToInputPin  = {
                    navController.navigateSingleTopTo(pinVerificationScreen.route)
                },
            )
        }
        //SetupBiometricScreen
        composable(
            route = biometricsSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            }
        )
        {
            SetupBiometricsScreen(
                onNextClick = {
                    navController.navigateSingleTopTo(pinVerificationScreen.route)
                },
            )
        }
        /**
         * VERIFY USER =============================================================================
         */
        composable(
            route = pinVerificationScreen.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                scaleOut(tween(500))
            }, )
        {
            VerifyUserScreen(
                onNavigateHome = {
                    navController.navigate(homeScreen.route){
                        popUpTo(pinVerificationScreen.route) { inclusive = true }
                    }
                },
            )
        }
        /**
         * MAIN APP SCREENS ========================================================================
         */
        //HomeScreen -------------------------------------------------------------------------------
        composable(
            route = homeScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            HomeScreen(
                onNavigateToTransactionList = {
                    navController.navigateSingleTopTo(transactionListScreen.route)
                },
                onNavigateToTransactionDetail = { transactionId ->
                    navController.navigate(route = "${transactionDetailScreen.route}/$transactionId")
                },
                onNavigateToCategoryList = {
                    navController.navigateSingleTopTo(categoryListScreen.route)
                },
                onNavigateToCategoryDetail = { categoryId ->
                    navController.navigate(route = "${categoryDetailScreen.route}/$categoryId")
                },
                //FAB Button Actions
                onClickAddManual = {
                    navController.navigate(route = "${transactionDetailScreen.route}/-1")
                },
                onClickAddOCR = {
                    navController.navigate(route = "${transactionDetailScreen.route}/-2")
                },
                onClickAddText = {
                    navController.navigate(route = "${transactionDetailScreen.route}/-3")
                },
                //Quick Access Actions
                quickAccessOnAnalysisClick = {
                    /*TODO*/
                },
                quickAccessOnAIChatClick = {
                    navController.navigate(route = chatbotScreen.route)
                },
                quickAccessOnRemindClick = {
                    /*TODO*/
                },
            )
        }
        //SettingScreen ----------------------------------------------------------------------------
        composable(
            route = settingScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {

        }
        /**
         * TRANSACTION SCREEN ======================================================================
         */
        //TransactionsListScreen -------------------------------------------------------------------
        composable(
            route = transactionListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            },
        ) {
            TransactionsListScreen(
                onTransactionClick = { transactionId ->
                    navController.navigate(route = "${transactionDetailScreen.route}/$transactionId")
                },
            )
        }

        //TransactionDetailScreen ------------------------------------------------------------------
        composable(
            route = "${transactionDetailScreen.route}/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.LongType } ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioLowBouncy
                    ))
            },
            exitTransition = { slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                ))
            },
        ) {
            TransactionDetailScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        /**
         * CATEGORY SCREENS ========================================================================
         */
        //CategoryListScreen -----------------------------------------------------------------------
        composable(
            route = categoryListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            CategoriesListScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(route = "${categoryDetailScreen.route}/$categoryId")
                },
            )
        }

        //CategoryDetailScreen ---------------------------------------------------------------------
        composable(
            route = "${categoryDetailScreen.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType } ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioLowBouncy
                    ), )//tween(durationMillis = 300, easing = FastOutSlowInEasing,), ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = { slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                ), )
            },
        ) {
            CategoryDetailScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        /**
         * CHATBOT SCREENS =========================================================================
         */
        composable(
            route = chatbotScreen.route,
        )
        {
            ChatBotScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

//Navigation FUNCTION
fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route)
{
    //Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
    {
        saveState = true
    }
    //Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    //Restore state when re-selecting a previously selected item
    restoreState = true
}