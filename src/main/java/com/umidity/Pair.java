package com.umidity;

/**
 * A Pair object contains a pair of objects. It is useful when two values are linked together but you don't need an entire
 * <em>Map</em>.
 * <em>Key</em> and <em>Value</em> notations are just symbolical, none is more important then the other.
 * @param <T>
 * @param <Y>
 */
public class Pair<T, Y>{
    /**
     * First object
     */
    private T key;
    /**
     * Second object
     */
    private Y value;

    /**
     * Create a new Pair object.
     * @param key first object.
     * @param value second object.
     */
    public Pair(T key, Y value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Create a new Pair object (key and value will be set to null).
     */
    public Pair() {
        this.key = null;
        this.value = null;
    }

    /**
     * Set key object.
     * @param key
     */
    public void setKey(T key){ this.key = key; }

    /**
     * Set value object.
     * @param value
     */
    public void setValue(Y value){ this.value = value; }

    /**
     * Get key object
     * @return
     */
    public T getKey(){ return key; }

    /**
     * Get value object
     * @return
     */
    public Y getValue(){ return value; }
}