package com.maurice.zxingscanner;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.maurice.qrcode.QRCodeScanner;
import com.maurice.qrcode.Result;
import com.maurice.qrcode.ScanActivityHandler;
import com.maurice.qrcode.camera.CameraManager;

import java.io.IOException;

/**
 * Created by Maurice on 2017/8/8.
 */

public class ScannerActivity extends AppCompatActivity implements SurfaceHolder.Callback, QRCodeScanner {
    CameraManager mCameraManager;
    SurfaceView scannerSurfaceView;
    private SurfaceHolder mHolder;
    private ScanActivityHandler mHandler;
    private boolean hasSurface;
    private ScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerSurfaceView = (SurfaceView) findViewById(R.id.scanner_surface_view);
        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        intData();
    }

    private void intData() {
        mCameraManager = new CameraManager(getApplication());
        mHolder = scannerSurfaceView.getHolder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler == null) {
            //得到扫描宽高进行绘制
            scannerView.setCameraManager(mCameraManager);
            if (hasSurface) {
                initCamera(mHolder);
            } else {
                mHolder.addCallback(this);
            }
            scannerView.startDraw();
        }
    }

    @Override
    protected void onPause() {
        if (mHandler != null) {
            mHandler.quitSynchronously();
            mHandler = null;
        }
        mCameraManager.closeDriver();
        if (!hasSurface) {
            mHolder.removeCallback(this);
        }
        super.onPause();
    }

    private void initCamera(SurfaceHolder mHolder) {
        if (mCameraManager.isOpen()) {
            return;
        }

        try {
            mCameraManager.openDriver(mHolder);
            if (mHandler == null) {
                mHandler = new ScanActivityHandler(this, null, "utf-8", mCameraManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public CameraManager getCameraManager() {
        return mCameraManager;
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handlerDecode(Result rawResult) {
        if (rawResult == null) {
            Log.d("Scanner", "识别失败");
            return;
        }
        String str = rawResult.getText();
        Log.d("Scanner", str);
    }
}
