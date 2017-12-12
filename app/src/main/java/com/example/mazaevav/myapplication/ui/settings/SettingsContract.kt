package com.example.mazaevav.myapplication.ui.settings

import com.example.mazaevav.myapplication.ui.common.BaseContract

/**
 * @author Alexey Mazaev
 */
interface SettingsContract{
  interface View: BaseContract.View {
    fun setColor(index: Int)
    fun setBlackWhite(isBlackWhite: Boolean)
  }

  interface Presenter: BaseContract.Presenter<SettingsContract.View> {
    fun initPresenter()

    fun getColor()
    fun getBlackWhite()

    fun onColorClick(colorIndex: Int)
    fun onBlackWhiteClick(isBlackWhite: Boolean)
  }
}