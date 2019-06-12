package com.brook.app.android.activityresultutil;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brook.app.android.activityresult.ActivityResultUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");

        ActivityResultUtil.with(this).requestCode(10000).startActivityForResult(intentToPickPic, new ActivityResultUtil.Callback() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {

            }
        });
    }
}
