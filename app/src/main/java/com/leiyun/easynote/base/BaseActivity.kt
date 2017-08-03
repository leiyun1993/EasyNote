package com.leiyun.easynote.base

import android.app.Activity
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.toast


/**
 * 类名：BaseActivity
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-02 9:57
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

fun Activity.showTips(view: View?, msg: String) {
    if (view != null) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    } else {
        toast(msg)
    }
}

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        setContentView(getContentLayout())
        setStatusBar()
        initData()
    }

    abstract fun getContentLayout(): Int

    abstract fun initData()

    open fun setStatusBar() {
        StatusBarUtil.setColor(this, Color.WHITE, 50)
    }
}
