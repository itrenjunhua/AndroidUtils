package com.renj.test

import android.app.Application
import com.renj.utils.AndroidUtils
import com.renj.utils.check.ANRWatchDog

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
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
        AndroidUtils.init(this)
        // debug模式下增加anr监测
        if (AndroidUtils.isDebug())
            ANRWatchDog.init()
    }
}
