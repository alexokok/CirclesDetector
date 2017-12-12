package com.example.mazaevav.myapplication.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.*
import org.opencv.core.Mat
import src.main.R
import org.opencv.android.CameraBridgeViewBase
import com.example.mazaevav.myapplication.ui.settings.SettingsActivity


class MainActivity : AppCompatActivity(), MainContract.View,
    CameraBridgeViewBase.CvCameraViewListener2 {

  companion object {
    val TAG = "src"

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
  private lateinit var presenter: MainContract.Presenter


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

  override fun getContext(): Context? = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    presenter = MainPresenter()
    presenter.attachView(this)
    presenter.initPresenter()

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
    return presenter.onCameraFrame(inputFrame.rgba())
  }

  private fun callSettings() {
    SettingsActivity.showSettings(this)
  }

  private fun initToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.app_name)
  }
}