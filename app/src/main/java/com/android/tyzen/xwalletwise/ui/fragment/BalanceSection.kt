package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.android.tyzen.xwalletwise.util.formatBalance

@Preview(showBackground = true)
@Composable
fun PreviewGlassmorphicBox() {
    WalletWiseTheme {
        GlassmorphicBox()
    }
}

@Composable
fun GlassmorphicBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF6E8EFB), Color(0xFFA777E3))
                )
            ),
        contentAlignment = Alignment.Center, )
    {
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Glassmorphism", fontSize = 24.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("This is a glassmorphic box.", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
/**
 * BALANCE SECTION =================================================================================
 */
@Preview(showBackground = true)
@Composable
fun PreviewBalanceSection() {
    WalletWiseTheme {
        BalanceSection(
            title = "Remaining Budget",
            balance = 1000000.0,
            currency = "VND",
            showTitle = true,
            showCurrencyBackground = true,
            currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun BalanceSection(
    title: String,
    showTitle: Boolean = true,
    balance: Double,
    balanceColor: Color = Color.White,
    currency: String,
    showCurrencyBackground: Boolean = true,
    currencyBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
    paddingValues: Dp = 16.dp, )
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues, bottom = paddingValues)
            .wrapContentHeight(Alignment.CenterVertically)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center, )
    {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally, )
        {
            if (showTitle) {
                Text(
                    text = title,
                    modifier = Modifier
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color.Black.copy(alpha = 0.2f),
                                start = androidx.compose.ui.geometry.Offset(0f, y),
                                end = androidx.compose.ui.geometry.Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(2.dp),
                    style = MaterialTheme.typography.titleSmall, )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically, )
            {
                if (showCurrencyBackground)
                {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(
                                color = currencyBackgroundColor,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .padding(2.dp)
                            .padding(start = 3.dp)
                            .padding(end = 3.dp), )
                    {
                        Text(
                            text = currency,
                            style = MaterialTheme.typography.titleSmall,
                            color = balanceColor, )
                    }
                }
                else
                {
                    Text(
                        text = currency,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp) )
                }

                Text(
                    text = formatBalance(balance),
                    modifier = Modifier.padding(top = 0.dp),
                    style = MaterialTheme.typography.displayLarge,
                    color = balanceColor, )
            }
        }
    }
}

/**
 * BALANCE BOX =====================================================================================
 */
@Preview(showBackground = true)
@Composable
fun PreviewBalanceBox() {
    WalletWiseTheme {
        BalanceBox(
            amount = 1000000.0,
            label = "Income",
            icon = Icons.Default.KeyboardArrowDown,
            gradientColors = listOf(Color(0xFF6E8EFB), Color(0xFFA777E3)),
            textColor = Color.DarkGray,
            onClick = {})
    }
}

@Composable
fun BalanceBox(
    amount: Double,
    label: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier, )
{
    Box(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(onClick = onClick), )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth().wrapContentHeight()
                .background(Color.White.copy(alpha = 0.1f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center, )
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = Color.White, )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = textColor, )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatBalance(amount),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally), )
            }
        }
    }
}

/**
 * DETAILED SECTION ================================================================================
 */
@Preview(showBackground = true)
@Composable
fun PreviewDetailedBalanceSection() {
    WalletWiseTheme {
        DetailedBalanceSection(
            incomeAmount = 10000000.0,
            outcomeAmount = 500000.0,
            onIncomeClick = {},
            onOutcomeClick = {}, )
    }
}

@Composable
fun DetailedBalanceSection(
    incomeAmount: Double,
    outcomeAmount: Double,
    onIncomeClick: () -> Unit = {},
    onOutcomeClick: () -> Unit = {}, )
{
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround, )
    {
        //Income Box
        BalanceBox(
            amount = incomeAmount,
            label = "Income",
            icon = Icons.Default.KeyboardArrowDown,
            gradientColors = listOf(
                Color(0xFF93F9B9).copy(alpha = 0.3f),
                Color(0xFF1D976C).copy(alpha = 0.3f),
            ),
            textColor = Color.White,
            modifier = Modifier.weight(1f),
            onClick = onIncomeClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        //Outcome Box
        BalanceBox(
            amount = outcomeAmount,
            label = "Expense",
            icon = Icons.Default.KeyboardArrowUp,
            gradientColors = listOf(
                Color(0xFF93F9B9).copy(alpha = 0.3f),
                Color(0xFF1D976C).copy(alpha = 0.3f),
            ),
            textColor = Color.White,
            modifier = Modifier.weight(1f),
            onClick = onOutcomeClick
        )
    }
}
