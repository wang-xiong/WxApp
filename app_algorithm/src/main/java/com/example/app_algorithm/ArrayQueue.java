package com.example.app_algorithm;

public class ArrayQueue<T> {

    Object[] array;
    private int front;
    private int last;


    public ArrayQueue() {
        this(10);
    }

    public ArrayQueue(int size) {
        array = new Object[size];
        front = 0;
        last = 0;
    }

    public boolean push(T object) {
        if ((last + 1) % array.length == front) {
            return false;
        }
        array[last] = object;
        last = (last + 1) % array.length;
        return true;
    }

    public T poll() {
        if (last == front) {
            return null;
        }
        front = (front + 1) % array.length;
        return (T) array[front];
    }




}
