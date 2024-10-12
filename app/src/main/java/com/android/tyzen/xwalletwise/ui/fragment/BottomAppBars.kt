package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.android.tyzen.xwalletwise.R

@Composable
@Preview(showBackground = true)
fun PreviewFloatingBottomBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WalletWiseFloatingBottomBar(
            selectedTab = 0,
            onTabSelected = { /*TODO*/ },
            onAddTransactionPress = { /*TODO*/ },
        )
    }
}

/**
 * MAIN Floating Bottom Bar ========================================================================
 */
@Composable
fun HomeButtonFloatingBottomBar(
    contentSize: List<Double>,
    isSelected: Boolean,
    onClick: () -> Unit,)
{
    val backgroundColor =
        if (!isSelected) MaterialTheme.colorScheme.primary.copy(
            red = MaterialTheme.colorScheme.primary.red * 0.8f,
            green = MaterialTheme.colorScheme.primary.green * 0.8f,
            blue = MaterialTheme.colorScheme.primary.blue * 0.8f
            )
        else
            MaterialTheme.colorScheme.primary.copy(
                red = MaterialTheme.colorScheme.primary.red * 1.2f,
                green = MaterialTheme.colorScheme.primary.green * 1.2f,
                blue = MaterialTheme.colorScheme.primary.blue * 1.2f
            )
    val iconColor = Color.White
    val textColor = Color.White

    IconButton(
        modifier = Modifier
            .width((contentSize[0] * 0.4).dp)
            .height(contentSize[1].dp)
            .padding(start = (contentSize[0] * 0.05).dp),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(
                    horizontal = (contentSize[0] * 0.05).dp,
                    vertical = (contentSize[1] * 0.14).dp,
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_bars_home),
                modifier = Modifier.size((contentSize[0]*0.07).dp),
                contentDescription = "Home Screen",
                tint = iconColor,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text  = "Home",
                color = textColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
fun ButtonFloatingBottomBar(
    icon: ImageVector,
    contentDescription: String,
    iconSize: Double,
    isSelected: Boolean,
    onClick: () -> Unit, )
{
    val iconColor = Color.White
    val shadowElevation by animateFloatAsState(targetValue = if (isSelected) 15f else 0f, label = "floating_icon_shadow")
    val backgroundColor =
        if (!isSelected)
            Color.Transparent
        else
            MaterialTheme.colorScheme.primary.copy(
                red = MaterialTheme.colorScheme.primary.red * 1.2f,
                green = MaterialTheme.colorScheme.primary.green * 1.2f,
                blue = MaterialTheme.colorScheme.primary.blue * 1.2f
            )

    IconButton(
        modifier = Modifier.size(iconSize.dp),
        onClick = onClick, )
    {
        Box(
            modifier = Modifier
                .size(iconSize.times(0.6).dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(16.dp),
                )
                .shadow(
                    elevation = shadowElevation.dp,
                    shape = CircleShape,
                    clip = true,
                    ambientColor = Color.Transparent,
                    spotColor = Color.White,
                ),
            contentAlignment = Alignment.Center, )
        {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier
                    .size(iconSize.times(0.35).dp)
            )
        }
    }
}

@Composable
fun WalletWiseFloatingBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    //Add Shortcut
    onAddTransactionPress: (Int) -> Unit,)
{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth  = configuration.screenWidthDp
    val isMenuExpanded = remember { mutableStateOf(false) }

    //0.9 Width x 0.06 Height
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width((screenWidth * 0.92).dp)
            .height((screenHeight * 0.07).dp),
        horizontalArrangement = Arrangement.SpaceAround, )
    {
        //NAVIGATION MENU --------------------------------------------------------------------------
        Row(
            modifier = Modifier
                .width((screenWidth * 0.75).dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
            horizontalArrangement = Arrangement.Start,
        ) {
            HomeButtonFloatingBottomBar(
                contentSize = listOf(screenWidth*0.75, screenHeight*0.07),
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )

            Row(
                modifier = Modifier
                    .width((screenWidth * 0.5).dp)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //OVERVIEW: 1
                ButtonFloatingBottomBar(
                    icon = ImageVector.vectorResource(R.drawable.ic_bars_analysis),
                    contentDescription = "Overview",
                    iconSize = screenWidth*0.2,
                    isSelected = selectedTab == 1,
                    onClick = {
                        onTabSelected(1)
                    }
                )

                //SETTINGS: 2
                ButtonFloatingBottomBar(
                    icon = ImageVector.vectorResource(R.drawable.ic_bars_setting),
                    contentDescription = "Settings",
                    iconSize = screenWidth*0.2,
                    isSelected = selectedTab == 2,
                    onClick = {
                        onTabSelected(2)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width((screenWidth*0.02).dp))

        //ADD TRANSACTION SHORTCUT -----------------------------------------------------------------
        Box(
            modifier = Modifier
                .width((screenWidth * 0.15).dp)
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .clickable { isMenuExpanded.value = true },  // Show menu on click
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Transaction",
                tint = Color.White
            )

            DropdownMenu(
                expanded = isMenuExpanded.value,
                onDismissRequest = { isMenuExpanded.value = false },
                offset = DpOffset(0.dp, 0.dp),
                properties = PopupProperties(focusable = true),
                containerColor = Color.Transparent,
                shadowElevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.Transparent)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    FloatingActionButton(
                        onClick = { onAddTransactionPress.invoke(-3) },
                        containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF196B52).copy(0.5f),
                                shape = CircleShape,
                            ),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_transaction_text),
                            tint = Color(0xFF196B52).copy(0.5f),
                            contentDescription = "", )
                    }

                    FloatingActionButton(
                        onClick = { onAddTransactionPress.invoke(-2) },
                        containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF196B52).copy(0.5f),
                                shape = CircleShape,
                            ),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_receipt_scan),
                            tint = Color(0xFF196B52).copy(0.5f),
                            contentDescription = "", )
                    }

                    FloatingActionButton(
                        onClick = { onAddTransactionPress.invoke(-1) },
                        containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF196B52).copy(0.5f),
                                shape = CircleShape,
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            tint = Color(0xFF196B52).copy(0.5f),
                            contentDescription = "", )
                    }
                }
            }
        }
    }
}