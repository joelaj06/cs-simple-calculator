package com.example.cssimplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView solutionTextView, resultTextView;
    MaterialButton buttonAC, bracketOpen, bracketClose;
    MaterialButton button1, button2, button3, button4, button5, button6, button7, button8,
            button9, button0;
    MaterialButton buttonDot, buttonC;
    //symbols
    MaterialButton times, divide, minus, plus, equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solutionTextView = findViewById(R.id.solution_text_view); // Fix here
        resultTextView = findViewById(R.id.result_text_view);


        // Initializing the buttons
        buttonAC = assignButtonId(R.id.button_ac);
        bracketOpen = assignButtonId(R.id.button_open_bracket);
        bracketClose = assignButtonId(R.id.button_close_bracket);
        button1 = assignButtonId(R.id.button_1);
        button2 = assignButtonId(R.id.button_2);
        button3 = assignButtonId(R.id.button_3);
        button4 = assignButtonId(R.id.button_4);
        button5 = assignButtonId(R.id.button_5);
        button6 = assignButtonId(R.id.button_6);
        button7 = assignButtonId(R.id.button_7);
        button8 = assignButtonId(R.id.button_8);
        button9 = assignButtonId(R.id.button_9);
        button0 = assignButtonId(R.id.button_0);
        buttonDot = assignButtonId(R.id.button_dot);
        buttonC = assignButtonId(R.id.button_c);
        times = assignButtonId(R.id.button_times);
        divide = assignButtonId(R.id.button_divide);
        minus = assignButtonId(R.id.button_minus);
        plus = assignButtonId(R.id.button_plus);
        equal = assignButtonId(R.id.button_equal);


    }


    MaterialButton assignButtonId(int id) {
        MaterialButton button = findViewById(id);
        button.setOnClickListener(this);
        return button;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String currentText = solutionTextView.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTextView.setText("");
            resultTextView.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            solutionTextView.setText(resultTextView.getText().toString());
            return;
        }

        if (buttonText.equals("C")) {
            if(currentText.length() == 1 || currentText.isEmpty()){
                solutionTextView.setText("");
                resultTextView.setText("0");
              return;
            }
            currentText = currentText.substring(0, currentText.length() - 1);

        } else {
            currentText += buttonText;
        }

        solutionTextView.setText(currentText);
        String finalResult = getResult(currentText);
        if(!finalResult.equals("Error")){
            Log.i("result",finalResult);
           resultTextView.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String res = context.evaluateString(scriptable, data, "Javascript",
                    1, null).toString();
            if(res.contains("Undefined")){
                res = "0";
            }
            if(res.endsWith(".0")){
                res = res.replace(".0","");
            }
            return res;
        }catch (Exception e){
            return "Error";
        }
    }
}