package com.example.app_algorithm;

public class ListNote {
    private int value;
    private ListNote next;

    public ListNote(int value) {
        this.value = value;
    }

    public ListNote getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setNext(ListNote next) {
        this.next = next;
    }
}
