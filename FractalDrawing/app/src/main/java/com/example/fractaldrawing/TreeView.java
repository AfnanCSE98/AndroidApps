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
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

public class TreeView extends View {
    private Tree mFractal;
    private int level=1;
    private int x1,x2,y1,y2,x3,y3;
    private Timer timer;

    private ScaleGestureDetector mScaleDetector;///for zooming purpose
    private float mScaleFactor = 1.f;

    public TreeView(Context context){
        super(context);
        mFractal = new Tree();
        timer = new Timer();///starting timer to draw the path dynamically
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                invalidate();
                level++;

                if(level==10){cancelTimer();}
            }
        }, 0, 1000);

        mScaleDetector = new ScaleGestureDetector(context, new TreeView.ScaleListener());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onDraw(Canvas canvas){

        x1 = canvas.getWidth()/2;
        y1 = canvas.getHeight() -100 ;

        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);


        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        canvas.drawRGB(r,g,b);
        mFractal.drawfractal(canvas , x1,y1,90 , level);
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
