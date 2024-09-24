package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

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
                navigationIcon = Icons.Default.ArrowBack,
                showActionButton = false,
                onNavigationClick = { /*TODO*/ },
            )

            Spacer(modifier = Modifier.height(16.dp).fillMaxWidth().background(Color.Black).padding(16.dp))

            WalletWiseFloatingTopBar(
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

            WalletWiseFloatingBottomBar(
                selectedTab = 0,
                onTabSelected = { /*TODO*/ },
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
                Icon(Icons.Default.List,
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
fun WalletWiseFloatingTopBar(
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
            .background(brush =
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFc7e1fc).copy(alpha = 0.8f),
                        Color(0xFF6be5ba).copy(alpha = 0.8f),
                    )
                )
            ),
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

/**
 * Floating Bottom Bar =============================================================================
 */
@Composable
fun GlassButtonFloatingBottomBar(
    icon: ImageVector,
    title: String,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit, )
{
    var iconColor = Color(0xFF1C9516)
    val iconSize  = 80
    val bottomPadding: Dp
    val glowRadius by animateFloatAsState(targetValue = if (isSelected) 50f else 1f, label = "glass_floating_icon_glow")
    val brushGradient: Brush = Brush.radialGradient(
        colors = listOf(
            Color.White.copy(alpha = if (isSelected) 0.5f else 0f),
            Color.Transparent
        ),
        radius = glowRadius
    )

    if (isSelected) {
        bottomPadding = 16.dp
    }
    else {
        iconColor = iconColor.copy(alpha = 0.8f)
        bottomPadding = 0.dp
    }

    IconButton(
        modifier = Modifier.size(iconSize.dp),
        onClick = onClick, )
    {
        Box(
            modifier = Modifier
                .size(iconSize.times(0.75).dp)
                .background(
                    brush = brushGradient,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center, )
        {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier.padding(bottom = bottomPadding)
            )

            if (isSelected) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize   = MaterialTheme.typography.bodySmall.fontSize.times(1.1f)
                    ),
                    color = iconColor,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }

        //Bottom Light -----------------------------------------------------------------------------
        if (isSelected) {
            Column(
                modifier = Modifier
                    .size(iconSize.dp)
                    .padding(bottom = 1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom, )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF62AE29).copy(0.3f),
                                    Color(0xFF62AE29),
                                    Color(0xFF62AE29).copy(0.3f),
                                    Color.Transparent,
                                )
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun WalletWiseFloatingBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit, )
{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    //Max Width x 0.06Height
    Box(
        modifier = Modifier
            .height((screenHeight * 0.06).dp)
            .fillMaxWidth(fraction = 0.96f)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF9EE9D8).copy(alpha = 0.3f),
                        Color(0xFF91E9CF).copy(alpha = 0.3f),
                    )
                ),
                shape = CircleShape,
            )
            .clip(CircleShape),
        contentAlignment = Alignment.Center, )
    {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.0f))
                .border(
                    width = 1.dp,
                    color = Color(0xFF196B52).copy(0.5f),
                    shape = CircleShape
                )
                .padding(8.dp),
        )

        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically, )
        {
            //OVERVIEW: 1
            GlassButtonFloatingBottomBar(
                icon = Icons.Default.Menu,
                title = "Overview",
                contentDescription = "Overview",
                isSelected = selectedTab == 1,
                onClick = {
                    onTabSelected(1)
                }
            )
            //HOME: 0
            GlassButtonFloatingBottomBar(
                icon = Icons.Default.Home,
                title = "Home",
                contentDescription = "Home",
                isSelected = selectedTab == 0,
                onClick = {
                    onTabSelected(0)
                }
            )
            //SETTINGS: 2
            GlassButtonFloatingBottomBar(
                icon = Icons.Default.Settings,
                title = "Settings",
                contentDescription = "Settings",
                isSelected = selectedTab == 2,
                onClick = {
                    onTabSelected(2)
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp).fillMaxWidth())
}

