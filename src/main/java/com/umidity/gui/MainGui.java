package com.umidity.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.umidity.Coordinates;
import com.umidity.Debugger;
import com.umidity.Main;
import com.umidity.api.Single;
import com.umidity.api.caller.ApiArgument;
import com.umidity.api.caller.ApiListener;
import com.umidity.api.response.ForecastResponse;
import com.umidity.api.response.OneCallHistoricalResponse;
import com.umidity.database.CityRecord;
import com.umidity.database.HumidityRecord;
import com.umidity.database.RecordsListener;
import com.umidity.statistics.StatsCalculator;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

/**
 * This class handles the MainFrame GUI
 */
public class MainGui implements ApiListener, RecordsListener {
    //region Components
    public JPanel panelMain;
    private JTextField textField_City;
    private JButton searchButton;
    private JTextField textField_ZIP;
    private JTextField textField_State;
    private JTable recordsTable;
    private JTable statisticsTable;
    private JComboBox timeStatsBox;
    private JButton settingsbutton;
    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;
    private JLabel cityLabel;
    private JLabel nosuchLabel;
    private JLabel enoughLabel;
    private JCheckBox saveCityRecordsCheckBox;
    private JCheckBox setFavouriteCityCheckBox;
    private JPanel statisticPanel;
    private JPanel BigPanel;
    private JLabel label1;
    private JToolBar toolbar;
    private JButton simpleGraphButton;
    private JButton recordsGraphButton;
    private JLabel easterEggLabel;
    private JLabel timeWarningLabel;
    private JButton getLast5DaysButton;
    private ChartPanel chartPanel;
    private ChartPanel chartRecordsPanel;
    //endregion

    boolean listenerOn;
    Single realtimeResponse;
    Vector<String> recordColumnNames;
    Vector<String> statisticsColumnNames;
    DecimalFormatSymbols otherSymbols;
    DecimalFormat df;
    JDatePanelImpl datePanelFrom;
    JDatePanelImpl datePanelTo;
    SimpleDateFormat format;

