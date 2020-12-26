package com.umidity.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.umidity.api.caller.ApiCaller;
import com.umidity.api.caller.EMode;
import com.umidity.api.caller.EUnits;
import com.umidity.api.response.ApiResponse;
import com.umidity.Coordinates;
import com.umidity.api.response.ForecastResponse;
import com.umidity.database.CityRecord;
import com.umidity.database.DatabaseManager;
import com.umidity.database.HumidityRecord;
import com.umidity.statistics.StatsCalculator;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

public class MainGui {
    public JPanel panelMain;
    private JTextField textField_City;
    private JButton buttonApi;
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
    private JButton button1;
    private JButton button2;
    private ChartPanel chartPanel;
    private ChartPanel chartRecordsPanel;
    DatabaseManager DBMS;
    ApiResponse realtimeResponse;
    StatsCalculator statsCalc;
    String[] recordColumnNames;
    String[] statisticsColumnNames;
    boolean listenerOn;
    Locale currentLocale;
    DecimalFormatSymbols otherSymbols;
    DecimalFormat df;
    DefaultCategoryDataset dcd;

    public MainGui() {
        createUIComponents();
        DBMS = new DatabaseManager();
        statsCalc = new StatsCalculator();
        recordColumnNames = new String[]{"DateTime", "Temperature", "Humidity"};
        statisticsColumnNames = new String[]{"Min", "Max", "Avg", "Variance"};
        listenerOn = true;
        currentLocale = Locale.getDefault();
        otherSymbols = new DecimalFormatSymbols(this.currentLocale);

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception var3) {
            System.err.println("Failed to initialize LaF");
        }

