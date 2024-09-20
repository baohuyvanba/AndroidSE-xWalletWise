package com.android.tyzen.xwalletwise.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun formatBalance(balance: Double): String
{
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.groupingSeparator = '.'
    symbols.decimalSeparator = ','

    val formatter = DecimalFormat("#,##0.##", symbols) // Use #.## to optionally display decimals

    if (balance.isInfinite() || balance.isNaN())
    {
        return "Invalid"
    }

    return formatter.format(balance)
}

fun formatDouble(input: String): Double {
    val symbols = DecimalFormatSymbols(Locale.getDefault()) // Or specify a locale if needed
    symbols.groupingSeparator = '.'
    symbols.decimalSeparator = ','
    val formatter = DecimalFormat("#,##0.##", symbols)

    return formatter.parse(input)?.toDouble() ?: 0.0        // Handle parsing errors gracefully
}