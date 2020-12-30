package com.umidity.cli;

import com.umidity.Debugger;
import com.umidity.Main;
import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.EUnits;
import com.umidity.cli.frames.Frame;
import com.umidity.cli.frames.FrameManager;
import com.umidity.cli.frames.eventHandlers.InputFormArgument;
import com.umidity.cli.frames.eventHandlers.InputFormListener;
import com.umidity.cli.frames.forms.ScreenMenu;
import com.umidity.cli.frames.forms.ScreenText;
import com.umidity.cli.frames.forms.TextInput;
import com.umidity.cli.frames.forms.formEvents.WaitEvent;
import com.umidity.cli.frames.forms.formEvents.WaitForInput;
import com.umidity.database.HumidityRecord;
import com.umidity.statistics.StatsCalculator;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is the main FrameManager for the CLI version of Umidity.<br>
 * The CLI version is still under development and therefore still not much optimized nor well documented.
 */
public class MainCli extends FrameManager implements InputFormListener {

    //region Properties
    protected ApiCaller caller = new ApiCaller("a8f213a93e1af4abd8aa6ea20941cb9b", EUnits.Metric);
    protected boolean close;
    protected ScreenMenu menu;
    protected TextInput input;
    protected ScreenText title;
    protected ScreenText message;
    protected boolean append;
    //endregion

    /**
     * Constructor for MainCli. Use <em>start()</em> to stat the CLI
     */
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
        message.setText("Welcome in Umidity 1.0-SNAPSHOT");
        message.addEvent(new WaitEvent(2000));


        append = false;
        close = false;
        frames.add(new Frame()
                .addForm(title)
                .addForm(message)
                .addForm(menu)
                .addForm(txtInput));

