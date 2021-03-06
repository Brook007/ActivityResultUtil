package com.brook.app.android.activityresultutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brook.app.android.activityresult.ActivityResultUtil;

public class MainActivity extends Activity {

    int time = 0;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = 0;

        start = findViewById(R.id.start);

        Log.d("Brook", "Main===" + this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClick(View view) {

        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");

        //        startActivityForResult(intentToPickPic, 10000);
        ActivityResultUtil.with(this)
                .requestCode(10000)
                .startActivityForResult(intentToPickPic, new ActivityResultUtil.Callback() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        Log.d("Brook", "onActivityResult" + data);
                        start.setText("点击选图" + data);
                    }
                });
    }

    public void onClock(View view) {
        start.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start.setText("开始选图" + time++);
                Handler handler = start.getHandler();
                if (handler != null) {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    public void appcompat(View view) {
        startActivity(new Intent(this, MainSupportActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        start.setText("点击选图" + this + "===" + data);
    }
}
