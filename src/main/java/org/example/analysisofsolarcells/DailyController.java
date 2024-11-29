package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class DailyController {

    //references to the UI in the FXML file
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField siteIDTextField;
    @FXML
    private LineChart<Number,Number> dailyLineChart;
    @FXML
    private Label resultLabel;
    @FXML
    private Label compare;

    //Array to save the measurements for a day
    Measurement[] measurements = new Measurement[24];

    //Get the Date and saves it as String named formattedDate.
    public String getDate() {
         String selectedDate = DatePicker.getValue().toString();
         return selectedDate;
    }

    //gets the site-ID and converts it to an int
    public int getSiteID() {
        try {
            int siteID = Integer.parseInt(siteIDTextField.getText());
            return siteID;
        }catch (NumberFormatException e) {
            resultLabel.setText("Invalid Site ID");
            return -1;
        }
    }

    //switches to monthly Scene.
    public void onMonthlySwitchClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DailyController.class.getResource("Monthly-view.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Monthly Solar Cell Analysis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //Method to compare graphs
    public void onCompareGraphClick() throws FileNotFoundException {
        if (graphClick){
            getMeasurements();
            displayGraph();

            compareTotalKwh();
        }
    }

    //Method which shows information when user clicks the button
    private boolean graphClick = false;
    public void onShowGraphClick() throws FileNotFoundException
    {
        dailyLineChart.getData().clear();
        getMeasurements();
        displayGraph();
        updateTotalKwh();
        graphClick =true;
    }

    //calculates and updates total kwh in a label
    private void updateTotalKwh() {
        int totalKwh = Calculations.calculateTotalKwh(measurements);
        resultLabel.setText("Total Kwh: " + totalKwh);
    }

    private void compareTotalKwh() {
        int totalKwh = Calculations.calculateTotalKwh(measurements);
        compare.setText("Total Kwh: " + totalKwh);
    }

    //gets measurements for a day based on site-ID and chosen date
    public void getMeasurements() throws FileNotFoundException
    {
        Read dataReader = new Read();
        dataReader.readFile(getSiteID(), getDate());

        //loop through 24hours and saves the data in the measurement array
        for(int i = 0; i < measurements.length; i++)
        {
            int online= dataReader.getOnlineVar(i);
            measurements[i] = new Measurement(online);
            System.out.println(online);
        }

    }
    //shows graph based on measurements
    public void displayGraph(){
        XYChart.Series series = new XYChart.Series();
        series.setName("Dato: " + DatePicker.getValue().toString() + " & Site ID: " + getSiteID());

        //adds data to the series hourly
        for (int i = 0; i < measurements.length; i++){
            String xName;

            //formatting the hour values
            if(i<10)
            {
                xName = "0" + i;
            }
            else
            {
                xName = "" + i;
            }

            series.getData().add(new XYChart.Data<>(xName,measurements[i].getOnline()));

        }
        dailyLineChart.getData().addAll(series);
    }
}

