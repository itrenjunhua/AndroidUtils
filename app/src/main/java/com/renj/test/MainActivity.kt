package com.renj.test

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.renj.utils.system.NotificationUtils

internal class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val channelInfo1 = NotificationUtils.ChannelInfo("1", "aValue")
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationUtils.getInstance().addChannel(channelInfo1)
        }
        NotificationUtils.getInstance().showNotification(
            this, 1, channelInfo1,
            R.mipmap.ic_launcher, 1, "aa",
            "title", "content", null
        )
    }
}
