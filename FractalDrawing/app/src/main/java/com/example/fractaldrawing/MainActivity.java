package com.example.fractaldrawing;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll;
    private FractalView fv ;
    private KochView kv;
    private SierpinskiView sv;
    private TreeView tv;
    Button c_curve , koch , tree ,sierpinski;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.ll);

        c_curve = (findViewById(R.id.c_curve));
        c_curve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.removeAllViews();
                fv = new FractalView(MainActivity.this);
                ll.addView(fv , 0);
            }
        });

        koch = findViewById(R.id.koch);
        koch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.removeAllViews();
                kv = new KochView(MainActivity.this);
                ll.addView(kv , 0);
            }
        });

        sierpinski = findViewById(R.id.sierpinski);
        sierpinski.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.removeAllViews();
                sv = new SierpinskiView(MainActivity.this);
                ll.addView(sv , 0);
            }
        });

        tree = findViewById(R.id.tree);
        tree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.removeAllViews();
                tv = new TreeView(MainActivity.this);
                ll.addView(tv , 0);
            }
        });

    }
}


