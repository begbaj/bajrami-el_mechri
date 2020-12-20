package umidity.gui;

import org.jdatepicker.impl.JDatePickerImpl;
import umidity.api.ApiCaller;
import umidity.api.EMode;
import umidity.api.EUnits;
import umidity.api.response.ApiResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import umidity.database.DatabaseManager;
import umidity.database.HumidityRecord;
import umidity.statistics.StatsCalculator;


public class MainGui {
    public JPanel panelMain;
    private JTextField textField_City;
    private JButton buttonApi;
    private JTextField textField_ZIP;
    private JTextField textField_State;
    private JTable recordsTable;
    private JTable forecastTable;
    private JTable statisticsTable;
    private JComboBox timeStatsBox;
    private JLabel label1;
    private JToolBar toolbar;
    private JButton settingsbutton;
    private JDatePanelImpl datePanelFrom;
    private JDatePanelImpl datePanelTo;
    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;
    private JLabel cityLabel;
    private JLabel enoguhLabel;
    private JLabel nosuchLabel;
    private JScrollPane tableScrollPane;
    private JTextArea textArea_Records;
    private JTable records;
    DatabaseManager DBSM=new DatabaseManager();
    ApiResponse realtimeResponse;
    StatsCalculator statsCalc;
    String[] recordColumnNames={"DateTime", "Temperature", "Humidity"};
    String[] statisticsColumnNames={"Min", "Max", "Avg", "Variance"};

    public MainGui(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ApiCaller caller=new ApiCaller("beb62ff92c75eefce173edf69bacd835", EMode.JSON, EUnits.Metric);
        createTable(recordsTable, null, recordColumnNames);
        createTable(statisticsTable, null, statisticsColumnNames);
        JButton settingsButton= new JButton();
        buttonApi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nosuchLabel.setText("");
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM hh:00");
                    realtimeResponse=caller.getByCityName(textField_City.getText(), textField_State.getText(), textField_ZIP.getText());
                    Date date= new Date(Long.parseLong(realtimeResponse.dt));
                    String dateString = format.format(date);
                    String[][] records={{dateString, Float.toString(realtimeResponse.main.temp), Float.toString(realtimeResponse.main.humidity)+"%"}};
                    createTable(recordsTable, records, recordColumnNames);
                    cityLabel.setText(realtimeResponse.name.toUpperCase());
                    HumidityRecord record=new HumidityRecord(realtimeResponse.main.humidity, new Date(), realtimeResponse.id, realtimeResponse.coord);
                    DBSM.addHumidity(record);
                    timeStatsBox.setSelectedIndex(0);
                }
                catch (FileNotFoundException fnfException){
                    nosuchLabel.setText("Can't find any area with such parameters");
                }
                catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        settingsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SettingsFrame settingsGui = new SettingsFrame();
            }
        });

        timeStatsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeStatsBox.getSelectedIndex()==0)
                {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -7);
                    Date fromDate = cal.getTime();
                    createStatistic(fromDate, new Date());
                }
                else if(timeStatsBox.getSelectedIndex()==1){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -30);
                    Date fromDate = cal.getTime();
                    createStatistic(fromDate, new Date());
                }
                else{
                    if(datePickerFrom.getJFormattedTextField().getText()!="" && datePickerTo.getJFormattedTextField().getText()!=""){
                        Date fromDate = (Date) datePickerFrom.getModel().getValue();
                        Date toDate = (Date) datePickerTo.getModel().getValue();
                        createStatistic(fromDate, toDate);
                    }
                }
            }
        });

        datePickerFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeStatsBox.getSelectedIndex()==2){
                    timeStatsBox.setSelectedIndex(2);
                }
            }
        });

        datePickerTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeStatsBox.getSelectedIndex()==2){
                    timeStatsBox.setSelectedIndex(2);
                }
            }
        });
    }

    public void createTable(JTable table, String[][] data, Object[] columnNames){
        table.setModel(new DefaultTableModel(data, columnNames));
        table.setFillsViewportHeight(true);
    }

    public void createStatistic(Date fromDate, Date toDate){
        try {
            enoguhLabel.setText("");
            String[][] statistics = {{Double.toString(statsCalc.min(DBSM.getHumidity(realtimeResponse.id), fromDate, toDate).getHumidity()),
                    Double.toString(statsCalc.max(DBSM.getHumidity(realtimeResponse.id), fromDate, toDate).getHumidity()),
                    Double.toString(statsCalc.avg(DBSM.getHumidity(realtimeResponse.id), fromDate, toDate)),
                    Double.toString(statsCalc.variance(DBSM.getHumidity(realtimeResponse.id), fromDate, toDate))}};
            createTable(statisticsTable, statistics, statisticsColumnNames);
        }catch (Exception e){
            createTable(statisticsTable, null, statisticsColumnNames);
            enoguhLabel.setText("Not enough records");
        }
    }

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
        datePickerTo=new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        datePickerFrom.setVisible(true);
        datePickerTo.setEnabled(false);
    }
}