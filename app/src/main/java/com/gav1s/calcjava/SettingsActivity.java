package com.gav1s.calcjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    Button buttonApply;
    RadioGroup radioGroup;
    private int themeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeId = getIntent().getExtras().getInt(MainActivity.PreferenceKey);
        doSetTheme(themeId);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void doSetTheme(int themeId) {
        switch (themeId) {
            case 0:
                setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar);
                break;
            case 1:
                setTheme(R.style.AppThemeDark);
                break;
            case 2:
                setTheme(R.style.Theme_Design_Light);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + themeId);
        }
    }

    private void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        buttonApply = findViewById(R.id.btnReturn);
        initOnClickListeners();
        selectRadioButton();
    }

    private void selectRadioButton() {
        switch (themeId) {
            case 0:
                radioGroup.check(R.id.radioNight);
                break;
            case 1:
                radioGroup.check(R.id.radioLight);
                break;
            case 2:
                radioGroup.check(R.id.radioCool);
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void initOnClickListeners() {
        buttonApply.setOnClickListener(v -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radioNight:
                    themeId = 0;
                    break;
                case R.id.radioLight:
                    themeId = 1;
                    break;
                case R.id.radioCool:
                    themeId = 2;
                    break;
            }
            SharedPreferences pref = getSharedPreferences(MainActivity.PreferenceKey, MODE_PRIVATE);
            pref.edit().putInt(MainActivity.MyThemeKey, themeId).apply();
            Intent intentResult = new Intent();
            setResult(RESULT_OK, intentResult);
            finish();
        });
    }
}

