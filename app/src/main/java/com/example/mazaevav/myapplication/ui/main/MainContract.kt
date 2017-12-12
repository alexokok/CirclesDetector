package com.example.mazaevav.myapplication.ui.main

import com.example.mazaevav.myapplication.ui.common.BaseContract
import org.opencv.core.Mat

/**
 * @author Alexey Mazaev
 */
interface MainContract {
  interface View : BaseContract.View {}

  interface Presenter : BaseContract.Presenter<MainContract.View> {
    fun initPresenter()
    fun onCameraFrame(inputMat: Mat): Mat
  }
}
