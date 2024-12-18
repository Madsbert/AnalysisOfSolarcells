package org.example.analysisofsolarcells;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AscApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AscApplication.class.getResource("Daily-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Daily Solar Cell Analysis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)throws IOException {
        launch();
    }
}
