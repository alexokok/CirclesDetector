package com.example.mazaevav.myapplication.ui.main

import com.example.mazaevav.myapplication.model.ColorType
import com.example.mazaevav.myapplication.ui.common.BasePresenter
import com.example.mazaevav.myapplication.utils.PrefUtils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * @author Alexey Mazaev
 */
class MainPresenter: BasePresenter<MainContract.View>(), MainContract.Presenter {
  private lateinit var prefUtils: PrefUtils

  override fun onCameraFrame(inputMat: Mat): Mat {
    val color = ColorType.values()[0]//prefUtils.getColorType()]
    val hsvImage = Mat()
    val lowerRedHueRange = Mat()
    val upperRedHueRange = Mat()

    val redHueImage = Mat()
    val circles = Mat()

    Imgproc.medianBlur(inputMat, inputMat, 3)

    Imgproc.cvtColor(inputMat, hsvImage, Imgproc.COLOR_RGB2HSV)

    Core.inRange(hsvImage, color.low1, color.high1, lowerRedHueRange)
    Core.inRange(hsvImage, color.low2, color.high2, upperRedHueRange)

    Core.addWeighted(lowerRedHueRange, 1.0, upperRedHueRange, 1.0, 0.0, redHueImage)
    Imgproc.GaussianBlur(redHueImage, redHueImage, Size(9.0, 9.0), 2.0, 2.0)

    Imgproc.HoughCircles(redHueImage, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0,
        redHueImage.rows() / 8.toDouble(), 100.0, 20.0, 0, 0)

    if (circles.cols() > 0) {
      for (x in 0 until Math.min(circles.cols(), 5)) {
        val circleVec = circles.get(0, x) ?: break
        val center = Point(circleVec[0].toInt().toDouble(), circleVec[1].toInt().toDouble())
        val radius = circleVec[2].toInt()

        Imgproc.circle(inputMat, center, 3, Scalar(0.0, 255.0, 0.0), 5)
        Imgproc.circle(inputMat, center, radius, Scalar(0.0, 255.0, 0.0), 2)
      }
    }

    return inputMat
  }

  override fun initPresenter() {
    view?.getContext()?.let { prefUtils = PrefUtils(it) }
  }
}