package com.finance.android.walletwise.ui.fragment

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

/**
 * ANIMATED TEXT -----------------------------------------------------------------------------------
 */
@Preview(showBackground = true)
@Composable
fun PreviewAnimatedGradientText(){
    WalletWiseTheme {
        AnimatedGradientText(
            text = "Hello, World!",
            align = TextAlign.Center,
            gradient = listOf(Color.Red, Color.Blue)
        )
    }
}

@Composable
fun AnimatedGradientText(
    text: String = "",
    align: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
    gradient: List<Color>,
    style: TextStyle = MaterialTheme.typography.headlineMedium, )
{
    //Animation configuration
    var offset by remember { mutableStateOf(0f) }
    val transition = rememberInfiniteTransition()
    val animatedOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    //Update offset for animation
    LaunchedEffect(key1 = animatedOffset) {
        offset = animatedOffset
    }

    //Gradient brush
    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return LinearGradientShader(
                    colors = gradient,
                    from = Offset(size.width * offset, 0f),
                    to = Offset(size.width * offset + size.width, 0f),
                    tileMode = TileMode.Mirror
                )
            }
        }
    }

    //Text with gradient brush
    Text(
        text = text,
        modifier = modifier,
        style = style.copy(brush = brush),
        textAlign = align,
    )
}

/**
 * AUTO SCALED TEXT (!!!) --------------------------------------------------------------------------
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AutoScaledTextNormal(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary
    )
)
{
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(), )
    {
        val density = LocalDensity.current.density // Access density
        var fontSize by remember { mutableStateOf(style.fontSize) }

        Text(
            text = text,
            style = style.copy(fontSize = fontSize),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    val maxWidthPx = constraints.maxWidth / density //Convert maxWidth to pixels
                    val textWidthPx = layoutCoordinates.size.width  //Width of the text in pixels

                    if (textWidthPx > maxWidthPx) {
                        fontSize = (fontSize*0.9)                   //Decrease the font size
                    }
                }
        )
    }
}

