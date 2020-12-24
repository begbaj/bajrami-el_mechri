package com.umidity;

public class Pair<T, Y>{
    private T key;
    private Y value;


    public Pair(T key, Y value) {
        this.key = key;
        this.value = value;
    }

    public Pair() {
        this.key = null;
        this.value = null;
    }

    public void setKey(T key){ this.key = key; }
    public void setValue(Y value){ this.value = value; }

    public T getKey(){ return key; }
    public Y getValue(){ return value; }
}