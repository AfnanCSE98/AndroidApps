package com.example.mazemusti;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Runnable;
import android.os.Handler;
import android.widget.Toast;

public class GameView extends View {

    private final static int col=9,row=18;
    private   Cell [][] cells;
    private static int wall_thickness = 4;
    private static float cellsize,hMargin , vMargin;
    private Paint paint,player;
    private  boolean prothom=true;
    private Stack<Cell> st = new Stack<>();
    private Cell tmp;
    Handler  mHandler = new Handler();
    Runnable handlerTask;
    MainActivity mainActivity=new MainActivity();
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        player = new Paint();
        player.setColor(Color.RED);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(wall_thickness);
        Create_Maze();
        dfsSolve();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        int width = getWidth();
        int height = getHeight();
        if(width/height < col/row){
            cellsize = width/(col+1);
        }else cellsize = height/(row+1);

        hMargin = (width-cellsize*col)/2;
        vMargin = (height-cellsize*row)/2 ;

        canvas.translate(hMargin , vMargin);

        if(prothom){
            for(int i=0;i<col;i++){
                for (int j=0;j<row;j++){
                    if(cells[i][j].top)canvas.drawLine(i*cellsize , j*cellsize , (i+1)*cellsize , j*cellsize , paint);
                    if(cells[i][j].right)canvas.drawLine((i+1)*cellsize , j*cellsize , (i+1)*cellsize , (j+1)*cellsize , paint);
                    if(cells[i][j].botttom)canvas.drawLine(i*cellsize , (j+1)*cellsize , (i+1)*cellsize , (j+1)*cellsize , paint);
                    if(cells[i][j].left)canvas.drawLine(i*cellsize , j*cellsize , (i)*cellsize , (j+1)*cellsize , paint);
                }
            }
        }
        prothom=false;
        ///coloring path
        Cell blk,src=cells[0][0];
        int margin = (int)cellsize/10;
        blk = cells[col-1][row-1];

       while (blk.parent!=null){
           st.push(blk);
           blk=blk.parent;
       }
       st.push(cells[0][0]);//this stack stores the path from src to dest
       /* handler = new Handler();
        handlerTask = new Runnable() {
            int margin = (int)cellsize/10;
            @Override
            public void run() {
                if(!st.empty()){
                    //handler.removeCallbacks(this);
                    tmp=st.pop();
                }
                System.out.println(""+tmp.col  );
                //canvas.drawRect(tmp.col*cellsize+margin , tmp.row*cellsize+margin , (tmp.col+1)*cellsize-margin , (tmp.row+1)*cellsize-margin , player);
                Toast.makeText(getContext(), ""+tmp.col, Toast.LENGTH_LONG).show();
                canvas.drawCircle((tmp.col*cellsize+(tmp.col+1)*cellsize)/2 , (tmp.row*cellsize+(tmp.row+1)*cellsize)/2 , 10 , player);
                //Toast.makeText(getContext(), "This is my Toast message!", Toast.LENGTH_LONG).show();

                handler.postDelayed(handlerTask , 1000);
            }

        };
        handlerTask.run();*/
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // Your logic here...
                if(!st.empty()){
                    tmp=st.pop();
                }

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(""+tmp.col  );
                        canvas.drawCircle((tmp.col*cellsize+(tmp.col+1)*cellsize)/2 , (tmp.row*cellsize+(tmp.row+1)*cellsize)/2 , 10 , player);
                    }
                });
            }
        }, 0, 1000); // End of your timer code.

    }


    private void Create_Maze(){
        Stack<Cell> st = new Stack<>();
        cells = new Cell[col][row];
        for(int i=0;i<col;i++)
        {
            for(int j=0;j<row;j++)
            {
                cells[i][j] = new Cell(i , j);
            }
        }
        //upper left theke recursive backtracking
        Cell current = cells[0][0] , next;
        current.visited=true;
        do{
            next = getNeighbour(current);
            if(next!=null){
                removeWall(current , next);
                st.push(current);
                current = next;
                current.visited = true;
            }
            else {
                current = st.pop();
            }
        }
        while (!st.empty());
    }

    private void dfsSolve(){
       Cell src = cells[0][0];
       src.parent = null;
       dfs(src);
    }

    private void dfs(Cell src){
        Cell nxt=null;
        int r=src.row,c=src.col;
        src.inpath=true;
        //invalidate();
        for(int i=0;i<4;i++){
          if(i==0 && r>0 && !src.top){
              if(!cells[c][r-1].botttom)nxt = cells[c][r-1];
          }
          else if(i==1 && c<col-1 && !src.right){
              if(!cells[c+1][r].left)nxt = cells[c+1][r];
          }
          else if(i==2 && r<row-1 && !src.botttom){
              if(!cells[c][r+1].top)nxt = cells[c][r+1];
          }
          else if(i==3 && c>0 && !src.left){
             if(!cells[c-1][r].right) nxt = cells[c-1][r];
          }
          if(nxt!=null && !nxt.inpath){
              nxt.inpath=true;
              nxt.parent=src;
              //invalidate();
              if(nxt.col==col-1 && nxt.row==row-1)return;
              dfs(nxt);
          }
        }
    }
    private Cell getNeighbour(Cell cell){
        ArrayList<Cell> ara = new ArrayList<>();
        if(cell.col>0 && !cells[cell.col-1][cell.row].visited)ara.add(cells[cell.col-1][cell.row]);
        if(cell.row>0 && !cells[cell.col][cell.row-1].visited)ara.add(cells[cell.col][cell.row-1]);
        if(cell.col<col-1 && !cells[cell.col+1][cell.row].visited)ara.add(cells[cell.col+1][cell.row]);
        if(cell.row<row-1 && !cells[cell.col][cell.row+1].visited)ara.add(cells[cell.col][cell.row+1]);

        if(ara.size()>0){
            return ara.get(new Random().nextInt(ara.size()));
        }else{
            return null;
        }
    }
    private void removeWall(Cell curr , Cell nxt){
        if((curr.col == nxt.col) && (curr.row == nxt.row+1)){/// upore
            curr.top=nxt.botttom=false;
        }
        if(curr.col==nxt.col && curr.row == nxt.row-1){///niche
            curr.botttom = nxt.top = false;
        }
        if(curr.col==nxt.col-1 && curr.row==nxt.row ){///dane
            curr.right = nxt.left = false;
        }
        if(curr.col == nxt.col+1 && curr.row == nxt.row){///bame
            curr.left = nxt.right = false;
        }
    }


}
class Cell{
    boolean top=true,botttom=true,left=true,right=true,visited=false,checked=false,inpath=false;
    Cell parent;
    int col,row;
    public Cell(int col,int row){
        this.col = col;this.row=row;
    }

}