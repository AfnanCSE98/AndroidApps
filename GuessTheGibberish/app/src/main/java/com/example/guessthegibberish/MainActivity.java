package com.example.guessthegibberish;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bt,bt_next;
    TextView q_text;
    TextView ans_text;
    String [] questions;
    String [] answers;
    Handler  i_handler;
    Runnable i_handlerTask;
    MediaPlayer mp;
    Intent intent;
    int i=0;
    int sec;
    StringBuilder sb;
    boolean sound_state;
    int game_level; ///1 for med, 0 for easy , 3 for hard
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.logo);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        StartButtonAction();
        nextButton();

    }


    public void StartButtonAction(){
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        bt = (Button)findViewById(R.id.button);
        q_text = (TextView)findViewById(R.id.q_text) ;
        ans_text = (TextView) findViewById(R.id.ans);
        mp = MediaPlayer.create(this , R.raw.tick);
        ans_text.setVisibility(View.INVISIBLE);
        bt.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                        if(game_level==2)sec=6;
                        else if(game_level==1)sec = 8;
                        else if(game_level==0)sec = 10;

                        View parent = (View) v.getParent();
                        final int parentHeight = parent.getHeight();
                        final int parentWidth = parent.getWidth();

                        q_text.setText(questions[i++]);
                        ///sb.append(questions[i-1]).append(",");
                        i_handler = new Handler();
                        i_handlerTask = new Runnable() {


                        @Override
                        public void run() {
                            ans_text.setVisibility(View.INVISIBLE);
                        if(sec>=1) {
                            LayoutInflater li = getLayoutInflater();
                            View layout = li.inflate(R.layout.time, (ViewGroup) findViewById(R.id.custom_time_toast_layout_id));


                            Toast toast = Toast.makeText(MainActivity.this , Integer.toString(sec) , Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            if(sound_state)mp.start();
                            sec--;
                        }else{
                            ans_text.setVisibility(View.VISIBLE);
                            ans_text.setText(answers[i-1]);
                            ans_text.setBackgroundResource(R.color.green);
                        }
                            i_handler.removeCallbacksAndMessages(null);
                            i_handler.postDelayed(i_handlerTask , 1000);
                    }
                    };
                        i_handlerTask.run();
                    }
                }
        );
    }

    public void nextButton(){
        bt_next = (Button)findViewById(R.id.btn_next);
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        bt = (Button)findViewById(R.id.button);
        q_text = (TextView)findViewById(R.id.q_text) ;
        ans_text = (TextView) findViewById(R.id.ans);


        bt_next.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                        ans_text.setText("");

                        if(game_level==2)sec=6;
                        else if(game_level==1)sec = 8;
                        else if(game_level==0)sec = 10;

                        View parent = (View) v.getParent();
                        final int parentHeight = parent.getHeight();
                        final int parentWidth = parent.getWidth();

                        q_text.setText(questions[i++]);
                        ///sb.append(questions[i-1]);
                        i_handler = new Handler();
                        i_handlerTask = new Runnable() {


                            @Override
                            public void run() {
                                ans_text.setVisibility(View.INVISIBLE);
                                if(sec>=1) {
                                    LayoutInflater li = getLayoutInflater();
                                    View layout = li.inflate(R.layout.time, (ViewGroup) findViewById(R.id.custom_time_toast_layout_id));
                                    Toast toast = Toast.makeText(MainActivity.this , Integer.toString(sec) , Toast.LENGTH_SHORT);
                                   // Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                   // toast.setText(Integer.toString(sec));

                                    toast.show();
                                    if(sound_state)mp.start();
                                    sec--;
                                }else{
                                    ans_text.setVisibility(View.VISIBLE);
                                    ans_text.setText(answers[i-1]);
                                    ans_text.setBackgroundResource(R.color.green);
                                }
                                i_handler.removeCallbacksAndMessages(null);
                                i_handler.postDelayed(i_handlerTask , 1000);
                            }
                        };
                        i_handlerTask.run();
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {

        case R.id.settings:
            intent = new Intent(this, Settings_Activity.class);
            startActivity(intent);

            return(true);
        case R.id.exit:
            finish();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume(){
        super.onResume();
        pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
        sound_state = pref.getBoolean("sound_state" , false);
        game_level = pref.getInt("game_level" , 0);

    }
}

