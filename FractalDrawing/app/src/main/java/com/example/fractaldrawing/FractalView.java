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

public class FractalView extends View {
    private Fractal mFractal;
    private int level=0;
    private float x1,x2,y1,y2;
    private Timer timer;

    private ScaleGestureDetector mScaleDetector;///for zooming purpose
    private float mScaleFactor = 1.f;


    public FractalView(final Context context ){
        super(context );
        mFractal = new Fractal();

        timer = new Timer();///starting timer to draw the path dynamically
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidate();
                level++;

                if(level==11){cancelTimer();}
            }
        }, 0, 1000);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onDraw(Canvas canvas){
        x1 = canvas.getWidth()/2-250;
        y1 = canvas.getHeight()/2 -100;
        x2 = x1+500;
        y2=y1;

        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        canvas.drawRGB(255,255,255);
        mFractal.drawfractal(canvas , x1 , y1 , x2 , y2 ,level);

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
