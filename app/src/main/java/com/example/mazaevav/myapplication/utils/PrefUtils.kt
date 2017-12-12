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
    const val PREF_HOUGH = "pref_hough"
  }

  fun getColorType(): ColorType {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)

    return when(sp.getInt(PREF_COLOR, 0)) {
      0 -> ColorType.RED
      /*1 -> ColorType.BLUE
      2 -> ColorType.GREEN*/
      else -> ColorType.RED
    }
  }

  fun setColorType(colorType: Int){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    sp.edit().putInt(PREF_COLOR, colorType).apply()
  }
}