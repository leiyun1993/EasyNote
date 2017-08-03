package com.leiyun.easynote

import android.app.Application
import com.leiyun.easynote.model.MyObjectBox
import io.objectbox.BoxStore

/**
 * 类名：App
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-03 10:35
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class App : Application() {

    lateinit var boxStore: BoxStore
        private set

    val colorArr = intArrayOf(R.color.colorGold,
            R.color.colorSkyBlue,
            R.color.colorSpringGreen,
            R.color.colorCrimson,
            R.color.colorOrange)

    override fun onCreate() {
        super.onCreate()
        instance = this
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }

}