package com.example.mazaevav.myapplication.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mazaevav.myapplication.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.selector
import src.main.R


/**
 * @author Alexey Mazaev
 */
class SettingsActivity : AppCompatActivity() {

  companion object {
    fun showSettings(activity: Activity) {
      val intent = Intent(activity, SettingsActivity::class.java)
      activity.startActivity(intent)
    }
  }

  private val prefUtils: PrefUtils = PrefUtils(this)
  private lateinit var colors: List<String>


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    colors = resources.getStringArray(R.array.colors).toList()

    initToolbar()
    setListeners()
    setColor()
  }

  private fun initToolbar(){
    setSupportActionBar(toolbar)

    supportActionBar?.title = getString(R.string.settings)
  }

  private fun setListeners(){
    color_title.setOnClickListener { showColorDialog() }
    color_value.setOnClickListener { showColorDialog() }
  }

  private fun setColor(){
    val colorType = prefUtils.getColorType()
    color_value.text = colors[colorType.index]
  }


  private fun showColorDialog() {
    val dialogTitle = getString(R.string.color_dialog_title)

    selector(dialogTitle, colors, { dialogInterface, i ->
      color_value.text = colors[i]
      prefUtils.setColorType(i)

      dialogInterface.dismiss()
    })
  }
}