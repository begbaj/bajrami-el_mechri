package com.umidity.cli.frames.forms.formEvents;

import java.io.IOException;

public class WaitForInput implements Callable{
    @Override
    public Object getArg() {
        return null;
    }

    @Override
    public void call() throws InterruptedException {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
