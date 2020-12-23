package umidity.cli.frames.forms.formEvents;

public class WaitEvent implements Callable {

    long arg;

    public WaitEvent(long arg){
        this.arg = arg;
    }

    @Override
    public Object getArg() {
        return arg;
    }

    /**
     * When no input is required, it is useful to let the form be visible for a custom ammount of time to the user
     * @param arg long value for milliseconds to wait
     * @throws InterruptedException
     */
    @Override
    public void call(Object arg) throws InterruptedException {
        Thread.sleep((long)arg);
    }
}
