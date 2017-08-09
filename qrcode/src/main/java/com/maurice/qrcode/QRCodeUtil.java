package com.maurice.qrcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.maurice.qrcode.common.BitMatrix;
import com.maurice.qrcode.decoder.ErrorCorrectionLevel;
import com.maurice.qrcode.encoder.BarcodeFormat;

import java.util.HashMap;

/**
 * Created by Maurice on 2017/8/9.
 */

public class QRCodeUtil {
    public static Bitmap getQRcodeBitmap(String msg, int width, int height, int margin) {
        QRCodeWriter qw = new QRCodeWriter();

        try {
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, margin);
            BitMatrix matrix = qw.encode(msg, BarcodeFormat.QR_CODE, width, height, hints);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }
}
