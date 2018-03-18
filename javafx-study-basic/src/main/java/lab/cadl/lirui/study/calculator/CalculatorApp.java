package lab.cadl.lirui.study.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("计算器");

//        Parent root = new FXMLLoader().load(CalculatorApp.class.getResourceAsStream("calculator.fxml"));
//        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(CalculatorPane.createScene());
        primaryStage.show();
}
}
