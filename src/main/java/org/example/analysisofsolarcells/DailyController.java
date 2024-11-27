package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DailyController {

    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField siteIDTextField;

    Measurement[] measurements = new Measurement[24];

    public void initialize() {
    }

    //Get the Date and saves it as String named formattedDate.
    public String getDate() {

         String selectedDate = DatePicker.getValue().toString();
         return selectedDate;
    }
    public int getSiteID() {
        int siteID = Integer.parseInt(siteIDTextField.getText());
        return siteID;
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

    public void onCompareGraphClick(ActionEvent actionEvent) {
        //if time, create a method the clears choiceboxes, datepicker and results, and allows a new graph.
    }
    public void onShowGraphClick() throws FileNotFoundException
    {
        getMeasurements();
    }

    public void getMeasurements() throws FileNotFoundException
    {
        Read dataReader = new Read();
        dataReader.readFile(getSiteID(), getDate());
        for(int i = 0; i < measurements.length; i++)
        {
            int online;

            online = dataReader.getOnlineVar(i);

            measurements[i] = new Measurement(online);
            System.out.println(online);
        }

    }

}