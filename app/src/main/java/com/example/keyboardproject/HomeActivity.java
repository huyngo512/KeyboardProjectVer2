package com.example.keyboardproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class HomeActivity extends AppCompatActivity {

    AppCompatButton keyboardButton, instructButton;
    Dialog dialog;
    Animation scaleUp, scaleDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ExtensionMethod.hideNavigationBar(this);

        dialog = new Dialog(this);

        keyboardButton = findViewById(R.id.keyboard_button);
        instructButton = findViewById(R.id.instruct_button);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        keyboardButton.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                keyboardButton.startAnimation(scaleUp);
                Intent intent = new Intent(getApplicationContext(), KeyboardActivity.class);
                startActivity(intent);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                keyboardButton.startAnimation(scaleDown);
            }
            return true;
        });

        instructButton.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                instructButton.startAnimation(scaleUp);
                openDialog();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                instructButton.startAnimation(scaleDown);
            }
            return true;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ExtensionMethod.hideNavigationBar(this);
    }
    private void openDialog() {
        dialog.setContentView(R.layout.layout_instruct);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnOK = dialog.findViewById(R.id.btnClose);
        btnOK.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}