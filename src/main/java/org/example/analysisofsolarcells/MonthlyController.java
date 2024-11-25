package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class MonthlyController {

    @FXML
    private Spinner<Integer> YearSpinner;
    private int currentYear=0;
    @FXML
    private ChoiceBox<String> MonthChoiceBox;
    private String[] Months = {
            "January", "February", "March", "April", "May", "June","July", "August", "September", "October","November","December"};
    private String selectedMonth="";

    @FXML
    private ChoiceBox<String> SiteIDChoiceBox;
    private String[] SiteID = {"1234","5678"};
    private String selectedSiteID="";



    public void initialize() {
        //spinner value factory object
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2100);
        //start value
        valueFactory.setValue(2024);
        //inserts start value
        YearSpinner.setValueFactory(valueFactory);
        //sets the value to currentWeight variable, for calculation
        currentYear = YearSpinner.getValue();


        //Creating ChoiceBoxes
        MonthChoiceBox.getItems().addAll(Months);
        //selected month is saved as a String.
        selectedMonth = MonthChoiceBox.getValue();
        SiteIDChoiceBox.getItems().addAll(SiteID);
        //selected Site ID is saved as a String
        SiteIDChoiceBox.setValue(selectedSiteID);
    }












    public void onDailySwitchClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MonthlyController.class.getResource("Daily-view.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 667, 525);
        stage.setTitle("login Screen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
