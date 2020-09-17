package com.renj.test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.renj.utils.common.Logger
import com.renj.utils.res.BitmapUtils
import com.renj.utils.system.NotificationUtils
import com.renj.utils.system.SystemPhotoUtils

internal class MainActivity : AppCompatActivity() {
    private var imageUrl: Uri? = null
    private lateinit var ivSrc: ImageView
    private lateinit var ivClipResult: ImageView
    private lateinit var ivBlurSrv: ImageView
    private lateinit var ivBlurResult: ImageView
    private lateinit var ivGreyResult: ImageView

    companion object {
        const val TYPE_OPEN_PHOTOS = 0
        const val TYPE_CLIP_IMAGES = 1
        const val REQUEST_PERMISSIONS = 2
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions() // 申请权限
        showNotification() // 显示notification

        ivSrc = findViewById(R.id.iv_src)
        ivClipResult = findViewById(R.id.iv_clip)
        ivBlurSrv = findViewById(R.id.iv_blur_src)
        ivBlurResult = findViewById(R.id.iv_blur)
        ivGreyResult = findViewById(R.id.iv_grey)

        // 打开相册
        findViewById<Button>(R.id.bt_open_photos).setOnClickListener {
            SystemPhotoUtils.openPhotos(this, TYPE_OPEN_PHOTOS)
        }

        // 裁剪图片
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

        ivBlurSrv.setImageResource(R.mipmap.test)
        // 高斯模糊
        findViewById<Button>(R.id.bt_blur_images).setOnClickListener {
            var blurBitmap = BitmapUtils.blurBitmap(
                this,
                BitmapFactory.decodeResource(resources, R.mipmap.test),
                25
            )
            ivBlurResult.setImageBitmap(blurBitmap)
        }
        // 灰色图片
        findViewById<Button>(R.id.bt_grey_images).setOnClickListener {
            var blurBitmap = BitmapUtils.greyBitmap(
                BitmapFactory.decodeResource(resources, R.mipmap.test)
            )
            ivGreyResult.setImageBitmap(blurBitmap)
        }
    }

    private fun showNotification() {
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

    /**
     * 请求权限
     */
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_PERMISSIONS
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TYPE_OPEN_PHOTOS) {
            var fileForPhotosReturn = SystemPhotoUtils.getFileForPhotosReturn(this, data)
            Logger.i("Selected Image Path: $fileForPhotosReturn")
            imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileForPhotosReturn?.let {
                    FileProvider.getUriForFile(
                        this,
                        "com.renj.test.fileprovider",
                        it
                    )
                }
            } else {
                Uri.fromFile(fileForPhotosReturn)
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
