package umidity.cli;

import umidity.Debugger;
import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.cli.frames.*;
import umidity.cli.frames.eventHandlers.*;
import umidity.cli.frames.*;
import umidity.cli.frames.forms.*;
import umidity.cli.frames.forms.formEvents.WaitEvent;

public class MainCli extends FrameManager implements InputFormListener {
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    protected boolean close;

    public MainCli(){
        TextInput txtInput = new TextInput();
        txtInput.setName("input");
        txtInput.setText(">");
        txtInput.setVisibility(true);
        txtInput.disable();

        ScreenText title = new ScreenText();
        title.setName("title");
        title.setText("Umidity");

        ScreenMenu menu = new ScreenMenu();
        menu.setName("menu");
        menu.setVisibility(false);

        ScreenText message = new ScreenText();
        message.setName("message");
        message.setText("Benvenuuto in Umidity!");
        message.addEvent(new WaitEvent(2000));



        close = false;
        frames.add(new Frame()
                .addForm(title)
                .addForm(message)
                .addForm(txtInput));
    }

    public void run(){
        while(!close)
        {
            refresh();
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        ((TextInput)getFrame().getForm("input")).openStream();
    }

    @Override
    public void onSubmit(Object obj, InputFormArgument arg) {
        Debugger.println(arg.getMessage());
    }

    @Override
    public void onNotValidCharacter(Object obj, InputFormArgument arg) {
        Debugger.println(arg.getMessage());
    }
}
