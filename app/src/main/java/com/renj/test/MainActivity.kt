package com.renj.test

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.renj.utils.common.Logger
import com.renj.utils.system.NotificationUtils
import com.renj.utils.system.SystemPhotoUtils
import com.renj.utils.system.SystemUtils
import java.io.File

internal class MainActivity : AppCompatActivity() {
    private var imageUrl: Uri? = null
    private lateinit var ivSrc: ImageView
    private lateinit var ivClipResult: ImageView

    companion object {
        const val TYPE_OPEN_PHOTOS = 0
        const val TYPE_CLIP_IMAGES = 1
    }

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

        ivSrc = findViewById(R.id.iv_src)
        ivClipResult = findViewById(R.id.iv_clip)


        findViewById<Button>(R.id.bt_open_photos).setOnClickListener {
            SystemPhotoUtils.openPhotos(this, TYPE_OPEN_PHOTOS)
        }

        findViewById<Button>(R.id.bt_clip_images).setOnClickListener {
            SystemPhotoUtils.cropImageUri(
                this,
                imageUrl,
                1,
                1,
                300,
                300,
                true,
                TYPE_CLIP_IMAGES
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TYPE_OPEN_PHOTOS) {
            var fileForPhotosReturn = SystemPhotoUtils.getFileForPhotosReturn(this, data)
            Logger.i("Selected Image Path: $fileForPhotosReturn")
            imageUrl = fileForPhotosReturn?.let {
                FileProvider.getUriForFile(
                    this,
                    "com.renj.test.fileprovider",
                    it
                )
            }
            ivSrc.setImageURI(imageUrl)
            Logger.i("Selected Image Uri: $imageUrl")
        } else if (requestCode == TYPE_CLIP_IMAGES) {
            var extras = data?.extras;
            if (extras != null) {
                var photo: Bitmap = extras!!.getParcelable("data")
                ivClipResult.setImageBitmap(photo)
                Logger.i("Clip Image Path: $photo")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
