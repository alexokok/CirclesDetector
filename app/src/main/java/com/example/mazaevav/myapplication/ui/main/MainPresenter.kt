package com.example.mazaevav.myapplication.ui.main

import android.content.Context
import com.example.mazaevav.myapplication.model.ColorType
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
class MainPresenter(context: Context): MainContract.Presenter {

  private val prefUtils: PrefUtils = PrefUtils(context)

  override fun onCameraFrame(inputMat: Mat): Mat {
    val color = ColorType.values()[prefUtils.getColorType().index]
    val hsvImage = Mat()
    val rgbImage = inputMat
    val lowerRedHueRange = Mat()
    val upperRedHueRange = Mat()

    val redHueImage = Mat()
    val circles = Mat()

    Imgproc.medianBlur(rgbImage, rgbImage, 3)

    Imgproc.cvtColor(rgbImage, hsvImage, Imgproc.COLOR_RGB2HSV)

    Core.inRange(hsvImage, Scalar(0.0, 150.0, 100.0), Scalar(10.0, 255.0, 255.0),
        lowerRedHueRange)
    Core.inRange(hsvImage, Scalar(160.0, 150.0, 100.0), Scalar(179.0, 255.0, 255.0),
        upperRedHueRange)

    Core.addWeighted(lowerRedHueRange, 1.0, upperRedHueRange, 1.0, 0.0, redHueImage)
    Imgproc.GaussianBlur(redHueImage, redHueImage, Size(9.0, 9.0), 2.0, 2.0)

    Imgproc.HoughCircles(redHueImage, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0,
        redHueImage.rows() / 8.toDouble(), 100.0, 20.0, 0, 0)

    if (circles.cols() > 0) {
      for (x in 0 until Math.min(circles.cols(), 5)) {
        val circleVec = circles.get(0, x) ?: break
        val center = Point(circleVec[0].toInt().toDouble(), circleVec[1].toInt().toDouble())
        val radius = circleVec[2].toInt()

        Imgproc.circle(rgbImage, center, 3, Scalar(0.0, 255.0, 0.0), 5)
        Imgproc.circle(rgbImage, center, radius, Scalar(0.0, 255.0, 0.0), 2)
      }
    }

    return rgbImage
  }
}