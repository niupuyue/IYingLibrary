package com.niupuyue.mylibrary.widgets.chatkeyboard.models;

/**
 * 表情model
 */
public class IMExpressionModel {

    private String name;

    private int id;

    public IMExpressionModel(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