    /**
     * MainGui constructor, components listeners are defined in here
     */
    public MainGui(){

        statisticsColumnNames =new Vector<>(Arrays.asList("Min", "Max", "Avg", "Variance"));
        recordColumnNames =new Vector<>(Arrays.asList("DateTime", "Temperature", "Humidity"));

        listenerOn = true;

        otherSymbols=new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        df = new DecimalFormat("###.##", otherSymbols);
        format = new SimpleDateFormat("dd-MM HH:00");

        changeTheme(Main.userSettings.getGuiTheme());
        SwingUtilities.updateComponentTreeUI(panelMain);

        updateTable(recordsTable, null, recordColumnNames);
        updateTable(statisticsTable, null, statisticsColumnNames);

        searchButton.addActionListener((e) -> {
            try {
                nosuchLabel.setText("");
                if(!textField_City.getText().equals(""))
                    realtimeResponse = new Single(Main.caller.getByCityName(textField_City.getText(), textField_State.getText(), textField_ZIP.getText()));
                else if(!textField_ZIP.getText().equals(""))
                    realtimeResponse= new Single(Main.caller.getByZipCode(textField_ZIP.getText(), textField_State.getText()));
                else nosuchLabel.setText("You must specify the area!");

                if(!nosuchLabel.getText().equals("You must specify the area!")) {
                    Vector<Vector<String>> matrix = new Vector();
                    Vector<String> firstRow = new Vector();
                    String degrees;
                    switch(Main.caller.getUnit()){
                        case Metric -> degrees= "C°";
                        case Imperial -> degrees="F°";
                        default -> degrees="K°";
                    }
                    firstRow.add(format.format(new Date(realtimeResponse.getTimestamp()*1000)));
                    firstRow.add(df.format(realtimeResponse.getTemp())+ " " + degrees);
                    firstRow.add(df.format(realtimeResponse.getHumidity()) + "%");
                    matrix.add(firstRow);

                    ForecastResponse forecastResponse = Main.caller.getForecastByCityName(realtimeResponse.getCityName(), realtimeResponse.getCityCountry(), textField_ZIP.getText());
                    Single[] forecastRecords = forecastResponse.getSingles();
                    for (int counter = 0; counter < forecastRecords.length; ++counter) {
                        Single f_record = forecastRecords[counter];
                        Date datetime = new Date((new Timestamp(f_record.getTimestamp() * 1000)).getTime());
                        String datetimeString = format.format(datetime);
                        Vector<String> nextRow = new Vector();

                        nextRow.add(datetimeString);
                        nextRow.add(df.format(f_record.getTemp())+ " " + degrees);
                        nextRow.add((df.format(f_record.getHumidity()) + "%"));
                        matrix.add(nextRow);
                    }

                    updateTable(recordsTable, matrix, recordColumnNames);
                    cityLabel.setText(realtimeResponse.getCityName().toUpperCase()+ ", " + realtimeResponse.getCityCountry().toUpperCase());
                    CityRecord city = new CityRecord(realtimeResponse.getCityId(), realtimeResponse.getCityName(), realtimeResponse.getCoord());
                    listenerOn = false;
                    if (Main.dbms.cityIsSaved(city)) {
                        saveCityRecordsCheckBox.setSelected(true);
                        timeStatsBox.setSelectedIndex(0);
                        setPanelEnabled(statisticPanel, true);
                    } else {
                        saveCityRecordsCheckBox.setSelected(false);
                        updateTable(statisticsTable, null, statisticsColumnNames);
                        setPanelEnabled(statisticPanel, false);
                    }

                    setFavouriteCityCheckBox.setSelected(Main.dbms.getFavouriteCity().getId() == city.getId());
                    listenerOn = true;
                }
            } catch (FileNotFoundException ex) {
                nosuchLabel.setText("Can't find any area with such parameters");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Invalid API key! Please change it in the settings.");
            }
        });

        settingsbutton.addActionListener((e) -> {
            SettingsFrame settingsGui = new SettingsFrame();
        });

        timeStatsBox.addActionListener((e) -> {
            Date[] dates=getDateRange();
            if(dates[0]!=null && dates[1]!=null && dates[0].before(dates[1]))
                createStatistic(dates[0], dates[1]);
        });

        datePickerFrom.addActionListener((e) -> {
            try {
                if (((Date) datePickerFrom.getModel().getValue()).before((Date) datePickerTo.getModel().getValue())) {
                    if (timeStatsBox.getSelectedIndex() == 2) {
                        timeStatsBox.setSelectedIndex(2);
                    }
                    easterEggLabel.setText("");
                    timeWarningLabel.setText("");
                } else {
                    easterEggLabel.setText("Time in Lordran is convoluted, but we ain't there.");
                    timeWarningLabel.setText("Invalid Date range!");
                }
            }catch (Exception ex){
            }
        });

        datePickerTo.addActionListener((e) -> {
            try {
                if (((Date) datePickerFrom.getModel().getValue()).before((Date) datePickerTo.getModel().getValue())) {
                    if (timeStatsBox.getSelectedIndex() == 2) {
                        timeStatsBox.setSelectedIndex(2);
                    }
                    easterEggLabel.setText("");
                    timeWarningLabel.setText("");
                } else {
                    easterEggLabel.setText("Time in Lordran is convoluted, but we ain't there.");
                    timeWarningLabel.setText("Invalid Date range!");
                }
            }catch (Exception ex){
            }

        });

        saveCityRecordsCheckBox.addActionListener((e) -> {
            if (listenerOn) {
                try {
                    CityRecord city = new CityRecord(realtimeResponse.getCityId(), realtimeResponse.getCityName(), realtimeResponse.getCoord());
                    if (saveCityRecordsCheckBox.isSelected()) {
                        boolean flag = Main.dbms.addCity(city);
                        if (flag) {
                            nosuchLabel.setText("City added!");
                            searchButton.doClick();
                            setPanelEnabled(statisticPanel, true);
                            timeStatsBox.setSelectedIndex(0);
                            updateAsynCaller();
                        } else {
                            System.out.println("SOMETHING WRONG");
                        }
                    } else if (JOptionPane.showConfirmDialog(panelMain, "Remove city and delete all its records?", "Message", 0) == 0) {
                        Main.dbms.removeCity(city);
                        nosuchLabel.setText("City removed!");
                        updateTable(statisticsTable, null, statisticsColumnNames);
                        setPanelEnabled(statisticPanel, false);
                        updateAsynCaller();
                    } else {
                        listenerOn = false;
                        saveCityRecordsCheckBox.setSelected(true);
                        listenerOn = true;
                    }
                } catch (Exception ex) {
                    nosuchLabel.setText("You have to search for an area first");
                    listenerOn = false;
                    saveCityRecordsCheckBox.setSelected(false);
                    listenerOn = true;
                }
            }

        });

        setFavouriteCityCheckBox.addActionListener((e) -> {
            if (listenerOn) {
                try {
                    if (setFavouriteCityCheckBox.isSelected()) {
                        Main.dbms.setFavouriteCity(new CityRecord(realtimeResponse.getCityId(), realtimeResponse.getCityName(), realtimeResponse.getCoord()));
                    } else {
                        Main.dbms.setFavouriteCity(new CityRecord(-1, "", new Coordinates(-1.0F, -1.0F)));
                    }
                }catch (Exception ex){
                    nosuchLabel.setText("You have to search for an area first");
                    listenerOn = false;
                    setFavouriteCityCheckBox.setSelected(false);
                    listenerOn = true;
                }
            }

        });

        simpleGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd=new DefaultCategoryDataset();
                List<HumidityRecord> records = Main.dbms.getHumidity(realtimeResponse.getCityId());
                Date[] dates=getDateRange();
                double min = StatsCalculator.min(records, dates[0], dates[1]).getHumidity();
                double max = StatsCalculator.max(records, dates[0], dates[1]).getHumidity();
                double avg = StatsCalculator.avg(records, dates[0], dates[1]);
                dcd.setValue(min, "Humidity", "Min");
                dcd.setValue(max, "Humidity", "Max");
                dcd.setValue(avg, "Humidity", "Avg");

                createGraph("Humidity Simple Graph", new Dimension(400, 400), dcd, false);
            }
        });

        recordsGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd=new DefaultCategoryDataset();
                List<HumidityRecord> records = Main.dbms.getHumidity(realtimeResponse.getCityId());
                for(HumidityRecord record:records){
                    dcd.addValue(record.getHumidity(), "Humidity", new Date((new Timestamp(record.getTimestamp() * 1000)).getTime()));
                }

                createGraph("Humidity Records Graph", new Dimension(1000, 400), dcd, true);
            }
        });

        getLast5DaysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(){
                    public void run(){
                        Calendar cal = Calendar.getInstance();
                        try {
                            if(realtimeResponse!=null){
                            List<HumidityRecord> records=new ArrayList<>();
                            CityRecord city=new CityRecord(realtimeResponse.getCityId(), realtimeResponse.getCityName(), realtimeResponse.getCoord());
                            for(int i=0; i<6; i++){
                                OneCallHistoricalResponse response= Main.caller.oneCall(realtimeResponse.getCoord().lat, realtimeResponse.getCoord().lon, cal.getTime().getTime()/1000);
                                for(var x:response.hourly){
                                    records.add(new HumidityRecord(x.humidity, x.dt, city));
                                }
                                cal.add(5, -1);
                            }
                            Main.dbms.addHumidity(records);
                            }else{
                                nosuchLabel.setText("You have to search for an area first");
                            }
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(null, "Something went wrong. Please retry.");
                        }
                        timeStatsBox.setSelectedIndex(0);
                    }
                };

                thread.start();
            }
        });

        /**
         * favourite city inizialitation
         */
        CityRecord favouriteCity = Main.dbms.getFavouriteCity();
        if (favouriteCity.getId() != -1) {
            textField_City.setText(favouriteCity.getName());
            searchButton.doClick();
        }
        else{
            setPanelEnabled(statisticPanel, false);
        }
    }

    /**
     * Update content of a table.
     * @param table table to update
     * @param data data to insert
     * @param columnNames column names
     */
    public void updateTable(JTable table, Vector<Vector<String>> data, Vector<String> columnNames) {
        table.setModel(new DefaultTableModel(data, columnNames));
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
    }

    /**
     * Creates and shows the statistics using the records within the Date range
     * @param fromDate minimum Date
     * @param toDate maximum Date
     */
    public void createStatistic(Date fromDate, Date toDate) {
        if(realtimeResponse!=null){
            simpleGraphButton.setEnabled(true);
            recordsGraphButton.setEnabled(true);
            enoughLabel.setText("");
            List<HumidityRecord> records = Main.dbms.getHumidity(realtimeResponse.getCityId());
            HumidityRecord record = StatsCalculator.min(records, fromDate, toDate);
            if(record!=null){
                double min = StatsCalculator.min(records, fromDate, toDate).getHumidity();
                double max = StatsCalculator.max(records, fromDate, toDate).getHumidity();
                double avg = StatsCalculator.avg(records, fromDate, toDate);
                double variance = StatsCalculator.variance(records, fromDate, toDate);
                Vector<Vector<String>> statistics = new Vector<Vector<String>>();
                statistics.add(new Vector<String>(Arrays.asList(df.format(min),
                        df.format(max),
                        df.format(avg),
                        df.format(variance))));
                updateTable(statisticsTable, statistics, statisticsColumnNames);
            }else{
                updateTable(statisticsTable, null, statisticsColumnNames);
                enoughLabel.setText("Not enough records");
                simpleGraphButton.setEnabled(false);
                recordsGraphButton.setEnabled(false);
            }
        }

    }

    /**
     * Enables/Disables the given panel, and all of its components
     * @param panel panel to enable/disable
     * @param isEnabled true: enable, false: disable
     */
    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();

        for(int i = 0; i < components.length; ++i) {
            Component component = components[i];
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel)component, isEnabled);
            }

            component.setEnabled(isEnabled);
        }

    }

    /**
     * Mandatory method needed to create components not included in Java.Swing
     */
    private void createUIComponents() {
        UtilDateModel modelFrom = new UtilDateModel();
        UtilDateModel modelTo = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanelFrom = new JDatePanelImpl(modelFrom, p);
        datePanelTo = new JDatePanelImpl(modelTo, p);
        datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        datePickerFrom.setVisible(true);
        datePickerTo.setEnabled(false);
    }

    /**
     * Updates the AsynCaller list of cities to search for
     */
    public void updateAsynCaller(){
        Vector<Integer> ids = new Vector<>();
        for(var city:Main.dbms.getCities()){
            ids.add(city.getId());
        }
         Main.asyncCaller.setArgs((Object) ids.toArray(Integer[]::new));
    }

    /**
     * Applies the given theme
     * @param theme theme to apply
     */
    public static void changeTheme(String theme){
        try {
            if(theme.equals("Light")) UIManager.setLookAndFeel(new FlatLightLaf());
            else if (theme.equals("Dark")) UIManager.setLookAndFeel(new FlatDarkLaf());
            else{
                Debugger.println("Tema selezionato non valido");
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }finally {
            Arrays.stream(JFrame.getFrames()).forEach(x -> SwingUtilities.updateComponentTreeUI(x.getComponent(0)));
        }
    }

    /**
     * Based on the date components builds a date range
     * @return Dates in this order: range start, range ending
     */
    public Date[] getDateRange(){
        Calendar cal=Calendar.getInstance();
        Date[] dates= new Date[2];
        if (timeStatsBox.getSelectedIndex() == 0) {
            cal.add(5, -7);
            dates[0] = cal.getTime();
            dates[1] = new Date();
        } else if (timeStatsBox.getSelectedIndex() == 1) {
            cal.add(5, -30);
            dates[0] = cal.getTime();
            dates[1] = new Date();
        } else if (!datePickerFrom.getJFormattedTextField().getText().equals("") && !datePickerTo.getJFormattedTextField().getText().equals("")) {
            dates[0] = (Date)datePickerFrom.getModel().getValue();
            dates[1] = (Date)datePickerTo.getModel().getValue();
        }
        return dates;
    }

    /**
     * Creates graphic
     * @param title Frame Title
     * @param dimension Graphic Dimension
     * @param dcd Dataset
     * @param isRecordsGraph false: create a BarChart (Based on statistics), true: create a LineChart (Based on records)
     */
    private void createGraph(String title, Dimension dimension, DefaultCategoryDataset dcd, boolean isRecordsGraph){
        JFreeChart jChart;

        if(isRecordsGraph)
            jChart = ChartFactory.createLineChart("Humidity", null, null, dcd,
                    PlotOrientation.VERTICAL, false, false, false);
        else
            jChart = ChartFactory.createBarChart("Humidity", null, null, dcd,
                    PlotOrientation.VERTICAL, false, false, false);

        CategoryPlot plot=jChart.getCategoryPlot();

        if(isRecordsGraph) {
            LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            renderer.setSeriesShapesVisible(0, true);
            plot.setRenderer(renderer);
        }

        plot.setRangeGridlinePaint(Color.black);
        if(Main.userSettings.getGuiTheme().equals("Dark")) {
            jChart.setBackgroundPaint(new Color(60, 60, 60));
            jChart.getTitle().setPaint(Color.LIGHT_GRAY);
            plot.setOutlinePaint(Color.GRAY);
            plot.getRangeAxis().setTickLabelPaint(Color.LIGHT_GRAY);
            plot.getDomainAxis().setTickLabelPaint(Color.LIGHT_GRAY);
        }

        ChartPanel chartpanel=new ChartPanel(jChart);
        chartpanel.setPreferredSize(dimension);
        JFrame chartFrame = new JFrame();
        ImageIcon icon = new ImageIcon("assets/icon64.png");
        chartFrame.setIconImage(icon.getImage());
        chartFrame.setTitle(title);
        chartFrame.add(chartpanel);
        chartFrame.setVisible(true);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(null);
    }

    /**
     * When launched if received record is the shown one, uodates table
     * @param sender caller that launched event
     * @param arg response with record
     */
    @Override
    public void onReceiveCurrent(Object sender, ApiArgument arg) {
        if(realtimeResponse!=null){
            if(arg.getResponse().getCityId()==realtimeResponse.getCityId())
                Main.dbms.addHumidity(HumidityRecord.singleToHumidityRecord(arg.getResponse()));
                searchButton.doClick();
                Debugger.println("Richiesta effettuata: " + arg.getResponse().getCityName() + " " + arg.getResponse().getHumidity());
            }
    }

    @Override
    public void onReceiveForecast(Object sender, ApiArgument arg) {

    }

    @Override
    public void onReceiveHistorical(Object sender, ApiArgument arg) {
    }

    @Override
    public void onReceive(Object sender, ApiArgument arg) {

    }

    @Override
    public void onRequestCurrent(Object sender, ApiArgument arg) {
    }

    @Override
    public void onRequestForecast(Object sender, ApiArgument arg) {

    }

    @Override
    public void onRequestHistorical(Object sender, ApiArgument arg) {

    }

    @Override
    public void onRequest(Object sender, ApiArgument arg) {

    }

    @Override
    public void onException(Object sender, Exception e) {
        JOptionPane.showMessageDialog(null, "Invalid API key! Please change it in the settings.");
    }

    /**
     * When launched updates shown city state, and calls updateAsynCaller method to update its cities.
     */
    @Override
    public void onChangedCities() {
            if(!Main.dbms.cityIsSaved(new CityRecord(realtimeResponse.getCityId(), realtimeResponse.getCityName(), realtimeResponse.getCoord())))
            {
                listenerOn=false;
                saveCityRecordsCheckBox.setSelected(false);
                setPanelEnabled(statisticPanel, false);
                nosuchLabel.setText("City removed!");
                updateTable(statisticsTable, null, statisticsColumnNames);
                listenerOn=true;
            }
            updateAsynCaller();
    }
}
