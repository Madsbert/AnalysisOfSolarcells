package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DailyController {

    @FXML
    private DatePicker DatePicker;

    Measurement[] measurements = new Measurement[24];

    public void initialize() {
    }

    //Get the Date and saves it as String named formattedDate.
    public void getDate(ActionEvent event) {
         LocalDate selectedDate = DatePicker.getValue();
         //Changes format for date.
         String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
         //test print
         System.out.println(DatePicker.getValue());
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
    public void onShowGraphClick(ActionEvent actionEvent) {
    }

        public void getMeasurements()
        {

            for (int i = 0; i < measurements.length; i++) {
                int online;

                Read dataReader = new Read();

                //online = dataReader.online[i];

                //measurements[i] = new Measurement(online);
            }

        }

    }
