package com.maurice.zxingscanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.maurice.qrcode.camera.CameraManager;

/**
 * Created by Maurice on 2017/8/9.
 */

public class ScannerView extends View {
    CameraManager cameraManager;
    private Paint mOutFramePaint;

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOutFramePaint = new Paint();
        mOutFramePaint.setAntiAlias(true);
        mOutFramePaint.setColor(Color.BLUE);
        mOutFramePaint.setStyle(Paint.Style.STROKE);
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return;
        }
        Rect frame = cameraManager.getFramingRect();
        if (frame == null) {
            return;
        }
        Rect outFrame = new Rect(frame.left, frame.top, frame.right, frame.bottom);
        canvas.drawRect(outFrame, mOutFramePaint);
    }

    public void startDraw() {
        invalidate();
    }
}
