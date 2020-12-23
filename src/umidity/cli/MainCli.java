package umidity.cli;

import umidity.Debugger;
import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.cli.frames.*;
import umidity.cli.frames.eventHandlers.InputFormArgument;
import umidity.cli.frames.eventHandlers.InputFormListener;
import umidity.cli.frames.Frame;
import umidity.cli.frames.forms.InputForm;
import umidity.cli.frames.forms.ScreenText;
import umidity.cli.frames.forms.TextInput;;

public class MainCli extends FrameManager implements InputFormListener {
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EMode.JSON, EUnits.Metric);
    protected boolean close;

    public MainCli(){
        TextInput txtInput = new TextInput();
        txtInput.setName("input");
        txtInput.setText(">");
        txtInput.setVisibility(true);

        ScreenText title = new ScreenText();
        title.setName("title");
        title.setText("Umidity");

        ScreenText message = new ScreenText();
        message.setName("message");
        message.setText("FUNZIONA");


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
