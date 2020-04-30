package com.example.guessthegibberish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Settings_Activity extends AppCompatActivity {

    private RadioGroup rd_grp,rd_grp_level;
    private RadioButton rd_btn,rd_btn_level;
    private boolean Sound_state;
    private int level;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);
        //Sound_Choose_Option();
        rd_grp=(RadioGroup)findViewById(R.id.radio_grp);
        rd_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
                editor = pref.edit();
                rd_btn = (RadioButton) findViewById(checkedId);
                if(rd_btn.getText().toString().equals("On"))Sound_state = true;
                else Sound_state = false;

                editor.putBoolean("sound_state" , Sound_state);
                //Toast.makeText(Settings_Activity.this, Boolean.toString(Sound_state), Toast.LENGTH_SHORT).show();
            }
        });

        rd_grp_level = (RadioGroup)findViewById(R.id.rd_grp_level);
        rd_grp_level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                pref = getSharedPreferences("GuessTheGibberish" , MODE_PRIVATE);
                editor = pref.edit();
                rd_btn_level = (RadioButton)findViewById(checkedId);
                if(rd_btn_level.getText().toString().equals("Easy"))level = 0;
                else if(rd_btn_level.getText().toString().equals("Medium"))level = 1;
                else level = 2;

                editor.putInt("game_level" , level);
                //Toast.makeText(Settings_Activity.this, Integer.toString(level), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
    }
}
