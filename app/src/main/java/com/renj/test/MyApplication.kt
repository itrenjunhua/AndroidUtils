package com.renj.test

import android.app.Application
import com.renj.common.CommonUtils

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-11-14   19:08
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonUtils.init(this)
    }
}
