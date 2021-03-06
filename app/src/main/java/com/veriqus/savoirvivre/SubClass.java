package com.veriqus.savoirvivre;

/**
 * Created by krzysztofmarczewski on 28.11.2017.
 */

public class SubClass {
    private String name;
    private int drawable;
    private boolean isDone;

    public SubClass(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public SubClass(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void setDone() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }
    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }
}
