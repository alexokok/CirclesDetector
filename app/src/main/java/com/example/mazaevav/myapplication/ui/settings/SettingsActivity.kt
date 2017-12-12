package com.example.mazaevav.myapplication.ui.settings

import android.app.Activity
import android.content.Context
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
class SettingsActivity : AppCompatActivity(), SettingsContract.View {
  companion object {
    fun showSettings(activity: Activity) {
      val intent = Intent(activity, SettingsActivity::class.java)
      activity.startActivity(intent)
    }
  }

  private lateinit var colors: List<String>

  private lateinit var presenter: SettingsPresenter

  override fun getContext(): Context? = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    presenter = SettingsPresenter()
    presenter.attachView(this)
    presenter.initPresenter()

    colors = resources.getStringArray(R.array.colors).toList()

    initToolbar()
    setListeners()
  }

  private fun initToolbar(){
    setSupportActionBar(toolbar)

    supportActionBar?.title = getString(R.string.settings)
  }

  private fun setListeners(){
    color_title.setOnClickListener { showColorDialog() }
    color_value.setOnClickListener { showColorDialog() }

    is_black_white.setOnCheckedChangeListener { _, b ->
      presenter.onBlackWhiteClick(b)
    }
  }

  override fun setColor(index: Int){
    color_value.text = colors[index]
  }

  override fun setBlackWhite(isBlackWhite: Boolean) {
    is_black_white.isChecked = isBlackWhite
  }


  private fun showColorDialog() {
    val dialogTitle = getString(R.string.color_dialog_title)

    selector(dialogTitle, colors, { dialogInterface, i ->
      color_value.text = colors[i]
      presenter.onColorClick(i)
      dialogInterface.dismiss()
    })
  }
}