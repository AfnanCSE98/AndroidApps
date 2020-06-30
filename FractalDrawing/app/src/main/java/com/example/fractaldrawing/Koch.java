package com.example.fractaldrawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

public class Koch {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawfractal1(Canvas canvas , float x1 , float y1 , float x2 , float y2, int level){
        Paint paint = new Paint();
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        paint.setColor(Color.rgb(r,g,b));
        paint.setStrokeWidth(7);

        if(level==0) {
            canvas.drawLine(x1,y1,x2,y2,paint);
        }
        else{
            ///https://stackoverflow.com/questions/15367165/finding-coordinates-of-koch-curve
            ///from here, i got to know the equations for koch snowflake
            float ux = x2-x1;
            float uy = y2-y1;
            float vx = y1-y2;
            float vy = x2-x1;

            float xa = x1 + ux/3;
            float ya = y1 + uy/3;
            float xb = x1 + ux/2 + (float) sqrt(3)*vx/6;
            float yb = y1 +uy/2 + (float)sqrt(3)*vy/6;
            float xc = x1 + 2*ux/3;
            float yc = y1 + 2*uy/3;
            drawfractal1(canvas , x1,y1,xa,ya,level-1);
            drawfractal1(canvas , xa,ya,xb,yb,level-1);
            drawfractal1(canvas , xb,yb,xc,yc,level-1);
            drawfractal1(canvas , xc,yc,x2,y2,level-1);
        }
    }
}
