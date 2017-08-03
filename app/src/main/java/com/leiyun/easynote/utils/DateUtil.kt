package com.leiyun.easynote.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 类名：DateUtil
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-02 17:45
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
class DateUtil {
    companion object {

        fun getDate(): Date = Calendar.getInstance().time

        fun getNowTimeFriendly(): String {
            val calendar: Calendar = Calendar.getInstance()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val friendlyHint = getFriendlyHint(hourOfDay)
            val format1 = SimpleDateFormat("yyyy年MM月dd日 ", Locale.CHINA)
            val format2 = SimpleDateFormat("H:mm", Locale.CHINA)
            return format1.format(calendar.time) + friendlyHint + format2.format(calendar.time)
        }

        fun getFriendlyTimeOfDate(date: Date): String {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val friendlyHint = getFriendlyHint(hourOfDay)
            val format1 = SimpleDateFormat("yyyy年MM月dd日 ", Locale.CHINA)
            val format2 = SimpleDateFormat("H:mm", Locale.CHINA)
            return format1.format(calendar.time) + friendlyHint + format2.format(calendar.time)
        }

        fun getFriendlyHint(hourOfDay: Int): String = when (hourOfDay) {
            in 7..8 -> "清晨"
            in 9..11 -> "上午"
            in 12..13 -> "中午"
            in 14..16 -> "下午"
            in 17..19 -> "傍晚"
            in 19..21 -> "晚上"
            else -> {
                "深夜"
            }
        }

        fun getDateFormat(format: String): DateFormat = SimpleDateFormat(format, Locale.CHINA)
    }
}