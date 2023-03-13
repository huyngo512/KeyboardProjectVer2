package com.example.keyboardproject;

public class CustomButton {
    public String textButton;
    public float xButton;
    public float yButton;
    public int typeButton;
    public int levelButton;
    public String colorPressButton;
    public String colorDefaultButton;

    public CustomButton(String textButton, float xButton, float yButton, int typeButton, int levelButton, String colorPressButton, String colorDefaultButton) {
        this.textButton = textButton;
        this.xButton = xButton;
        this.yButton = yButton;
        this.typeButton = typeButton;
        this.levelButton = levelButton;
        this.colorPressButton = colorPressButton;
        this.colorDefaultButton = colorDefaultButton;
    }

    public String getTextButton() {
        return textButton;
    }

    public void setTextButton(String textButton) {
        this.textButton = textButton;
    }

    public float getxButton() {
        return xButton;
    }

    public void setxButton(float xButton) {
        this.xButton = xButton;
    }

    public float getyButton() {
        return yButton;
    }

    public void setyButton(float yButton) {
        this.yButton = yButton;
    }

    public int getTypeButton() {
        return typeButton;
    }

    public void setTypeButton(int typeButton) {
        this.typeButton = typeButton;
    }

    public int getLevelButton() {
        return levelButton;
    }

    public void setLevelButton(int levelButton) {
        this.levelButton = levelButton;
    }

    public String getColorPressButton() {
        return colorPressButton;
    }

    public void setColorPressButton(String colorPressButton) {
        this.colorPressButton = colorPressButton;
    }

    public String getColorDefaultButton() {
        return colorDefaultButton;
    }

    public void setColorDefaultButton(String colorDefaultButton) {
        this.colorDefaultButton = colorDefaultButton;
    }
}
