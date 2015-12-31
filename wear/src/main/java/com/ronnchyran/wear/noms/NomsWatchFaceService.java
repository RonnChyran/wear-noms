package com.ronnchyran.wear.noms;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Looper;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.os.Handler;
import android.text.format.Time;

import java.util.Date;


public class NomsWatchFaceService extends CanvasWatchFaceService {
    private static final long TICK_PERIOD_MILLIS = 33L;

    @Override
    public Engine onCreateEngine() {
        return new NomsEngine();
    }

    private class NomsEngine extends CanvasWatchFaceService.Engine {
        private Handler timeTick;
        private NomsWatchFace watchFace;
        private int mouthFrameCounter = 11;
        private final int MOUTH_FRAMES = 11;
        private int currentMinute = 0;
        @Override
        public void onCreate(SurfaceHolder holder){
            super.onCreate(holder);
            setWatchFaceStyle(new WatchFaceStyle.Builder(NomsWatchFaceService.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setAmbientPeekMode(WatchFaceStyle.AMBIENT_PEEK_MODE_HIDDEN)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setStatusBarGravity(Gravity.AXIS_PULL_BEFORE)
                    .setShowSystemUiTime(false)
                    .build());
            timeTick = new Handler(Looper.myLooper());
            startTimerIfNecessary();
            watchFace = NomsWatchFace.newInstance(NomsWatchFaceService.this);

        }
        private void startTimerIfNecessary() {
            timeTick.removeCallbacks(timeRunnable);
            if (isVisible() && !isInAmbientMode()) {
                timeTick.post(timeRunnable);
            }
        }
        private final Runnable timeRunnable = new Runnable() {
            @Override
            public void run() {
                onSecondTick();
                if (isVisible() && !isInAmbientMode()) {
                    timeTick.postDelayed(this, TICK_PERIOD_MILLIS);
                }
            }
        };
        private void onSecondTick() {
            Time now = new Time();
            now.setToNow();
            if (now.minute != this.currentMinute){
                System.gc(); //reliably call GC every minute before playing the animation to reduce lag.
                this.mouthFrameCounter = 0;
                this.currentMinute = now.minute;
            }
            invalidateIfNecessary(); //invalidate every second?
        }

        private void invalidateIfNecessary() {
            if (isVisible() && !isInAmbientMode()) { //if visible and is not ambient mode redraw
                invalidate();
            }
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            if(!isInAmbientMode()){
                if(MOUTH_FRAMES > mouthFrameCounter) {

                    watchFace.draw(canvas, bounds, mouthFrameCounter);
                    mouthFrameCounter++;
                    invalidate();
                }else {
                    watchFace.draw(canvas, bounds, 0);
                }
            }else {
                watchFace.drawAmbient(canvas, bounds);
            }

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            startTimerIfNecessary();
        }


        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            watchFace.setInAmbientMode(inAmbientMode);
            watchFace.setAntiAlias(!inAmbientMode);
            watchFace.setColor(inAmbientMode ? Color.GRAY : Color.WHITE);
            invalidate();
            if(!inAmbientMode){
                System.gc();
                mouthFrameCounter = 0;
            }
            startTimerIfNecessary();
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onDestroy() {
            timeTick.removeCallbacks(timeRunnable);
            super.onDestroy();
        }
    }
}
