package com.example.app_algorithm;

import java.util.Stack;

public class StackQueue<T> {

    private Stack<T> in = new Stack<>();
    private Stack<T> out = new Stack<>();


    public void push(T value) {
        in.push(value);
    }

    public T poll() {
        if (in.isEmpty() && out.isEmpty()) {
            return null;
        }
        if (!out.isEmpty()) {
            return out.pop();
        } else {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
            return out.pop();
        }
    }
}
