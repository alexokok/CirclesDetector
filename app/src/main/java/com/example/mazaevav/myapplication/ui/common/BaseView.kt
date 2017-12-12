package com.example.mazaevav.myapplication.ui.common

import android.content.Context

/**
 * @author Alexey Mazaev
 */
open class BaseView: BaseContract.View{
  override fun getContext(): Context? = null
}