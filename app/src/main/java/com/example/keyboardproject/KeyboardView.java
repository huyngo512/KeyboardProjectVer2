package com.example.keyboardproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyboardView extends View {

    public Paint paint;
    public int radius = 110;

    private SparseArray<PointF> mActivePointers;

    public float x = 160, y = 280;

    public boolean check = false;

    ArrayList<CustomButton> listButton = new ArrayList<>();

    ArrayList<HashMap<String, Integer>> rules;

    public String text = "";



    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        rules = ExtensionMethod.getRules();
        paint = new Paint();
        listButton.add(new CustomButton("S", (x + radius), (float) (y - radius * Math.sqrt(3)), 0, 0, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("P", (x + 3 * radius), (float) (y - radius * Math.sqrt(3)), 0, 1, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("T", x, y, 0, 2, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("Q", (x + 2 * radius), y, 0, 3, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("R", (x + 4 * radius), y, 0, 4, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("K", (x + radius), (float) (y + radius * Math.sqrt(3)), 0, 5, "#DC143C", "#F08080"));
        listButton.add(new CustomButton("H", (x + 3 * radius), (float) (y + radius * Math.sqrt(3)), 0, 6, "#DC143C", "#F08080"));

        x += (6.5) * radius;
        listButton.add(new CustomButton("N", (x + radius), (float) (y - radius * Math.sqrt(3)), 2, 0, "#006400", "#20B2AA"));
        listButton.add(new CustomButton("T", (x + 3 * radius), (float) (y - radius * Math.sqrt(3)), 2, 1, "#006400", "#20B2AA"));
        listButton.add(new CustomButton("J", x, y, 2, 2, "#006400", "#20B2AA"));
        listButton.add(new CustomButton("G", (x + 2 * radius), y, 2, 3, "#006400", "#20B2AA"));
        listButton.add(new CustomButton("K", (x + 4 * radius), y, 2, 4, "#006400", "#20B2AA"));

        x += (6.5) * radius;
        listButton.add(new CustomButton("I", (x + radius), (float) (y - radius * Math.sqrt(3)), 1, 0, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("W", (x + 3 * radius), (float) (y - radius * Math.sqrt(3)), 1, 1, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("U", x, y, 1, 2, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("Y", (x + 2 * radius), y, 1, 3, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("A", (x + 4 * radius), y, 1, 4, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("O", (x + radius), (float) (y + radius * Math.sqrt(3)), 1, 5, "#1E90FF", "#87CEFA"));
        listButton.add(new CustomButton("E", (x + 3 * radius), (float) (y + radius * Math.sqrt(3)), 1, 6, "#1E90FF", "#87CEFA"));

        x -= (6.5) * radius;
        listButton.add(new CustomButton("N", (x + radius), (float) (y + radius * Math.sqrt(3)), 3, 0, "#FFFF00", "#F0E68C"));
        listButton.add(new CustomButton("H", (x + 3 * radius), (float) (y + radius * Math.sqrt(3)), 3, 1, "#FFFF00", "#F0E68C"));
        listButton.add(new CustomButton("S", (x + 2 * radius), (float)(y+2*radius*Math.sqrt(3)), 3, 2, "#FFFF00", "#F0E68C"));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskAction = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (maskAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f);
                ArrayList<Integer> arrayPointerDownList = getTouchButtons(f.x, f.y);
                if (arrayPointerDownList.size() != 0 && !check) {
                    check = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(check) {
                    PointF pointF = mActivePointers.get(pointerId);
                    ArrayList<Integer> arrayUpList = getTouchButtons(pointF.x, pointF.y);
                    if (arrayUpList.size() != 0) {
                        //System.out.println(getTextPressButton());
                        //textView.append(getTextPressButton() + " ");
                        text = getTextPressButton();
                        check = false;
                    }
                }
                mActivePointers.remove(pointerId);
                break;

            case MotionEvent.ACTION_CANCEL:
                mActivePointers.remove(pointerId);
                break;
        }
        invalidate();
        return true;
    }

    public boolean isCheck() {
        return check;
    }

    public String getText() {
        return text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetButtons(canvas);
        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);
            if (point != null) {
                ArrayList<Integer> listID = getTouchButtons(point.x, point.y);
                for (int j = 0; j < listID.size(); j++) {
                    CustomButton customButton = listButton.get(listID.get(j));
                    setPressButton(customButton, canvas);
                }
            }
        }
    }


    private ArrayList<Integer> getTouchButtons(float x, float y) {
        ArrayList<Integer> idArray = new ArrayList<>();
        for (int i = 0; i < listButton.size(); i++) {
            float distance = (float) Math.sqrt(Math.pow(listButton.get(i).getxButton() + radius - x, 2) + Math.pow(listButton.get(i).getyButton() + radius - y, 2));
            if (distance <= (float) 3 * radius / 2) {
                idArray.add(i);
            }
        }
        return idArray;
    }

    private void resetButtons(Canvas canvas) {
        for (CustomButton button : listButton) {
            paint.setColor(Color.parseColor(button.getColorDefaultButton()));
            canvas.drawCircle(button.getxButton() + radius, button.getyButton() + radius, radius, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(45);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(button.getTextButton(), button.getxButton() + radius, button.getyButton() + radius + 15, paint);
        }
    }

    private void setPressButton(CustomButton button, Canvas canvas) {
        paint.setColor(Color.parseColor(button.getColorPressButton()));
        canvas.drawCircle(button.getxButton() + radius, button.getyButton() + radius, radius, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(45);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(button.getTextButton(), button.getxButton() + radius, button.getyButton() + radius + 15, paint);
    }

    public String getTextPressButton() {
        String keyword = "";
        ArrayList<Integer> listID = new ArrayList<>();
        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);
            if (point != null) {
                ArrayList<Integer> listIdTouch = getTouchButtons(point.x, point.y);
                for (int j = 0; j < listIdTouch.size(); j++) {
                    if (!listID.contains(listIdTouch.get(j))) {
                        listID.add(listIdTouch.get(j));
                    }
                }
            }
        }

        String[] word = {"", "", "", ""};
        for (int i = 0; i < listID.size(); i++) {
            CustomButton customButton = listButton.get(listID.get(i));
            int typeWord = customButton.getTypeButton();
            if (word[typeWord].equals("")) {
                word[typeWord] = word[typeWord].concat(customButton.getTextButton());
            } else {
                char[] tmp = word[typeWord].toCharArray();
                if (rules.get(typeWord).get(Character.toString(tmp[tmp.length - 1])) < rules.get(typeWord).get(customButton.getTextButton())) {
                    word[typeWord] = word[typeWord].concat(customButton.getTextButton());

                } else {
                    for (int j = 0; j < tmp.length; j++) {
                        if (rules.get(typeWord).get(Character.toString(tmp[j])) > rules.get(typeWord).get(customButton.getTextButton())) {
                            word[typeWord] = new StringBuilder(word[typeWord]).insert(j, customButton.getTextButton()).toString();
                            break;
                        }
                    }
                }
            }
        }
        keyword = word[0].concat("-").concat(word[3]).concat("-").concat(word[1]).concat("-").concat(word[2]);
        return keyword;
    }

}
