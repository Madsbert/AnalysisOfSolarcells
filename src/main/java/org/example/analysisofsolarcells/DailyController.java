package org.example.analysisofsolarcells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DailyController {

    @FXML
    private DatePicker DatePicker;

    public void initialize() {
    }

    //Get the Date and saves it as String named formattedDate.
    public void getDate(ActionEvent event) {
         LocalDate selectedDate = DatePicker.getValue();
         //Changes format for date.
         String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
         //test print
         System.out.println(formattedDate);
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
}