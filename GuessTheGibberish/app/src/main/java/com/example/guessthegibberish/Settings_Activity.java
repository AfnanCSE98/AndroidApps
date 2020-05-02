package com.example.guessthegibberish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Settings_Activity extends AppCompatActivity {

    private RadioGroup rd_grp,rd_grp_level;
    private RadioButton rd_btn,rd_btn_level;
    private boolean Sound_state=false;
    private int level=-1;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    boolean sound_state;int game_level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        Log.i("Settings_Activity" , "Oncreate of settings called");
        onResume();
        //pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
        editor = pref.edit();

        rd_grp=(RadioGroup)findViewById(R.id.radio_grp);

        if(sound_state)rd_grp.check(R.id.on);
        else rd_grp.check(R.id.off);

        if(rd_grp.getCheckedRadioButtonId() == -1){
            //pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
            //editor = pref.edit();
            Sound_state = false;
            editor.putBoolean("sound_state" , Sound_state);
        }
        rd_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                rd_btn = (RadioButton) findViewById(checkedId);
                //rd_btn.setChecked(true);
                if(rd_btn.getText().toString().equals("On"))Sound_state = true;
                else if(rd_btn.getText().toString().equals("Off"))Sound_state = false;

                editor.putBoolean("sound_state" , Sound_state);
                //editor.putInt("q_serial" , q_serial);
                //Toast.makeText(Settings_Activity.this, Boolean.toString(Sound_state), Toast.LENGTH_SHORT).show();
            }
        });

        rd_grp_level = (RadioGroup)findViewById(R.id.rd_grp_level);

        if(game_level==0)rd_grp_level.check(R.id.easy_level);
        else if(game_level==1)rd_grp_level.check(R.id.medium_level);
        else if(game_level==2)rd_grp_level.check(R.id.hard_level);

        if(rd_grp_level.getCheckedRadioButtonId() == -1){
            //pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
            //editor = pref.edit();
            editor.putInt("game_level" , -1);
        }
        rd_grp_level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                rd_btn_level = (RadioButton)findViewById(checkedId);
                //rd_btn.setChecked(true);
                if(rd_btn_level.getText().toString().equals("Easy"))level = 0;
                else if(rd_btn_level.getText().toString().equals("Medium"))level = 1;
                else if(rd_btn_level.getText().toString().equals("Hard"))level = 2;

                editor.putInt("game_level" , level);
                //editor.putInt("q_serial" , q_serial);
                //Toast.makeText(Settings_Activity.this, Integer.toString(level), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
        Log.i("Settings_Activity" , "pause called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
        sound_state = pref.getBoolean("sound_state" , true);
        game_level = pref.getInt("game_level" , 2);
        Log.i("Settings_Activity" , "resume of settings called");

    }

    @Override
    public void onBackPressed(){
        finish();


    }
}
