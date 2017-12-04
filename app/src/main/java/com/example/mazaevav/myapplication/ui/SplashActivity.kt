package com.example.mazaevav.myapplication.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import src.main.R


/**
 * @author Alexey Mazaev
 */
class SplashActivity : AppCompatActivity() {

  companion object {
    private const val MY_PERMISSIONS_REQUEST_CAMERA = 1111
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkPermissions()
  }

  private fun checkPermissions() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
        PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
          Manifest.permission.CAMERA)) {
        showSecondaryPermissionsSnack()
      } else {
        showPermissionsRequest()
      }
    } else {
      navigateToMainScreen()
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
      grantResults: IntArray) {
    if(requestCode == MY_PERMISSIONS_REQUEST_CAMERA && grantResults.isNotEmpty()) {
      if(grantResults[0] > 0) {
        navigateToMainScreen()
      } else {
        showSecondaryPermissionsSnack()
      }
    }
  }

  private fun navigateToMainScreen(){
    MainActivity.navigateToMainActivity(this)
    finish()
  }

  private fun showPermissionsRequest(){
    ActivityCompat
        .requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
            MY_PERMISSIONS_REQUEST_CAMERA)
  }

  private fun showSecondaryPermissionsSnack() {
    Snackbar.make(findViewById(android.R.id.content),
        R.string.permission_dialog,
        Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.action_accept) { showPermissionsRequest() }
        .show()
  }
}