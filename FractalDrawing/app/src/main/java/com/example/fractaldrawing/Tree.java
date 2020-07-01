package com.example.fractaldrawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ThreadLocalRandom;

public class Tree {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawfractal(Canvas canvas , float x1 , float y1 , double angle, int level){
        Paint paint = new Paint();
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        //paint.setColor(Color.rgb(r,g,b));
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        if(level==0)return;
        if(level<=8){paint.setColor(Color.rgb(0,255,0));}
        else paint.setColor(Color.rgb(0,0,0));

        float x2 = x1 + (float) (Math.cos(Math.toRadians(angle)) * level * 30.0);
        float y2 = y1 - (float) (Math.sin(Math.toRadians(angle)) * level * 30.0);

        canvas.drawLine(x1 , y1 , x2 , y2 , paint);
        drawfractal(canvas , x2 ,y2 , angle-8 , level-1);
        drawfractal(canvas , x2 , y2 , angle+8 , level-1);

    }
}
