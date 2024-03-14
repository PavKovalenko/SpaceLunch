package com.stein.spacelunch.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toUpcomingString(): String {
    val sdf = SimpleDateFormat("MMMM dd - hh:mm z", Locale.getDefault())

    return sdf.format(this)

}