package com.example.asciicam;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImageConverter {
    private ColorMatrix matrix = new ColorMatrix();
    private ColorMatrixColorFilter filter;

    public ImageConverter() {
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
    }

    public Bitmap setGrayscaleBitmap(Bitmap bitmap) {
        Bitmap grayscaleBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(grayscaleBitmap);
        canvas.drawBitmap(grayscaleBitmap, 0, 0, paint);
        return grayscaleBitmap;
    }
}