        this.path = "main";
        this.menu = ((ScreenMenu)getFrame().getForm("menu"));
        this.input = (getFrame().getForm("input"));
        this.title = (getFrame().getForm("title"));
        this.message = (getFrame().getForm("message"));
    }

    public void run(){
        while(!close)
        {
            super.refresh();
        }
    }

    //region Views
    private void changePath(){
        switch (path){
            case "main" -> mainMenu();
            case "currentByCityName" -> currentByCityName();
            case "forecastByCityName" -> forecastByCityName();
            case "toggleGui" -> {
                Main.userSettings.setGuiEnabled(!Main.userSettings.isGuiEnabled());
                Main.dbms.saveUserSettings();
                setPath("main"); }
            case "pastStatistics" -> pastStatistics();
            case "quit" -> this.close = true;
            default -> path = "main";
        }
    }

    private void mainMenu(){
        message.disable();
        menu.enable();
        menu.setVisibility(true);
        menu.clearMenuEntries();
        menu.setText("Select one of the following:");
        menu.addMenuEntry("Current humidity", "currentByCityName");
        menu.addMenuEntry("Forecast humidity", "forecastByCityName");
        menu.addMenuEntry((Main.userSettings.isGuiEnabled()?"Disable" : "Enable") + " GUI", "toggleGui");
        //menu.addMenuEntry("Get past humidity statistics (only for saved cities)", "pastStatistics");
        //menu.addMenuEntry("Get forecast humidity statistics (only for saved cities)", "forecastStatistics");
        menu.addMenuEntry("Close Umidity", "quit");
        menu.updateMenu();
        append = false;
        input.enable();
        if(input.isInput()){
            try{
                setPath(menu.getValue(Integer.parseInt(input.consumeInput())));
            }catch (NumberFormatException e){
            }
        }
    }
    private void currentByCityName(){
        menu.disable();
        message.enable();
        message.setVisibility(true);
        boolean next = false;

        append = true;
        input.enable();
        switch (input.getLength()){
            case 0 -> message.setText("Type city name: ");
            case 1 -> message.setText("Type country code(optional, you can leave this empty): ");
            case 2 -> message.setText("Type zip code(optional, you can leave this empty): ");
            case 3 -> next = true;
        }
        if(next){
            var inputs = input.getInputs();
            try {
                var r = caller.getByCityName(inputs[0], inputs[1], inputs[2]);
                String unit = (caller.getUnit().equals(EUnits.Metric)? "°C" : "°F");
                String text = "Humidity for %s, %s (%s) is: %02d%% \n Temperature: %03.02f" + unit +"\n Feels Like: %03.02f" + unit;
                message.setText(String.format(text, r.getCityName(),r.getCityCountry(),r.getCoord().toString(),r.getHumidity(), r.getTemp(), r.getFeels_like()));
            } catch (IOException e) {
                message.setText("Ooops, something went wrong :( ... Maybe you typed the wrong city?");
            }finally {
                message.addEvent(new WaitForInput());
                input.disable();
                setPath("");
            }
        }
    }
    private void forecastByCityName(){
        menu.disable();
        message.enable();
        message.setVisibility(true);
        boolean next = false;

        append = true;
        input.enable();
        switch (input.getLength()){
            case 0 -> message.setText("Type city name: ");
            case 1 -> message.setText("Type country code(optional, you can leave this empty): ");
            case 2 -> message.setText("Type zip code(optional, you can leave this empty): ");
            case 3 -> next = true;
        }
        if(next){
            var inputs = input.getInputs();
            try {
                var r = caller.getByCityName(inputs[0], inputs[1], inputs[2]);
                String unit = (caller.getUnit().equals(EUnits.Metric)? "°C" : "°F");
                String text = "Forecast for %s, %s (%s) is: \n";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh-mm");
                boolean first =true;
                for(var h:caller.getForecastByCityName(inputs[0], inputs[1], inputs[2]).getSingles()){
                    if(first){
                        first =false;
                        text = String.format(text, r.getCityName(),r.getCityCountry(),r.getCoord().toString());
                    }
                    String pre = "(%s) Humidity: %02d%% ~ Temperature: %03.02f \n";
                    text += String.format(pre, format.format(new Date(new Timestamp(h.getTimestamp()*1000).getTime())), h.getHumidity(), h.getTemp());
                }
                message.setText(text);
            } catch (IOException e) {
                message.setText("Ooops, something went wrong :( ... Maybe you typed the wrong city?");
            }finally {
                message.addEvent(new WaitForInput());
                input.disable();
                setPath("");
            }
        }
    }
    private void pastStatistics(){
        message.disable();
        menu.enable();
        append = true;
        menu.clearMenuEntries();
        if(input.getLength() >= 0){
            menu.setText("Select which city's statistics you want to see: ");
            for(var c:Main.dbms.getCities()){
                menu.addMenuEntry(c.getName(), String.valueOf(c.getId()));
            }
            if(input.isInput())
                menu.addHistory(input.getInputs()[0]);
        }if(input.getLength() > 1){
            menu.clearMenuEntries();
            menu.addMenuEntry("Last 7 days", "last7Days");
            menu.addMenuEntry("Last 30 days", "last30Days");
            //menu.addMenuEntry("Custom range", "customRange");
            if(input.isInput()){
                if(menu.getValue(Integer.parseInt(input.getInputs()[1])).equals("customRange")){
                    //menu.setText("Type range (dd-MM-yyyy,dd-MM-yyyy):");
                }if(menu.getValue(Integer.parseInt(input.getInputs()[1])).equals("last7Days")){
                    var record =
                            Main.dbms.getHumidity(Integer.parseInt(menu.getValue(Integer.parseInt(menu.getHistory()[0]))));
                    menu.disable();
                    message.enable();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -7);
                    Date fromDate = cal.getTime();
                    Date toDate = new Date();
                    double avg = StatsCalculator.avg(record, fromDate, toDate);
                    HumidityRecord min = StatsCalculator.min(record, fromDate, toDate);
                    HumidityRecord max = StatsCalculator.max(record, fromDate, toDate);

                    String text = "average: " + avg + "% \n" +
                            "min: " + min.getHumidity() + "% in date " + new Date(min.getTimestamp()) + "\n" +
                            "max: " + max.getHumidity() + "% in date " + new Date(max.getTimestamp()) + "\n";
                    message.setText(text);
                    message.addEvent(new WaitForInput());
                    setPath("");
                }if(menu.getValue(Integer.parseInt(input.getInputs()[1])).equals("last30Days")){
                    var record =
                            Main.dbms.getHumidity(Integer.parseInt(menu.getValue(Integer.parseInt(menu.getHistory()[0]))));
                    menu.disable();
                    message.enable();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -30);
                    Date fromDate = cal.getTime();
                    Date toDate = new Date();
                    double avg = StatsCalculator.avg(record, fromDate, toDate);
                    HumidityRecord min = StatsCalculator.min(record, fromDate, toDate);
                    HumidityRecord max = StatsCalculator.max(record, fromDate, toDate);

                    String text = "average: " + avg + "% \n" +
                            "min: " + min.getHumidity() + "% in date " + new Date(min.getTimestamp()) + "\n" +
                            "max: " + max.getHumidity() + "% in date " + new Date(max.getTimestamp()) + "\n";
                    message.setText(text);
                    message.addEvent(new WaitForInput());
                    setPath("");
                }
            }
        }

        menu.updateMenu();
        append = true;
        input.enable();



    }
    //endregion

    //region Events
    @Override
    public void onSubmit(Object obj, InputFormArgument arg) {
        Debugger.println(arg.getMessage());
    }
    @Override
    public void onNotValidCharacter(Object obj, InputFormArgument arg) {
        Debugger.println(arg.getMessage());
    }
    //endregion

    /**
     * Overrides beforeUpdate operations
     */
    @Override
    protected void beforeUpdate(){
        title.enable();
        title.setVisibility(true);
    }
    /**
     * Overrides afterUpdate operations
     */
    @Override
    protected void afterUpdate(){
        if(!append) input.openStream();
        else input.appendStream();
        changePath();
    }

    /**
     * Set new path for the next iteration
     * @param newPath
     */
    @Override
    public void setPath(String newPath){
        super.setPath(newPath);
        changePath();
    }

}
