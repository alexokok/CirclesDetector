package com.example.mazaevav.myapplication.model

import org.opencv.core.Scalar


/**
 * @author Alexey Mazaev
 */
enum class ColorType(val index: Int, val low1: Scalar, val high1: Scalar,
    low2: Scalar, high2: Scalar) {
  RED(0,
      low1 = Scalar(0.0, 150.0, 100.0),
      high1 = Scalar(10.0, 255.0, 255.0),
      low2 = Scalar(160.0, 150.0, 100.0),
      high2 = Scalar(179.0, 255.0, 255.0))
 /* GREEN(1, low = Scalar(), high = Scalar()),
  BLUE(2, low = Scalar(), high = Scalar())*/
}