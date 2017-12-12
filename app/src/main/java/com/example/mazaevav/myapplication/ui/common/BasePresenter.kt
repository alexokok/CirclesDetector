package com.example.mazaevav.myapplication.ui.common

/**
 * @author Alexey Mazaev
 */
open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
  var view: V? = null

  override fun attachView(view: V) {
    this.view = view
  }

  override fun detachView() {
    view = null
  }
}