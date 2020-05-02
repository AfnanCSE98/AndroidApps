package com.example.guessthegibberish;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
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

import java.util.Random;

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
    int i,no_of_ques;
    int [] q_idx;

    int sec;
    Toast toast;
    boolean sound_state,activity_running;
    int game_level; ///1 for med, 0 for easy , 3 for hard
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_running=true;
        ///animation
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        AnimationDrawable animationDrawable = (AnimationDrawable) ll.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.logo);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
        editor = pref.edit();

        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        no_of_ques = questions.length;
        q_idx = new int[no_of_ques];
        for(int i=0;i<no_of_ques;i++){
            q_idx[i] = i;
        }
        shuffleArray(q_idx);

        StartButtonAction();
        nextButton();

    }

    public void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void StartButtonAction(){

        bt = (Button)findViewById(R.id.button);
        q_text = (TextView)findViewById(R.id.q_text) ;
        ans_text = (TextView) findViewById(R.id.ans);
        mp = MediaPlayer.create(this , R.raw.tick);
        ans_text.setVisibility(View.INVISIBLE);
        //set idx


        bt.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                       // i = q_serial;
                        if(game_level==2)sec=6;
                        else if(game_level==1)sec = 8;
                        else if(game_level==0)sec = 10;

                        q_text.setText(questions[q_idx[i++]]);

                        i_handler = new Handler();
                        i_handlerTask = new Runnable() {


                        @Override
                        public void run() {
                            ans_text.setVisibility(View.INVISIBLE);
                        if(sec>=1) {

                            if(activity_running)tst_snd();
                            sec--;

                        }else{
                            ans_text.setVisibility(View.VISIBLE);
                            ans_text.setText(answers[q_idx[i-1]]);
                            ans_text.setBackgroundResource(R.color.lime);

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
                        else if(game_level==1)sec = 7;
                        else if(game_level==0 || game_level==-1)sec = 8;

                        q_text.setText(questions[q_idx[i++]]);

                        i_handler = new Handler();
                        i_handlerTask = new Runnable() {


                            @Override
                            public void run() {
                                ans_text.setVisibility(View.INVISIBLE);
                                if(sec>=1) {
                                    if(activity_running)tst_snd();
                                    sec--;
                                }else{
                                    ans_text.setVisibility(View.VISIBLE);
                                    ans_text.setText(answers[q_idx[i-1]]);
                                    ans_text.setBackgroundResource(R.color.lime);
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

            editor.putInt("game_level" , game_level);
            editor.putBoolean("sound_state" , sound_state);
            startActivity(intent);
            return(true);
        case R.id.exit:
            finish();
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
        activity_running=false;
        Log.i("MainActivity" , "pause of main called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
        sound_state = pref.getBoolean("sound_state" , true);
        game_level = pref.getInt("game_level" , 2);
       // q_serial = pref.getInt("q_serial" , 0);
        Log.i("MainActivity" , "resume of main called");
        activity_running = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        activity_running=false;
    }
    public void tst_snd(){
        toast = Toast.makeText(MainActivity.this , Integer.toString(sec) , Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if(sound_state)mp.start();
        toast.show();
    }


}

