package com.example.mazaevav.myapplication.ui

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.*

import org.opencv.core.Mat
import src.main.R

class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

  companion object {
    val TAG = "src"

    init {
      if (! OpenCVLoader.initDebug()) {
        Log.wtf(TAG, "OpenCV failed to load!")
      }
    }

    fun navigateToMainActivity(activity: Activity){
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
          cameraView !!.enableView()
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
    cameraView !!.setCvCameraViewListener(this)
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId){
      R.id.settings -> { callSettings() }
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
      cameraView !!.disableView()
  }

  override fun onCameraViewStarted(width: Int, height: Int) {

  }

  override fun onCameraViewStopped() {

  }

  override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
    /*val input = inputFrame.gray()
    val circles = Mat()

    Imgproc.blur(input, input, Size(7.0, 7.0), Point(2.0, 2.0))
    Imgproc.HoughCircles(input, circles, Imgproc.CV_HOUGH_GRADIENT, 2.0, 100.0, 100.0, 90.0, 0,
        1000)

    Log.i(TAG, ("size: " + circles.cols()).toString() + ", " + circles.rows().toString())

    if (circles.cols() > 0) {
      for (x in 0 until Math.min(circles.cols(), 5)) {
        val circleVec = circles.get(0, x) ?: break

        val center = Point(circleVec[0].toInt().toDouble(), circleVec[1].toInt().toDouble())
        val radius = circleVec[2].toInt()

        Imgproc.circle(input, center, 3, Scalar(255.0, 255.0, 255.0), 5)
        Imgproc.circle(input, center, radius, Scalar(255.0, 255.0, 255.0), 2)
      }
    }

    circles.release()
    input.release()*/
    return inputFrame.rgba()
  }

  private fun callSettings(){
    SettingsActivity.showSettings(this)
  }

  private fun initToolbar(){
    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.app_name)
  }
}