package com.ronnchyran.wear.noms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/*
* Handles drawing the mouth and time in interactive mode
* If in ambient mode,
*/
public class NomsJaws {
    private Context watchContext;
    private String previousTimeText;
    public static final int[] _mouthFrames = {
            R.mipmap.mouth_anim00,
            R.mipmap.mouth_anim01,
            R.mipmap.mouth_anim02,
            R.mipmap.mouth_anim03,
            R.mipmap.mouth_anim04,
            R.mipmap.mouth_anim05,
            R.mipmap.mouth_anim06,
            R.mipmap.mouth_anim07,
            R.mipmap.mouth_anim08,
            R.mipmap.mouth_anim09,
            R.mipmap.mouth_anim10,
            R.mipmap.mouth_anim11
    };
    public final Bitmap[] mouthFrames;
    private int mouthWidth;
    private int mouthHeight;
    public NomsJaws(Context watchContext){
        this.watchContext = watchContext;
        this.previousTimeText = "";
        Bitmap mouth = BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth);
        mouthWidth = mouth.getWidth();
        mouthHeight = mouth.getHeight();
        mouthFrames = new Bitmap[]{
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim00),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim01),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim02),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim03),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim04),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim05),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim06),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim07),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim08),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim09),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim10),
                BitmapFactory.decodeResource(watchContext.getResources(), R.mipmap.mouth_anim11)
        };

    }
    public void drawMouthFrame(Canvas canvas, Rect bounds, int frameIndex, String timeText, Paint timePaint){
        canvas.drawBitmap(mouthFrames[frameIndex],
                new Rect(0, 0, mouthWidth, mouthHeight),
                new RectF(0, bounds.height() - mouthHeight, bounds.width(), bounds.height() - 25),
                new Paint(Paint.FILTER_BITMAP_FLAG));

        switch(frameIndex){
            case 0:
            case 11:
                float timeXOffset = NomsWatchFace.computeXOffset(timeText, timePaint, bounds);
                float timeYOffset = NomsWatchFace.computeTimeYOffset(timeText, timePaint, bounds);
                canvas.drawText(timeText, timeXOffset, timeYOffset, timePaint);
        }
    }
}
