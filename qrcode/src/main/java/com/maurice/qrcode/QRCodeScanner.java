package com.maurice.qrcode;

import android.os.Handler;

import com.maurice.qrcode.camera.CameraManager;

/**
 * Created by Maurice on 2017/8/7.
 */

public interface QRCodeScanner {
    int decode = 1;
    int decode_failed = 2;
    int decode_succeeded = 3;
    int quit = 4;
    int restart_preview = 5;

    CameraManager getCameraManager();

    Handler getHandler();

    void handlerDecode(Result rawText);
}
