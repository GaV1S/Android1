package com.gav1s.calcjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String YOUR_THEME = "YOUR_THEME";
    public static final int REQUEST_CODE = 42;

    private static final String NameSharedPreference = "THEME";
    private static final String appTheme = "APP_THEME";

    private static final int MyCoolCodeStyle = 0;
    private static final int AppThemeLightCodeStyle = 1;
    private static final int AppThemeCodeStyle = 2;
    private static final int AppThemeDarkCodeStyle = 3;

    private YourTheme yourTheme;

    private String currentTheme = "";

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";
    TypedValue outValue = new TypedValue();
    String forCommit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        switch (currentTheme) {
            case "dark": {
                setAppTheme(getAppTheme(R.style.AppThemeDark));
                resultField.setText(currentTheme);
            }
            forCommit = "Probe";
            case "light": {
                setAppTheme(getAppTheme(R.style.AppThemeLight));
                resultField.setText(currentTheme);
            }
            case "myCool": {
                setAppTheme(getAppTheme(R.style.MyCoolStyle));
                resultField.setText(currentTheme);
            }
            case "appTheme": {
                setAppTheme(getAppTheme(R.style.AppTheme));
                resultField.setText(currentTheme);
            }
        }
        setContentView(R.layout.activity_main);
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);


        initView();
    }

    private void initView() {
        findViewById(R.id.btnChangeTheme).setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            fillTheme();
            intent.putExtra(YOUR_THEME, outValue.string);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    public void fillTheme() {
        getTheme().resolveAttribute(R.attr.themeName, outValue, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            yourTheme = data.getParcelableExtra(YOUR_THEME);
            currentTheme = yourTheme.getName();

            switch (currentTheme) {
                case "dark": {
                    recreate();
                    setAppTheme(getAppTheme(R.style.AppThemeDark));
                    resultField.setText(currentTheme);
                    recreate();

                }
                case "light": {
                    recreate();
                    setAppTheme(getAppTheme(R.style.AppThemeLight));
                    resultField.setText(currentTheme);
                    recreate();

                }
                case "myCool": {
                    recreate();
                    setAppTheme(getAppTheme(R.style.MyCoolStyle));
                    resultField.setText(currentTheme);
                    recreate();

                }
                case "appTheme": {
                    recreate();
                    setAppTheme(getAppTheme(R.style.AppTheme));
                    resultField.setText(currentTheme);
                    recreate();

                }
            }

            initView();
        }
    }

    private void setAppTheme(int code) {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view) {

        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    public void onOperationClick(View view) {

        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {

        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

}


