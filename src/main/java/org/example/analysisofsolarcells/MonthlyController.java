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
    //references to the UI in the FXML file
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
    //Array to save the measurements for a month
    Measurement[] monthlyMeasurements = new Measurement[31];

    //initializing to give spinner and choicebox start values.
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
    //Get the Date and returns it as a String named 01,02,03...
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
    //gets the site-ID and converts it to an int, and returns it
    public int getSiteID() {
        int siteID = Integer.parseInt(siteIDTextFieldMonthly.getText());
        return siteID;
    }
    //gets the year from YearSpinner and returns it
    public int getYear() {
        return YearSpinner.getValue();
    }
    //switches to monthly Scene
    public void onDailySwitchClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MonthlyController.class.getResource("Daily-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Daily Solar Cell Analysis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    //shows the graph.
    public void onShowGraphClickMonthly() throws FileNotFoundException {

        getDaysInMonth();
        updateTotalKwhMonthly();
        int daysInMonth = getDaysInMonth();
        displayMonthlyGraph(daysInMonth);
    }
    //get how many days there are in a month, and saves it in a variable.
    public int getDaysInMonth() throws FileNotFoundException {

        int daysInMonth = 1;
        switch (MonthChoiceBox.getValue()) {
            case "January", "March", "May", "July", "August", "October", "December":
                daysInMonth = 31;
                break;

            case "April", "June", "September", "November":
                daysInMonth = 30;
                break;

            case "February":
                //Leap Year Fix.
                int year = YearSpinner.getValue();
                if ((year % 4 == 0)){
                    daysInMonth = 29;
                }
                else
                {
                    daysInMonth = 28;
                }
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
    //Takes how many days in a month, and displays it in a graph from our monthlyMeasurements array.
    private void displayMonthlyGraph(int daysInMonth) {
        //Create a graph, that is ready for information.
        XYChart.Series series = new XYChart.Series<>();
        //name of the specific graph.
        series.setName("Month: " + MonthChoiceBox.getValue() + " & Site ID: " + getSiteID());

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
        //insert the information.
        monthlyLineChart.getData().addAll(series);

    }
    //Uses calculate to add everything from the array.
    private void updateTotalKwhMonthly() {
        int totalKwh = Calculations.calculateTotalKwh(monthlyMeasurements);
        resultLabelMonthly.setText("Total Kwh for the month: " + totalKwh);
    }
    //Takes days in a month, runs daily 24 time for each day in a month, and adds it to the monthlyMeasurement array.
    public void getMonthlyMeasurements(int daysInMonth) throws FileNotFoundException {
        Read dataReader = new Read();
        {
            for (int day = 0; day < daysInMonth; day++) {
                int onlineDailyTotal = 0;
                int dayCheckDay = day+1;
                String checkDay;
                if(dayCheckDay<10)
                {
                    checkDay = "0" + dayCheckDay; //in our record, we search for a specific month with a String
                }
                else
                {
                    checkDay = Integer.toString(dayCheckDay);
                }
                //takes siteID, month, year, day and looks up the specific values in the record.
                Read.fileReaderMonthly(getSiteID(), getMonth(), getYear(), checkDay);

                //day starter som 1, <= days in month, string
                //adds each hourly production from onlineVar array into onlineDailyTotal variable.
                for (int hour = 0; hour < 24; hour++) {
                    onlineDailyTotal = onlineDailyTotal + dataReader.getOnlineVar(hour);
                }
                //adds the kwh production for each day into the array.
                monthlyMeasurements[day] = new Measurement(onlineDailyTotal);

                //these were used for bug finding.
                //System.out.println("Day " + day + ": " + onlineDailyTotal);
                //System.out.println(monthlyMeasurements[day].getOnline());
            }
        }
    }
}
