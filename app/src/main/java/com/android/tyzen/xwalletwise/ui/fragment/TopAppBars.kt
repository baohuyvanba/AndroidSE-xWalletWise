package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.android.tyzen.xwalletwise.ui.theme.secondaryContainerLight

@Preview(showBackground = true)
@Composable
fun PreviewWalletWiseAppBar() {
    WalletWiseTheme {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color.White), )
        {
            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))

            WalletWiseTopAppBar(
                title = "WalletWise",
                useIconForTitle = true,
                showNavigationButton = true,
                showActionButton = true,
                onNavigationClick = { /*TODO*/ },
                onActionClick = { /*TODO*/ },
            )

            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))

            WalletWiseTopAppBar(
                title = "WalletWise",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                showActionButton = false,
                onNavigationClick = { /*TODO*/ },
            )

            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))

            WalletWiseMainTopBar(
                showActionButton = true,
            )

            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))

            WalletWiseBottomBar(
                selectedTab = 1,
                onTabSelected = { /*TODO*/ },
                onHomeClick = { /*TODO*/ },
                onExpenseListClick = { /*TODO*/ },
                onCategoryListClick = { /*TODO*/ },
                onSettingsClick = {/* TODO */},
            )

            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))
        }
    }
}

//TopAppBar composable -----------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletWiseTopAppBar(
    title: String,
    useIconForTitle: Boolean = true,
    showNavigationButton: Boolean = true,
    navigationIcon: ImageVector = Icons.Default.Menu,
    navigationButton: @Composable (() -> Unit)? = null,
    onNavigationClick: () -> Unit = {},
    showActionButton: Boolean = true,
    actionButton: @Composable (() -> Unit)? = null,
    onActionClick: () -> Unit = {}, )
{
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    TopAppBar(
        modifier = Modifier
            .height((screenHeight*0.06).dp)
            .fillMaxWidth(),
        title = {
            if (useIconForTitle)
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center, )
                {
                    Image(
                        painter = painterResource(id = R.drawable.application_logo),
                        contentDescription = "WalletWise application text logo", )
                }
            }
            else
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,)
                {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                }
            }
        },
        navigationIcon = {
            if (showNavigationButton)
            {
                IconButton(onClick = onNavigationClick)
                {
                    navigationButton?.invoke() ?: Icon(navigationIcon, contentDescription = navigationIcon.name)
                }
            }
        },
        actions = {
            if (showActionButton)
            {
                IconButton(onClick = onActionClick)
                {
                    actionButton?.invoke() ?: Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }
        }
    )
}


//NavigationAppBar composable ----------------------------------------------------------------------
@Composable
fun WalletWiseBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onExpenseListClick: () -> Unit,
    onCategoryListClick: () -> Unit,
    onSettingsClick: () -> Unit, )
{
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    NavigationBar(
        modifier = Modifier
            .height((screenHeight*0.06).dp)
            .fillMaxWidth(), )
    {
        //HOME
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home")
            },
            selected = selectedTab == 0,
            onClick = {
                onTabSelected(0)
                onHomeClick()
            }
        )

        //EXPENSE LIST
        NavigationBarItem(
            icon = {
                Icon(Icons.AutoMirrored.Filled.List,
                    contentDescription = "Expense List")
            },
            selected = selectedTab == 1,
            onClick = {
                onTabSelected(1)
                onExpenseListClick()
            }
        )

        //CATEGORY LIST
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category),
                    contentDescription = "Category List")
            },
            selected = selectedTab == 2,
            onClick = {
                onTabSelected(2)
                onCategoryListClick()
            }
        )

        //SETTING
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings")
            },
            selected = selectedTab == 3,
            onClick = {
                onTabSelected(3)
                onSettingsClick()
            }
        )

    }
}

/**
 * Floating Top Bar ================================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletWiseMainTopBar(
    showActionButton: Boolean = true,
    actionButton: @Composable() (() -> Unit)? = null,
    onActionClick: () -> Unit = {}, )
{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    //Max Width x 0.05*Height
    Box(
        modifier = Modifier
            .height((screenHeight * 0.05).dp)
            .fillMaxWidth()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    secondaryContainerLight,
                    secondaryContainerLight,
                )
            )),
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
            modifier = Modifier.fillMaxSize(),
            title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.application_logo),
                        contentDescription = "WalletWise application text logo",
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }
            },
            actions = {
                if (showActionButton) {
                    IconButton(onClick = onActionClick) {
                        actionButton?.invoke()
                            ?: Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                    }
                }
            }
        )
    }
}

/**
 * View Detail Top App Bar =========================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletWiseViewDetailTopAppBar(
    title: String,
    navigationButton: @Composable (() -> Unit)? = null,
    onNavigationClick: () -> Unit = {}, )
{
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    TopAppBar(
        modifier = Modifier
            .height((screenHeight*0.06).dp)
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = Color.Black,
        ),
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,)
            {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick)
            {
                navigationButton?.invoke() ?: Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}

