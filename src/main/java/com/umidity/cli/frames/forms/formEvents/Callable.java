package com.umidity.cli.frames.forms.formEvents;

/**
 * Interface for Callable classes, used for forms event.
 * this interface provides a method "call" which is used by the classes that extend Form abstract class for the execution
 * of cuustom events.
 */
public interface Callable {
    /**
     * get argument passed to the method
     * @return
     */
    Object getArg();

    /**
     * method to execute when this event is needed
     * @throws InterruptedException
     */
    void call() throws InterruptedException;
}
