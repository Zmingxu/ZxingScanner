package com.maurice.zxingscanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.maurice.qrcode.QRCodeUtil;

/**
 * Created by Maurice on 2017/8/9.
 */

public class ScannerResultActivity extends AppCompatActivity {
    private ImageView ivCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_result_activity);
        String msg = getIntent().getStringExtra("msg");
        ivCode = (ImageView) findViewById(R.id.iv_code);
        Bitmap qRcodeBitmap = QRCodeUtil.getQRcodeBitmap(msg,500,500, 0);
        ivCode.setImageBitmap(qRcodeBitmap);
    }
}
