package com.umidity.cli.frames.forms.formEvents;

/**
 * Make the form wait 'till milliseconds in argument has passed. This class is an implementation of Callable interface
 * and for this it has a method "call" which will be called by the form in which this event has been queued.
 */
public class WaitEvent implements Callable {

    /**
     * Milliseconds to wait
     */
    long arg;

    /**
     * Make the form wait till milliseconds in argument has passed
     * @param arg
     */
    public WaitEvent(long arg){
        this.arg = arg;
    }

    @Override
    public Object getArg() {
        return arg;
    }

    /**
     * When no input is required, it is useful to let the form be visible for a custom ammount of time to the user
     * @throws InterruptedException
     */
    @Override
    public void call() throws InterruptedException {
        Thread.sleep((long)arg);
    }
}
