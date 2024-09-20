 package com.android.tyzen.xwalletwise.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(datetime: Date): String {
    val formatter = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
    return formatter.format(datetime)
}