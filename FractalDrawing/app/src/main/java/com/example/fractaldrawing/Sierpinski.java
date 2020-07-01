package com.example.fractaldrawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;
public class Sierpinski {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawfractal(Canvas canvas , float x1 , float y1 , float x2 , float y2, float x3,float y3 , int level){
        Paint paint = new Paint();
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        paint.setColor(Color.rgb(r,g,b));
        paint.setStrokeWidth(7);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        if(level==0) {
            canvas.drawLine(x1 , y1 , x2 , y2 , paint);
            canvas.drawLine(x3 , y3 , x2 , y2 , paint);
            canvas.drawLine(x1 , y1 , x3 , y3 , paint);
        }
        else{

            drawfractal(canvas , x1,y1 , (x1+x2)/2 , (y1+y2)/2 ,(x1+x3)/2 , (y1+y3)/2,level-1);
            //drawfractal(canvas , lm.x , lm.y , mn.x,mn.y,ln.x,ln.y,level-1);
            drawfractal(canvas ,(x1+x3)/2 , (y1+y3)/2 , (x2+x3)/2 , (y2+y3)/2 ,x3,y3,level-1);
            drawfractal(canvas , (x1+x2)/2 , (y1+y2)/2 , x2,y2 ,(x2+x3)/2 , (y2+y3)/2,level-1);
        }
    }
}
