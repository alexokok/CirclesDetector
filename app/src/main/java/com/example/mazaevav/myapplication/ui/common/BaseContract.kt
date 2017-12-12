package com.example.mazaevav.myapplication.ui.common

import android.content.Context

/**
 * @author Alexey Mazaev
 */
interface BaseContract {

  interface View {
    fun getContext(): Context?
  }

  interface Presenter<in V : View> {
    fun attachView(view: V)

    fun detachView()
  }
}