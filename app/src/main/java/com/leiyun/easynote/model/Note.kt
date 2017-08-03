package com.leiyun.easynote.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * 类名：Note
 * 作者：Yun.Lei
 * 功能：
 * 创建日期：2017-08-03 10:58
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
@Entity
data class Note(
        @Id var id: Long = 0,
        var type: String? = null,
        var content: String? = null,
        var date: Date? = null
)