        this.otherSymbols.setDecimalSeparator('.');
        this.df = new DecimalFormat("###.##", this.otherSymbols);
        Vector<String> vectorRecordColumnNames = new Vector();
        vectorRecordColumnNames.add("DateTime");
        vectorRecordColumnNames.add("Temperature");
        vectorRecordColumnNames.add("Humidity");
        SwingUtilities.updateComponentTreeUI(this.panelMain);
        ApiCaller caller = new ApiCaller("beb62ff92c75eefce173edf69bacd835", EMode.JSON, EUnits.Metric);
        this.createTable(this.recordsTable, (String[][])null, this.recordColumnNames);
        this.createTable(this.statisticsTable, (String[][])null, this.statisticsColumnNames);
        new JButton();
        this.buttonApi.addActionListener((e) -> {
            try {
                this.nosuchLabel.setText("");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM hh:00");
                this.realtimeResponse = caller.getByCityName(this.textField_City.getText(), this.textField_State.getText(), this.textField_ZIP.getText());
                Date date = new Date((new Timestamp(Long.parseLong(this.realtimeResponse.dt) * 1000L)).getTime());
                String dateString = format.format(date);
                Vector<Vector<String>> matrix = new Vector();
                Vector<String> firstRow = new Vector();
                firstRow.add(dateString);
                firstRow.add(Float.toString(this.realtimeResponse.main.temp));
                firstRow.add(Float.toString((float)this.realtimeResponse.main.humidity) + "%");
                matrix.add(firstRow);
                ForecastResponse forecastResponse = caller.getForecastByCityName(this.textField_City.getText(), this.textField_State.getText(), this.textField_ZIP.getText());
                ApiResponse[] var10 = forecastResponse.list;
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    ApiResponse f_record = var10[var12];
                    Date datetime = new Date((new Timestamp(Long.parseLong(f_record.dt) * 1000L)).getTime());
                    String datetimeString = format.format(datetime);
                    Vector<String> nextRow = new Vector();
                    nextRow.add(datetimeString);
                    nextRow.add(Float.toString(f_record.main.temp));
                    nextRow.add(Float.toString((float)f_record.main.humidity) + "%");
                    matrix.add(nextRow);
                }

                this.recordsTable.setModel(new DefaultTableModel(matrix, vectorRecordColumnNames));
                this.recordsTable.setFillsViewportHeight(true);
                this.cityLabel.setText(this.realtimeResponse.name.toUpperCase());
                CityRecord city = new CityRecord(this.realtimeResponse.id, this.realtimeResponse.name, this.realtimeResponse.getCoord());
                HumidityRecord record = new HumidityRecord((double)this.realtimeResponse.main.humidity, new Date(), city);
                this.listenerOn = false;
                if (this.DBMS.cityisSaved(city)) {
                    this.saveCityRecordsCheckBox.setSelected(true);
                    this.DBMS.addHumidity(record);
                    this.timeStatsBox.setSelectedIndex(0);
                    this.setPanelEnabled(this.statisticPanel, true);
                } else {
                    this.saveCityRecordsCheckBox.setSelected(false);
                    this.createTable(this.statisticsTable, (String[][])null, this.statisticsColumnNames);
                    this.setPanelEnabled(this.statisticPanel, false);
                }

                this.setFavouriteCityCheckBox.setSelected(this.DBMS.getFavouriteCity().getId() == city.getId());
                this.listenerOn = true;
            } catch (FileNotFoundException var17) {
                this.nosuchLabel.setText("Can't find any area with such parameters");
            } catch (IOException var18) {
                var18.printStackTrace();
            }

        });
        this.settingsbutton.addActionListener((e) -> {
            SettingsFrame settingsGui = new SettingsFrame();
            WindowFocusListener hi = new WindowFocusListener() {
                public void windowGainedFocus(WindowEvent e) {
                    SwingUtilities.updateComponentTreeUI(MainGui.this.panelMain);
                }

                public void windowLostFocus(WindowEvent e) {
                    SwingUtilities.updateComponentTreeUI(MainGui.this.panelMain);
                    MainGui.this.DBMS.setUserSettings();
                }
            };
            settingsGui.addWindowFocusListener(hi);
        });
        this.timeStatsBox.addActionListener((e) -> {
            Calendar cal;
            Date toDate;
            if (this.timeStatsBox.getSelectedIndex() == 0) {
                cal = Calendar.getInstance();
                cal.add(5, -7);
                toDate = cal.getTime();
                this.createStatistic(toDate, new Date());
            } else if (this.timeStatsBox.getSelectedIndex() == 1) {
                cal = Calendar.getInstance();
                cal.add(5, -30);
                toDate = cal.getTime();
                this.createStatistic(toDate, new Date());
            } else if (!this.datePickerFrom.getJFormattedTextField().getText().equals("") && !this.datePickerTo.getJFormattedTextField().getText().equals("")) {
                Date fromDate = (Date)this.datePickerFrom.getModel().getValue();
                toDate = (Date)this.datePickerTo.getModel().getValue();
                this.createStatistic(fromDate, toDate);
            }

        });
        this.datePickerFrom.addActionListener((e) -> {
            if (this.timeStatsBox.getSelectedIndex() == 2) {
                this.timeStatsBox.setSelectedIndex(2);
            }

        });
        this.datePickerTo.addActionListener((e) -> {
            if (this.timeStatsBox.getSelectedIndex() == 2) {
                this.timeStatsBox.setSelectedIndex(2);
            }

        });
        this.saveCityRecordsCheckBox.addActionListener((e) -> {
            if (this.listenerOn) {
                try {
                    CityRecord city = new CityRecord(this.realtimeResponse.id, this.realtimeResponse.name, this.realtimeResponse.coord);
                    if (this.saveCityRecordsCheckBox.isSelected()) {
                        boolean flag = this.DBMS.addCity(city);
                        if (flag) {
                            this.nosuchLabel.setText("City added!");
                        } else {
                            System.out.println("SOMETHING WRONG");
                        }
                    } else if (JOptionPane.showConfirmDialog(this.panelMain, "Remove city and delete all its records?", "Message", 0) == 0) {
                        this.DBMS.removeCity(city);
                        this.nosuchLabel.setText("City removed!");
                    } else {
                        this.listenerOn = false;
                        this.saveCityRecordsCheckBox.setSelected(true);
                        this.listenerOn = true;
                    }
                } catch (Exception var4) {
                    this.nosuchLabel.setText("You have to search it first");
                    this.listenerOn = false;
                    this.saveCityRecordsCheckBox.setSelected(false);
                    this.listenerOn = true;
                }
            }

        });
        this.setFavouriteCityCheckBox.addActionListener((e) -> {
            if (this.listenerOn) {
                if (this.setFavouriteCityCheckBox.isSelected()) {
                    this.DBMS.setFavouriteCity(new CityRecord(this.realtimeResponse.id, this.realtimeResponse.name, this.realtimeResponse.coord));
                } else {
                    this.DBMS.setFavouriteCity(new CityRecord(-1, "", new Coordinates(-1.0F, -1.0F)));
                }
            }

        });
        this.favouriteCityStart();
        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd=new DefaultCategoryDataset();
                List<HumidityRecord> records = DBMS.getHumidity(realtimeResponse.id);
                Calendar cal;
                Date fromDate=new Date();
                Date toDate=new Date();
                if (timeStatsBox.getSelectedIndex() == 0) {
                    cal = Calendar.getInstance();
                    cal.add(5, -7);
                    fromDate = cal.getTime();
                } else if (timeStatsBox.getSelectedIndex() == 1) {
                    cal = Calendar.getInstance();
                    cal.add(5, -30);
                    fromDate = cal.getTime();
                } else if (datePickerFrom.getJFormattedTextField().getText().equals("") && datePickerTo.getJFormattedTextField().getText().equals("")) {
                    fromDate = (Date)datePickerFrom.getModel().getValue();
                    toDate = (Date)datePickerTo.getModel().getValue();
                }
                double min = StatsCalculator.min(records, fromDate, toDate).getHumidity();
                double max = StatsCalculator.max(records, fromDate, toDate).getHumidity();
                double avg = StatsCalculator.avg(records, fromDate, toDate);
                dcd.setValue(min, "Humidity", "Min");
                dcd.setValue(max, "Humidity", "Max");
                dcd.setValue(avg, "Humidity", "Avg");

                JFreeChart jChart = ChartFactory.createBarChart("Humidity", (String)null, (String)null, dcd, PlotOrientation.VERTICAL, false, false, false);
                CategoryPlot plot=jChart.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.black);
                ChartPanel chartpanel=new ChartPanel(jChart);
                chartpanel.setPreferredSize(new Dimension(400, 400));

                JFrame chartFrame = new JFrame();
                chartFrame.add(chartpanel);
                chartFrame.setVisible(true);
                chartFrame.setSize(500,400);
                chartFrame.pack();
                chartFrame.setLocationRelativeTo(null);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dcd=new DefaultCategoryDataset();
                List<HumidityRecord> records = DBMS.getHumidity(realtimeResponse.id);
                for(HumidityRecord record:records){
                    dcd.addValue(record.getHumidity(), "Humidity", record.getDate());
                }

                JFreeChart jChart = ChartFactory.createLineChart("Humidity", (String)null, (String)null, dcd, PlotOrientation.VERTICAL, false, false, false);
                CategoryPlot plot=jChart.getCategoryPlot();
                plot.setRangeGridlinePaint(Color.black);
                ChartPanel chartpanel=new ChartPanel(jChart);
                chartpanel.setPreferredSize(new Dimension(1000, 400));

                JFrame chartFrame = new JFrame();
                chartFrame.add(chartpanel);
                chartFrame.setVisible(true);
                chartFrame.setSize(1000,400);
                chartFrame.pack();
                chartFrame.setLocationRelativeTo(null);
            }
        });
    }

    public void createTable(JTable table, String[][] data, Object[] columnNames) {
        table.setModel(new DefaultTableModel(data, columnNames));
        table.setFillsViewportHeight(true);
    }

    public void createStatistic(Date fromDate, Date toDate) {
        try {
            this.enoughLabel.setText("");
            List<HumidityRecord> records = this.DBMS.getHumidity(this.realtimeResponse.id);
            double min = StatsCalculator.min(records, fromDate, toDate).getHumidity();
            double max = StatsCalculator.max(records, fromDate, toDate).getHumidity();
            double avg = StatsCalculator.avg(records, fromDate, toDate);
            createStatsGraph(min, max, avg);
            double variance = StatsCalculator.variance(records, fromDate, toDate);
            String[][] statistics = new String[][]{{Double.toString(min), Double.toString(max), this.df.format(avg), this.df.format(variance)}};
            this.createTable(this.statisticsTable, statistics, this.statisticsColumnNames);
        } catch (Exception var14) {
            this.createTable(this.statisticsTable, (String[][])null, this.statisticsColumnNames);
            this.enoughLabel.setText("Not enough records");
        }

    }

    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        Component[] var4 = components;
        int var5 = components.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Component component = var4[var6];
            if (component instanceof JPanel) {
                this.setPanelEnabled((JPanel)component, isEnabled);
            }

            component.setEnabled(isEnabled);
        }

    }

    private void favouriteCityStart() {
        CityRecord favouriteCity = this.DBMS.getFavouriteCity();
        if (favouriteCity.getId() != -1) {
            this.textField_City.setText(favouriteCity.getName());
            this.buttonApi.doClick();
        }

    }

    private void createUIComponents() {
        UtilDateModel modelFrom = new UtilDateModel();
        UtilDateModel modelTo = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
        this.datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
        this.datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        this.datePickerFrom.setVisible(true);
        this.datePickerTo.setEnabled(false);
        chartPanel=new ChartPanel(null);
        int c=3;
    }

    private void createStatsGraph(double min, double max, double avg) {
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        dcd.setValue(min, "Humidity", "Min");
        dcd.setValue(max, "Humidity", "Max");
        dcd.setValue(avg, "Humidity", "Avg");
        JFreeChart jChart = ChartFactory.createBarChart("Humidity", (String)null, (String)null, dcd, PlotOrientation.VERTICAL, false, false, false);
        chartPanel=new ChartPanel(null);
        chartPanel.setChart(jChart);
        chartPanel.validate();
        panelMain.validate();
    }

//    private JFreeChart createChart(final XYDataset dataset) {
//        final JFreeChart result = ChartFactory.createTimeSeriesChart(
//                TITLE, "hh:mm:ss", "milliVolts", dataset, true, true, false);
//        final XYPlot plot = result.getXYPlot();
//        ValueAxis domain = plot.getDomainAxis();
//        domain.setAutoRange(true);
//        ValueAxis range = plot.getRangeAxis();
//        range.setRange(-MINMAX, MINMAX);
//        return result;
//    }
}
