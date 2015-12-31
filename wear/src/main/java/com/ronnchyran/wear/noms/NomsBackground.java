package com.ronnchyran.wear.noms;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import android.support.v4.content.ContextCompat;

public class NomsBackground {
    private Context watchContext;
    Bitmap face;
    Bitmap face_ambient;
    Bitmap eyes;
    public NomsBackground(Context watchContext){
        this.watchContext = watchContext;
        this.eyes = BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.eyes);
        this.face = BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.face);
        this.face_ambient = BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.face_ambient);
    }

    public void drawBackground(Canvas canvas, Rect bounds, boolean ambient){
        int eyeHeight = eyes.getHeight();
        canvas.drawBitmap(!ambient ? this.face : this.face_ambient,
                new Rect(0, 0, face.getWidth(), face.getHeight()),
                new RectF(0, 0, bounds.width(), bounds.height()),
                null
        );
        canvas.drawBitmap(eyes,
                new Rect(0, 0, eyes.getWidth(), eyeHeight),
                new RectF(0, 5, bounds.width() , eyeHeight - 5),
                new Paint(Paint.FILTER_BITMAP_FLAG)
        );

    }
}
