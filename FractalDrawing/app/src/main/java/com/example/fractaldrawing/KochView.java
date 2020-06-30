package com.example.fractaldrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.sqrt;

public class KochView extends View{
    private Koch mFractal;
    private int level=0;
    private float x1,x2,y1,y2,x3,y3;
    private Timer timer;

    private ScaleGestureDetector mScaleDetector;///for zooming purpose
    private float mScaleFactor = 1.f;

    public KochView(Context context){
        super(context);
        mFractal = new Koch();

        timer = new Timer();///starting timer to draw the path dynamically
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                invalidate();
                level++;

                if(level==4){cancelTimer();}
            }
        }, 0, 2500);

        mScaleDetector = new ScaleGestureDetector(context, new KochView.ScaleListener());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onDraw(Canvas canvas){
        x1 = canvas.getWidth()/2-300;
        y1 = canvas.getHeight()/2 ;
        x2 = x1+600;
        y2=y1;

        x3 = x1 + 300;
        y3 = (float) (y1 - (float)300*sqrt(3));

        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawRGB(255,255,255);
        mFractal.drawfractal1(canvas , x1 , y1 , x2 , y2 ,level);
        mFractal.drawfractal1(canvas , x3 , y3 , x1 , y1 ,level);
        mFractal.drawfractal1(canvas , x2 , y2 , x3 , y3 ,level);

        canvas.restore();

    }
    private void cancelTimer(){
        timer.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        return true;
    }
    ///to enable pinch zooming
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }
}
