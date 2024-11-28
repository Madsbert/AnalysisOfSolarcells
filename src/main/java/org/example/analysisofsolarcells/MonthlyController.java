package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MonthlyController {

    @FXML
    private Spinner<Integer> YearSpinner;
    @FXML
    private ChoiceBox<String> MonthChoiceBox;
    private String[] Months = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @FXML
    private TextField siteIDTextFieldMonthly;
    @FXML
    private Label resultLabelMonthly;
    @FXML
    private LineChart<Number, Number> monthlyLineChart;
    Measurement[] monthlyMeasurements = new Measurement[32];


    public void initialize() {
        //spinner value factory object
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2100);
        //start value
        valueFactory.setValue(2024);
        //inserts start value
        YearSpinner.setValueFactory(valueFactory);

        //Creating ChoiceBoxes
        MonthChoiceBox.getItems().addAll(Months);
        MonthChoiceBox.setValue("Vælg måned");

    }

    public String getMonth() {
        switch (MonthChoiceBox.getValue()) {
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "0";
        }

    }

    public int getSiteID() {
        int siteID = Integer.parseInt(siteIDTextFieldMonthly.getText());
        return siteID;
    }

    public int getYear() {
        return YearSpinner.getValue();
    }

    public void onDailySwitchClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MonthlyController.class.getResource("Daily-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Daily Solar Cell Analysis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void onShowGraphClickMonthly() throws FileNotFoundException {
       getDaysInMonth();
       updateTotalKwhMonthly();

        int daysInMonth = getDaysInMonth();
        displayMonthlyGraph(daysInMonth);
    }
    public int getDaysInMonth() throws FileNotFoundException {

        int daysInMonth = 31;
        switch (MonthChoiceBox.getValue()) {
            case "January", "March", "May", "July", "August", "October", "December":
                daysInMonth = 31;
                break;

            case "April", "June", "September", "November":
                daysInMonth = 30;
                break;

            case "February":
                daysInMonth = 28;
                break;

            default:
                if (MonthChoiceBox.getValue().isEmpty()) {
                   daysInMonth = 0;
                }
                break;

        }
        getMonthlyMeasurements(daysInMonth);
        return daysInMonth;
    }

    private void displayMonthlyGraph(int daysInMonth) {

        XYChart.Series series = new XYChart.Series<>();

        series.setName("Produktionen i dag");
        for (int i = 0; i < daysInMonth; i++){
            int numberToAdd;
            String dayToAdd = "" + (i + 1);

            if(monthlyMeasurements[i]==null)
            {
                numberToAdd = 0;
            }
            else
            {
                numberToAdd = monthlyMeasurements[i].getOnline();
            }
            series.getData().add(new XYChart.Data<>(dayToAdd,numberToAdd));

        }
        monthlyLineChart.getData().addAll(series);

    }

    private void updateTotalKwhMonthly() {
        int totalKwh = Calculations.calculateTotalKwh(monthlyMeasurements);
        resultLabelMonthly.setText("Total Kwh for the month: " + totalKwh);
    }

    public void getMonthlyMeasurements(int daysInMonth) throws FileNotFoundException {
        Read dataReader = new Read();

        {
            for (int day = 0; day <= daysInMonth; day++) {
                int onlineDayliTotal = 0;
                int dayCheckDay = day++;
                String checkDay;
                if(dayCheckDay<10)
                {
                    checkDay = "0" + dayCheckDay;
                }
                else
                {
                    checkDay = Integer.toString(dayCheckDay);
                }

                Read.fileReaderMonthly(getSiteID(), getMonth(), getYear(), checkDay);

                //day starter som 1, <= days in month, string

                for (int hour = 0; hour < 24; hour++) {
                    onlineDayliTotal = onlineDayliTotal + dataReader.getOnlineVar(hour);
                }

                monthlyMeasurements[day] = new Measurement(onlineDayliTotal);
                System.out.println("Day " + day + ": " + onlineDayliTotal);
                System.out.println(monthlyMeasurements[day].getOnline());
            }
            //System.out.println(""+monthlyMeasurements[1].getOnline()+monthlyMeasurements[7].getOnline()+monthlyMeasurements[29].getOnline()+monthlyMeasurements[13].getOnline()+monthlyMeasurements[25].getOnline()+monthlyMeasurements[19].getOnline());
        }
    }
}
