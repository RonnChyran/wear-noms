package com.ronnchyran.wear.noms;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
public class NomsWatchFace {
    private static final String TIME_FORMAT_WITHOUT_SECONDS = "%02d:%02d";
    private final Paint timePaint;
    private boolean inAmbientMode;
    private NomsBackground background;
    private NomsJaws jaws;

    public static NomsWatchFace newInstance(Context context) {
        Paint timePaint = new Paint();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "big_noodle_titling.ttf");
        timePaint.setColor(Color.WHITE);
        timePaint.setAntiAlias(true);
        timePaint.setTypeface(typeface);
        return new NomsWatchFace(timePaint, new NomsBackground(context), new NomsJaws(context));
    }

    NomsWatchFace(Paint timePaint, NomsBackground background, NomsJaws jaws) {
        this.timePaint = timePaint;
        this.background = background;
        this.jaws = jaws;
    }

    public void draw(Canvas canvas, Rect bounds, int mouthFrame) {
        background.drawBackground(canvas, bounds, this.inAmbientMode);
        timePaint.setTextSize((canvas.getHeight() - 15f) / 3f);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date today = new Date();
        String timeText = sdf.format(today);
        this.jaws.drawMouthFrame(canvas, bounds, mouthFrame, timeText, timePaint);

    }
    public void drawAmbient(Canvas canvas, Rect bounds){
        timePaint.setTextSize((canvas.getHeight() - 15f) / 3f);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date today = new Date();
        String timeText = sdf.format(today);
        background.drawBackground(canvas, bounds, this.inAmbientMode);
        float timeXOffset = NomsWatchFace.computeXOffset(timeText, timePaint, bounds);
        float timeYOffset = NomsWatchFace.computeTimeYOffset(timeText, timePaint, bounds);
        canvas.drawText(timeText, timeXOffset, timeYOffset, timePaint);
    }
    public static float computeXOffset(String text, Paint paint, Rect watchBounds) {
        float centerX = watchBounds.exactCenterX();
        float timeLength = paint.measureText(text);
        return centerX - (timeLength / 2.0f);
    }

    public static float computeTimeYOffset(String timeText, Paint timePaint, Rect watchBounds) {
        float centerY = watchBounds.exactCenterY();
        Rect textBounds = new Rect();
        timePaint.getTextBounds(timeText, 0, timeText.length(), textBounds);
        int textHeight = textBounds.height();
        return centerY + textHeight;
    }

    public void setAntiAlias(boolean antiAlias) {
        timePaint.setAntiAlias(antiAlias);
    }

    public void setColor(int color) {
        timePaint.setColor(color);
    }

    public void setInAmbientMode(boolean inAmbientMode){
        this.inAmbientMode = inAmbientMode;
    }
}
