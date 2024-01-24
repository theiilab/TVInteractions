package com.yuanren.tvinteractions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yuanren.tvinteractions.MainActivity;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.network.RandomPositionSocketService;

public class LoginActivity extends AppCompatActivity {
    private EditText participantET;
    private EditText sessionET;
    private ToggleButton methodBtn;
    private Button button;
    private LinearLayout layout4;
    private ToggleButton dataSetBtn;

    /** ----- log ----- */
    String pid = "0";
    String session = "0";
    String method = "Remote";
    String dataSet = "0";
    /** --------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        participantET = findViewById(R.id.pid);
        sessionET = findViewById(R.id.session);
        methodBtn = findViewById(R.id.method);
        button = findViewById(R.id.submit);
        layout4 = findViewById(R.id.layout4);
        dataSetBtn = findViewById(R.id.dataSet);

        layout4.setVisibility(View.GONE);

        participantET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        participantET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    pid = v.getText().toString();
                    sessionET.requestFocus();
                    return true;
                }
                return false;
            }
        });

        sessionET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        sessionET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    session = v.getText().toString();

                    if (session.equals("1") || session.equals("2")) {
                        layout4.setVisibility(View.VISIBLE);
                    } else if (session.equals("3")){
                        layout4.setVisibility(View.GONE);
                    }
                    methodBtn.requestFocus();
                    return true;
                }
                return false;
            }
        });

        methodBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                method = isChecked ? "Smartwatch" : "Remote";

                if (layout4.getVisibility() == View.VISIBLE) {
                    dataSetBtn.requestFocus();
                }
            }
        });

        dataSetBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataSet = isChecked ? "1" : "0";
                button.requestFocus();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** -------- log -------- */
                Metrics metrics = (Metrics) getApplicationContext();
                metrics.pid = Integer.parseInt(pid);
                metrics.method = method;
                metrics.session = Integer.parseInt(session);
                metrics.dataSet = Integer.parseInt(dataSet);
                metrics.targetMovie = metrics.getFirstTargetMovie(); // must set!

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}