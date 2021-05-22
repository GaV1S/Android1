package com.gav1s.calcjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected static final String PreferenceKey = "THEME";
    protected static final String MyThemeKey = "SET_THEME";
    protected static final int REQUEST_CODE = 44;
    private TextView resultField;
    private EditText numberField;
    private TextView operationField;
    private Double operand = null;
    private String lastOperation = "=";
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8,
            button9, button_point, button_plus, button_minus, button_multiply, button_divide,
            button_result, buttonSettings;
    TextView textView;
    private final String OPERATION = "OPERATION";
    private final String OPERAND = "OPERAND";
    private int themeId;
    boolean lastTouchResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences(PreferenceKey, MODE_PRIVATE);
        themeId = pref.getInt(MyThemeKey, 0);
        doSetTheme(themeId);

        setContentView(R.layout.activity_main);
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);

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
        buttonSettings = findViewById(R.id.btnChangeTheme);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button_point = findViewById(R.id.button_point);
        button_plus = findViewById(R.id.button_plus);
        button_minus = findViewById(R.id.button_minus);
        button_multiply = findViewById(R.id.button_multiply);
        button_divide = findViewById(R.id.button_divide);
        button_result = findViewById(R.id.button_result);
        textView = findViewById(R.id.numberField);
        buttonsInitOnClickListeners();
        lastTouchResult = false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK) {
            recreate();
        }
    }

    private void buttonsInitOnClickListeners() {
        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(PreferenceKey, themeId);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OPERATION, lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString(OPERATION);
        operand = savedInstanceState.getDouble(OPERAND);
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