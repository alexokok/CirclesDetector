package com.example.mazaevav.myapplication.utils

import android.content.Context
import android.preference.PreferenceManager
import com.example.mazaevav.myapplication.model.ColorType


/**
 * @author Alexey Mazaev
 */
class PrefUtils(private val context: Context){

  companion object {
    const val PREF_COLOR = "pref_color"
    const val PREF_BLACK_WHITE = "pref_black_white"
  }

  fun getColorType(): Int {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    return sp.getInt(PREF_COLOR, 0)
  }

  fun setColorType(colorType: Int){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    sp.edit().putInt(PREF_COLOR, colorType).apply()
  }

  fun getBlackWhite(): Boolean {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    return sp.getBoolean(PREF_BLACK_WHITE, false)
  }

  fun setBlackWhite(isBlackWhite: Boolean) {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    sp.edit().putBoolean(PREF_BLACK_WHITE, isBlackWhite).apply()
  }

}