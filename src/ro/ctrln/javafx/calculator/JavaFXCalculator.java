package ro.ctrln.javafx.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * CTRL+N Java Developer Program
 * <p>
 * Module JavaFX & SceneBuilder
 * <p>
 * 04/2021
 */
public class JavaFXCalculator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("JavaFX Calcualtor");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
