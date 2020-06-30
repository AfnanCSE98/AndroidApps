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
    Point l,m,n;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawfractal(Canvas canvas , int x1 , int y1 , int x2 , int y2, int x3,int y3 , int level){
        Paint paint = new Paint();
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        paint.setColor(Color.rgb(r,g,b));
        paint.setStrokeWidth(7);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        l = new Point(x1, y1);
        m = new Point(x2 , y2);
        n = new Point(x3 , y3);

        if(level==0) {
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.lineTo(m.x, m.y);
            path.lineTo(n.x, n.y);
            path.lineTo(l.x, l.y);
            path.close();

            canvas.drawPath(path, paint);
        }
        else{
            Point lm = new Point((x1+x2)/2 , (y1+y2)/2);
            Point mn = new Point((x2+x3)/2 , (y2+y3)/2);
            Point ln = new Point((x1+x3)/2 , (y1+y3)/2);

            drawfractal(canvas , l.x , l.y , lm.x,lm.y,ln.x,ln.y,level-1);
            drawfractal(canvas , lm.x , lm.y , mn.x,mn.y,ln.x,ln.y,level-1);
            drawfractal(canvas , ln.x , ln.y , mn.x,mn.y,n.x,n.y,level-1);
            drawfractal(canvas , lm.x , lm.y , m.x,m.y,mn.x,mn.y,level-1);
        }
    }
}
