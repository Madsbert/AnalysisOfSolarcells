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
    Measurement[] monthlyMeasurements = new Measurement[31];


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

    public int getMonth() {
        switch (MonthChoiceBox.getValue()) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
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


        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        series.setName("Produktionen i dag");
        for (int i = 0; i < daysInMonth; i++){

            series.getData().add(new XYChart.Data<>(i+1,monthlyMeasurements[i].getOnline()));

        }
        monthlyLineChart.getData().add(series);

    }

    private void updateTotalKwhMonthly() {
        int totalKwh = Calculations.calculateTotalKwh(monthlyMeasurements);
        resultLabelMonthly.setText("Total Kwh for the month: " + totalKwh);
    }

    public void getMonthlyMeasurements(int daysInMonth) throws FileNotFoundException {
        Read dataReader = new Read();
        dataReader.fileReaderMonthly(getSiteID(), getMonth(), getYear());
        {
            for (int day = 0; day < daysInMonth; day++) {
                int onlineDayliTotal = 0;

                for (int hour = 0; hour < 24; hour++) {
                    onlineDayliTotal = onlineDayliTotal + dataReader.getOnlineVar(hour);
                }

                monthlyMeasurements[day] = new Measurement(onlineDayliTotal);
                System.out.println("Day " + (day + 1) + ": " + onlineDayliTotal);
            }
        }
    }
}
