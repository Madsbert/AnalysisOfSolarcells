package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MonthlyController {

    @FXML
    private Spinner<Integer> YearSpinner;
    private int currentYear=0;
    @FXML
    private ChoiceBox<String> MonthChoiceBox;
    private String[] Months = {
            "January", "February", "March", "April", "May", "June","July", "August", "September", "October","November","December"};
    @FXML
    private TextField siteIDTextField;
    @FXML
    private Label resultLabel;

    Measurement[] monthlyMeasurements = new Measurement[31];


    public void initialize() {
        //spinner value factory object
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2100);
        //start value
        valueFactory.setValue(2024);
        //inserts start value
        YearSpinner.setValueFactory(valueFactory);
        //saved the selected year, and saves it as a string.
        currentYear = YearSpinner.getValue();

        //Creating ChoiceBoxes
        MonthChoiceBox.getItems().addAll(Months);
        MonthChoiceBox.setValue("Vælg måned");

    }
    public String getMonth(){
        //selected month is saved as a String.
        String selectedMonth = MonthChoiceBox.getValue();
        return selectedMonth;
    }
    public int getSiteID() {
        int siteID = Integer.parseInt(siteIDTextField.getText());
        return siteID;
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
    public void onShowGraphClickMonthly() throws FileNotFoundException{

        switch (MonthChoiceBox.getValue()) {
            case "January","March","May","July","August","October","December":
                for (int monthday = 0; monthday < 31; monthday++) {
                    getMonthlyMeasurements();
                }
                break;

            case "April","June","September","November":
                for (int monthday = 0; monthday < 30; monthday++) {
                    getMonthlyMeasurements();
                }
                break;

                case "February":
                    for (int monthday = 0; monthday < 28; monthday++) {
                        getMonthlyMeasurements();
                    }
        }
        displayMonthlyGraph();
    }

    private void displayMonthlyGraph() {

    }

    private void updateTotalKwhMonthly() {
        int totalKwh = Calculations.calculateTotalKwh(monthlyMeasurements);
        resultLabel.setText("Total Kwh: " + totalKwh);
    }

    public void getMonthlyMeasurements() throws FileNotFoundException
    {
        Read dataReader = new Read();
        dataReader.readFile(getSiteID(), getMonth());

        for(int i = 0; i < 24; i++)
        {
            int onlineDayliTotal = 0;

            onlineDayliTotal = onlineDayliTotal + dataReader.getOnlineVar(i);

            monthlyMeasurements[i] = new Measurement(onlineDayliTotal);
            System.out.println(onlineDayliTotal);
        }

    }
}
