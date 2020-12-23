package com.umidity.cli.frames.forms.formEvents;

public interface Callable {
    Object getArg();
    void call(Object arg) throws InterruptedException;
}
