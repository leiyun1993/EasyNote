package com.leiyun.easynote.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 类名：SoftInputUtil
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-03 17:38
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class SoftInputUtil {
    companion object {
        fun showSoftInput(activity: Activity) {
            val softInputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            softInputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        fun hideSoftInput(activity: Activity, view: View) {
            val softInputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (softInputManager.isActive) {
                softInputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }
}