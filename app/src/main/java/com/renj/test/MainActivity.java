package com.renj.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.renj.common.utils.UIUtils;
import com.renj.common.weight.CircleImageView;
import com.renj.common.weight.dialog.CustomDialog;

class MainActivity extends AppCompatActivity {
    private Button showDialog;
    private CircleImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDialog = findViewById(R.id.bt_show_dialog);
        imageView = findViewById(R.id.imageView);


        imageView.setImageResource(R.mipmap.test);
        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        CustomDialog.newInstance(MainActivity.this)
                .isShowTitle(true)
                .setTitleContent("自定义标题")
                .setDialogContent("自定义对话框内容")
                .setCancelText("取消")
                .setConfirmText("确定")
                .setCanceledOnTouchOut(false)
                .setCustomDialogListener(new CustomDialog.CustomDialogListener() {
                    public void onCancel(CustomDialog dialog) {
                        UIUtils.showToastSafe("取消");
                    }

                    public void onConfirm(CustomDialog dialog) {
                        UIUtils.showToastSafe("确定");
                    }
                })
                .show();
    }
}
