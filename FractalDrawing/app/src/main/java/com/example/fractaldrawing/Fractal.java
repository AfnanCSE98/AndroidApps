package com.example.fractaldrawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ThreadLocalRandom;

public class Fractal {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawfractal(Canvas canvas , float x1 , float y1 , float x2 , float y2, int level){
        Paint paint = new Paint();
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        paint.setColor(Color.rgb(r,g,b));
        paint.setStrokeWidth(7);

        if(level==0) canvas.drawLine(x1,y1,x2,y2,paint);
        else{
            float xn = (x1+x2)/2 + (y1-y2)/2;
            float yn = (x2-x1)/2 + (y1+y2)/2;
            drawfractal(canvas , x1,y1,xn,yn,level-1);
            drawfractal(canvas , xn,yn,x2,y2,level-1);
        }
    }
}
