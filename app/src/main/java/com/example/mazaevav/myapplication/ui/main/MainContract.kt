package com.example.mazaevav.myapplication.ui.main

import org.opencv.core.Mat

/**
 * @author Alexey Mazaev
 */
interface MainContract {
  interface View {

  }

  interface Presenter {
    fun onCameraFrame(inputMat: Mat): Mat
  }
}