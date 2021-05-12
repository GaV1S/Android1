package com.gav1s.calcjava;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String appTheme = "APP_THEME";
    private static final String NameSharedPreference = "THEME";

    private static final int MyCoolCodeStyle = 0;
    private static final int AppThemeLightCodeStyle = 1;
    private static final int AppThemeCodeStyle = 2;
    private static final int AppThemeDarkCodeStyle = 3;

    TypedValue outValue = new TypedValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentTheme = getIntent().getExtras().getString(MainActivity.YOUR_THEME);
        switch (currentTheme) {
            case "dark": {
                setTheme(getAppTheme(R.style.AppThemeDark));
            }
            case "light": {
                setTheme(getAppTheme(R.style.AppThemeLight));
            }
            case "myCool": {
                setTheme(getAppTheme(R.style.MyCoolStyle));
            }
            case "appTheme": {
                setTheme(getAppTheme(R.style.AppTheme));
            }
        }
        setContentView(R.layout.activity_settings);

        initView();
    }

    private void initView() {
        findViewById(R.id.radioNight)
                .setOnClickListener(v -> {
                    setAppTheme(AppThemeDarkCodeStyle);
                    recreate();
                });
        findViewById(R.id.radioLight)
                .setOnClickListener(v -> {
                    setAppTheme(AppThemeLightCodeStyle);
                    recreate();
                });
        findViewById(R.id.radioAT)
                .setOnClickListener(v -> {
                    setAppTheme(AppThemeCodeStyle);
                    recreate();
                });
        findViewById(R.id.radioCool)
                .setOnClickListener(v -> {
                    setAppTheme(MyCoolCodeStyle);
                    recreate();
                });
        Button buttonReturn = findViewById(R.id.btnReturn);
        buttonReturn.setOnClickListener(v -> {
            Intent intentResult = new Intent();
            getTheme().resolveAttribute(R.attr.themeName, outValue, true);
            intentResult.putExtra(MainActivity.YOUR_THEME, createTheme());
            setResult(Activity.RESULT_OK, intentResult);

            finish();
        });
    }

    private YourTheme createTheme() {
        getTheme().resolveAttribute(R.attr.themeName, outValue, true);
        YourTheme yourTheme = new YourTheme(outValue.string.toString());
        return yourTheme;
    }

    protected void setAppTheme(int code) {
        SharedPreferences sharedPreferences = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(appTheme, code)
                .apply();
    }

    protected int getAppTheme(int code) {
        return codeStyleByStyleId(getCodeStyleId(code));
    }

    protected int codeStyleByStyleId(int code) {
        switch (code) {
            case AppThemeCodeStyle:
                return R.style.AppTheme;
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            case AppThemeDarkCodeStyle:
                return R.style.AppThemeDark;
            case MyCoolCodeStyle:
            default:
                return R.style.MyCoolStyle;
        }
    }

    protected int getCodeStyleId(int codeStyle) {
        SharedPreferences sharedPreferences = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPreferences.getInt(appTheme, codeStyle);
    }
}