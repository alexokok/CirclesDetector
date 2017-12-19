package com.example.mazaevav.myapplication.model

import org.opencv.core.Scalar


/**
 * @author Alexey Mazaev
 */
enum class ColorType(val low1: Scalar, val high1: Scalar,
    val low2: Scalar, val high2: Scalar) {
  RED(low1 = Scalar(0.0, 150.0, 100.0),
      high1 = Scalar(10.0, 255.0, 255.0),
      low2 = Scalar(160.0, 150.0, 100.0),
      high2 = Scalar(179.0, 255.0, 255.0)),
  GREEN(low1 = Scalar(45.0, 100.0, 59.0),
      high1 = Scalar(75.0, 255.0, 255.0),
      low2 = Scalar(45.0, 100.0, 59.0),
      high2 = Scalar(75.0, 255.0, 255.0)),
  BLUE(      low1 = Scalar(45.0, 100.0, 59.0),
      high1 = Scalar(75.0, 255.0, 255.0),
      low2 = Scalar(45.0, 100.0, 59.0),
      high2 = Scalar(75.0, 255.0, 255.0))
}