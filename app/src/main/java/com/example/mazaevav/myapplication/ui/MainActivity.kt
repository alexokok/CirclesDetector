package com.example.mazaevav.myapplication.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.*
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import src.main.R
import org.opencv.core.Core
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Core.addWeighted
import org.opencv.core.Core.inRange
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc.COLOR_BGR2HSV
import org.opencv.imgproc.Imgproc.COLOR_RGB2HSV
import org.opencv.imgproc.Imgproc.CV_HOUGH_GRADIENT
import org.opencv.imgproc.Imgproc.GaussianBlur
import org.opencv.imgproc.Imgproc.HoughCircles
import org.opencv.imgproc.Imgproc.cvtColor
import android.R.attr.x


class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

  companion object {
    val TAG = "src"
    val VIEW_MODE_CANNY = 1

    init {
      if (!OpenCVLoader.initDebug()) {
        Log.wtf(TAG, "OpenCV failed to load!")
      }
    }

    fun navigateToMainActivity(activity: Activity) {
      val intent = Intent(activity, MainActivity::class.java)

      activity.startActivity(intent)
    }
  }

  private var cameraView: JavaCameraView? = null


  private val loaderCallback = object : BaseLoaderCallback(this) {
    override fun onManagerConnected(status: Int) {
      when (status) {
        LoaderCallbackInterface.SUCCESS -> {
          Log.i(TAG, "OpenCV loaded successfully")
          cameraView!!.enableView()
        }
        else -> super.onManagerConnected(status)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initToolbar()

    cameraView = findViewById(R.id.cameraview)
    cameraView!!.setCvCameraViewListener(this)
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.settings -> {
        callSettings()
      }
      else -> super.onOptionsItemSelected(item)
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, loaderCallback)
  }

  override fun onPause() {
    super.onPause()
    if (cameraView != null)
      cameraView!!.disableView()
  }

  override fun onCameraViewStarted(width: Int, height: Int) {}

  override fun onCameraViewStopped() {}

  override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
    val hsvImage = Mat()
    val rgbImage = inputFrame.rgba()
    val lowerRedHueRange = Mat()
    val upperRedHueRange = Mat()

    val redHueImage = Mat()
    val circles = Mat()

    cvtColor(rgbImage, hsvImage, COLOR_RGB2HSV)

    inRange(hsvImage, Scalar(0.0, 100.0, 100.0), Scalar(10.0, 255.0, 255.0),
        lowerRedHueRange)
    inRange(hsvImage, Scalar(160.0, 100.0, 100.0), Scalar(179.0, 255.0, 255.0),
        upperRedHueRange)

    addWeighted(lowerRedHueRange, 1.0, upperRedHueRange, 1.0, 0.0, redHueImage)
    GaussianBlur(redHueImage, redHueImage, Size(9.0, 9.0), 2.0, 2.0)

    HoughCircles(redHueImage, circles, CV_HOUGH_GRADIENT, 1.0,
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

  private fun callSettings() {
    SettingsActivity.showSettings(this)
  }

  private fun initToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.app_name)
  }
}