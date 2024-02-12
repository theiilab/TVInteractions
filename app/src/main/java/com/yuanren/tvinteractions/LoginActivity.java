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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yuanren.tvinteractions.log.Session;
import com.yuanren.tvinteractions.model.MovieList;

public class LoginActivity extends AppCompatActivity {
    private EditText participantET;
    private EditText sessionET;
    private ToggleButton methodBtn;
    private Button button;
    private LinearLayout layout4;
    private ToggleButton dataSetBtn;
    private EditText blockNumET;

    /** ----- log ----- */
    String pid = "0";
    String sid = "0";
    String method = "Remote";
    String dataSet = "0";
    String bid = "1";
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
        blockNumET = findViewById(R.id.block_num);

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
                    sid = v.getText().toString();
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
                dataSetBtn.requestFocus();
            }
        });

        dataSetBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataSet = isChecked ? "1" : "0";
                blockNumET.requestFocus();
            }
        });

        blockNumET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        blockNumET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    bid = v.getText().toString();
                    button.requestFocus();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                MovieList.init(getApplicationContext());
                MovieList.setUpMovies();

                /** -------- log -------- */
                Session session = (Session) getApplicationContext();

                if (bid.equals("1")) {
                    session.init(Integer.parseInt(pid), Integer.parseInt(sid), method, Integer.parseInt(dataSet), MovieList.randomPositions);
                } else {
                    session.init(Integer.parseInt(pid), Integer.parseInt(sid), method, Integer.parseInt(dataSet), MovieList.randomPositions, Integer.parseInt(bid));
                }
                /** --------------------- */

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}