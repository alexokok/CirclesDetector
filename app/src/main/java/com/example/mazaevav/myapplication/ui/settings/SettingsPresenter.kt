package com.example.mazaevav.myapplication.ui.settings

import com.example.mazaevav.myapplication.ui.common.BasePresenter
import com.example.mazaevav.myapplication.utils.PrefUtils

/**
 * @author Alexey Mazaev
 */
class SettingsPresenter: BasePresenter<SettingsContract.View>(), SettingsContract.Presenter {

  private lateinit var prefUtils: PrefUtils

  override fun initPresenter() {
    view?.getContext()?.let { prefUtils = PrefUtils(it) }
  }

  override fun getColor() {
    view?.setColor(prefUtils.getColorType())
  }

  override fun getBlackWhite() {
    view?.setBlackWhite(prefUtils.getBlackWhite())
  }

  override fun onColorClick(colorIndex: Int) {
    prefUtils.setColorType(colorIndex)
    view?.setColor(colorIndex)
  }

  override fun onBlackWhiteClick(isBlackWhite: Boolean) {
    prefUtils.setBlackWhite(isBlackWhite)
  }
}