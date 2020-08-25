package com.lipssoftware.wargag.utils

import android.content.Context

const val DARK_MODE_PREF = "DARK_MODE"

class Util(val context: Context) {

    val prefs = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE)

    companion object{
        val prefs_name = "APP_PREFS"
    }
}