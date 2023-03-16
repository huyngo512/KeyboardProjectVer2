package com.example.keyboardproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class KeyboardActivity extends AppCompatActivity implements View.OnTouchListener {

    KeyboardView keyboardView;

    TextView textView, displayView;

    SharedPreferences sharedPreferences_amdau_rule, sharedPreferences_amchinh_rule, sharedPreferences_amcuoi_rule, sharedPreferences_tailieu_rule,
            sharedPreferences_amdau_data, sharedPreferences_amchinh_data, sharedPreferences_amcuoi_data, sharedPreferences_tailieu_data,
            sharedPreferences_amdau_code, sharedPreferences_amchinh_code, sharedPreferences_amcuoi_code, sharedPreferences_tailieu_code;


    public boolean check = false;


    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        ExtensionMethod.hideNavigationBar(this);

        textView = findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());
        displayView = findViewById(R.id.display_view);
        keyboardView = findViewById(R.id.keyboard_view);
        keyboardView.setOnTouchListener(this);
        readFile();
        readFileRaw("amdau");
        readFileRaw("amchinh");
        readFileRaw("amcuoi");

        sharedPreferences_amdau_rule = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amdau", Context.MODE_PRIVATE);
        sharedPreferences_amchinh_rule = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amchinh", Context.MODE_PRIVATE);
        sharedPreferences_amcuoi_rule = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amcuoi", Context.MODE_PRIVATE);
        sharedPreferences_tailieu_rule = getSharedPreferences(getApplicationContext().getPackageName() + "." + "dictionary", Context.MODE_PRIVATE);

        sharedPreferences_amdau_data = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amdau.data", Context.MODE_PRIVATE);
        sharedPreferences_amchinh_data = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amchinh.data", Context.MODE_PRIVATE);
        sharedPreferences_amcuoi_data = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amcuoi.data", Context.MODE_PRIVATE);
        sharedPreferences_tailieu_data = getSharedPreferences(getApplicationContext().getPackageName() + ".dictionary.data", Context.MODE_PRIVATE);

        sharedPreferences_amdau_code = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amdau.code", Context.MODE_PRIVATE);
        sharedPreferences_amchinh_code = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amchinh.code", Context.MODE_PRIVATE);
        sharedPreferences_amcuoi_code = getSharedPreferences(getApplicationContext().getPackageName() + "." + "amcuoi.code", Context.MODE_PRIVATE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        ExtensionMethod.hideNavigationBar(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        keyboardView.onTouchEvent(event);
        String text = keyboardView.getText();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                check = true;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (check && !text.equals("")) {
                    displayView.setText(text);
                    String keyword = sharedPreferences_tailieu_rule.getString(text, "");
                    if(!keyword.equals("")){
                        textView.append(keyword + " ");
                    }
                    check = false;
                }
                break;
        }
        return true;
    }

    public void readFileRaw(String document) {
        BufferedReader reader;
        SharedPreferences rulesPreferences, dataPreferences, codePreferences;
        rulesPreferences = getSharedPreferences(getApplicationContext().getPackageName() + "." + document, Context.MODE_PRIVATE);
        dataPreferences = getSharedPreferences(getApplicationContext().getPackageName() + "." + document + ".data", Context.MODE_PRIVATE);
        codePreferences = getSharedPreferences(getApplicationContext().getPackageName() + "." + document + ".code", Context.MODE_PRIVATE);
        SharedPreferences.Editor rulesEditor, dataEditor, codeEditor;

        rulesEditor = rulesPreferences.edit();
        dataEditor = dataPreferences.edit();
        codeEditor = codePreferences.edit();

        try {
            final InputStream file = getResources().openRawResource(getResources().getIdentifier(document, "raw", getPackageName()));
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            int i = 1;
            line = reader.readLine();
            while (line != null) {
                String[] part = line.split("\t");
                rulesEditor.putString(part[1], part[0]);
                dataEditor.putString(String.valueOf(i), part[0]);
                codeEditor.putString(String.valueOf(i), part[1]);
                i++;
                line = reader.readLine();
            }
            rulesEditor.apply();
            dataEditor.apply();
            codeEditor.apply();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void readFile() {
        BufferedReader reader;
        SharedPreferences sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName() + ".dictionary", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences_data = getSharedPreferences(getApplicationContext().getPackageName() + ".dictionary.data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor_data = sharedPreferences_data.edit();
        try {
            final InputStream file = getResources().openRawResource(getResources().getIdentifier("tailieu", "raw", getPackageName()));
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            int i = 1;
            line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String [] part = line.split("\t");
                editor.putString(part[0], part[1]);
                editor_data.putString(String.valueOf(i), line);
                i++;
                line = reader.readLine();
            }
            editor.apply();
            editor_data.apply();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}