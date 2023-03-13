package com.example.keyboardproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtensionMethod {

    public static void hideNavigationBar(Context context) {
        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    public static ArrayList<HashMap<String, Integer>> getRules() {
        ArrayList<HashMap<String, Integer>> rules = new ArrayList<>();

        HashMap<String, Integer> amdau, amchinh, amcuoi, amdieu;

        amdau = new HashMap<>();
        amchinh = new HashMap<>();
        amcuoi = new HashMap<>();
        amdieu = new HashMap<>();

        amdau.put("S", 1);
        amdau.put("T", 2);
        amdau.put("K", 3);
        amdau.put("H", 4);
        amdau.put("R", 5);
        amdau.put("P", 6);
        amdau.put("Q", 7);

        rules.add(amdau);

        amchinh.put("I", 1);
        amchinh.put("U", 2);
        amchinh.put("O", 3);
        amchinh.put("E", 4);
        amchinh.put("A", 5);
        amchinh.put("W", 6);
        amchinh.put("Y", 7);

        rules.add(amchinh);

        amcuoi.put("J", 1);
        amcuoi.put("N", 2);
        amcuoi.put("G", 3);
        amcuoi.put("T", 4);
        amcuoi.put("K", 5);

        rules.add(amcuoi);

        amdieu.put("N", 1);
        amdieu.put("H", 2);
        amdieu.put("S", 3);

        rules.add(amdieu);

        return rules;
    }

}
